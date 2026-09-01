package io.github.sinri.keel.logger.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

class LateObjectTest {

    @Test
    void testSetAndGet() {
        LateObject<String> lateObject = new LateObject<>();

        Assertions.assertFalse(lateObject.isInitialized());
        Assertions.assertThrows(IllegalStateException.class, lateObject::get);

        lateObject.set("value");

        Assertions.assertTrue(lateObject.isInitialized());
        Assertions.assertEquals("value", lateObject.get());
        Assertions.assertThrows(IllegalStateException.class, () -> lateObject.set("another"));
    }

    @Test
    void testRejectsNullValues() {
        LateObject<String> fromSet = new LateObject<>();
        LateObject<String> fromSupplier = new LateObject<>();

        Assertions.assertThrows(NullPointerException.class, () -> fromSet.set(null));
        Assertions.assertThrows(NullPointerException.class, () -> fromSupplier.ensure(null));
        Assertions.assertThrows(NullPointerException.class, () -> fromSupplier.ensure(() -> null));
        Assertions.assertFalse(fromSet.isInitialized());
        Assertions.assertFalse(fromSupplier.isInitialized());
    }

    @Test
    void testEnsureSupportsCovariantSupplier() {
        LateObject<Number> lateObject = new LateObject<>();
        Supplier<Integer> supplier = () -> 42;

        Assertions.assertEquals(42, lateObject.ensure(supplier));
    }

    @Test
    void testEnsurePropagatesExceptionAndAllowsRetry() {
        LateObject<String> lateObject = new LateObject<>();
        IllegalArgumentException failure = new IllegalArgumentException("failure");

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> lateObject.ensure(() -> {
                    throw failure;
                }));

        Assertions.assertSame(failure, thrown);
        Assertions.assertFalse(lateObject.isInitialized());
        Assertions.assertEquals("recovered", lateObject.ensure(() -> "recovered"));
    }

    @Test
    void testEnsurePropagatesError() {
        LateObject<String> lateObject = new LateObject<>();
        AssertionError failure = new AssertionError("failure");

        AssertionError thrown = Assertions.assertThrows(
                AssertionError.class,
                () -> lateObject.ensure(() -> {
                    throw failure;
                }));

        Assertions.assertSame(failure, thrown);
        Assertions.assertFalse(lateObject.isInitialized());
    }

    @Test
    void testRejectsReentrantSet() {
        LateObject<String> lateObject = new LateObject<>();

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> lateObject.ensure(() -> {
                    lateObject.set("inner");
                    return "outer";
                }));

        Assertions.assertFalse(lateObject.isInitialized());
    }

    @Test
    void testRejectsRecursiveEnsure() {
        LateObject<String> lateObject = new LateObject<>();

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> lateObject.ensure(() -> lateObject.ensure(() -> "nested")));

        Assertions.assertFalse(lateObject.isInitialized());
    }

    @Test
    void testConcurrentEnsureComputesOnce() throws Exception {
        int taskCount = 16;
        LateObject<Object> lateObject = new LateObject<>();
        AtomicInteger invocationCount = new AtomicInteger();
        CountDownLatch ready = new CountDownLatch(taskCount);
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(taskCount);

        try {
            List<Future<Object>> futures = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(() -> {
                    ready.countDown();
                    Assertions.assertTrue(start.await(5, TimeUnit.SECONDS));
                    return lateObject.ensure(() -> {
                        invocationCount.incrementAndGet();
                        return new Object();
                    });
                }));
            }

            Assertions.assertTrue(ready.await(5, TimeUnit.SECONDS));
            start.countDown();

            Object expected = futures.get(0).get(5, TimeUnit.SECONDS);
            for (Future<Object> future : futures) {
                Assertions.assertSame(expected, future.get(5, TimeUnit.SECONDS));
            }
            Assertions.assertEquals(1, invocationCount.get());
        } finally {
            executor.shutdownNow();
            Assertions.assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
        }
    }
}
