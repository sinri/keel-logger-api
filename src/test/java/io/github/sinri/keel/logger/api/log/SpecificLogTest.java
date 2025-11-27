package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class SpecificLogTest {

    @Test
    void testNoArgsConstructor() {
        TestSpecificLog log = new TestSpecificLog();

        Assertions.assertNotNull(log.timestamp());
        Assertions.assertTrue(log.timestamp() > 0);
        Assertions.assertNotNull(log.threadInfo());
        Assertions.assertNotNull(log.context());
        Assertions.assertNotNull(log.extra());
        Assertions.assertEquals(LogLevel.INFO, log.level());
        Assertions.assertNull(log.message());
        Assertions.assertNull(log.exception());
        Assertions.assertNull(log.classification());
    }

    @Test
    void testCopyConstructor() {
        TestSpecificLog original = new TestSpecificLog();
        original.level(LogLevel.ERROR)
                .message("Original message")
                .context("key1", "value1")
                .classification(List.of("cat1", "cat2"));

        RuntimeException exception = new RuntimeException("Test exception");
        original.exception(exception);

        TestSpecificLog copy = new TestSpecificLog(original);

        Assertions.assertEquals(original.timestamp(), copy.timestamp());
        Assertions.assertEquals(original.threadInfo(), copy.threadInfo());
        Assertions.assertEquals(original.level(), copy.level());
        Assertions.assertEquals(original.message(), copy.message());
        Assertions.assertEquals(original.exception(), copy.exception());
        Assertions.assertEquals(original.classification(), copy.classification());
        Assertions.assertEquals(original.context().toMap().get("key1"), copy.context().toMap().get("key1"));
    }

    @Test
    void testGetters() {
        TestSpecificLog log = new TestSpecificLog();

        long timestamp = log.timestamp();
        String threadInfo = log.threadInfo();
        LogContext context = log.context();
        Map<String, Object> extra = log.extra();
        LogLevel level = log.level();

        Assertions.assertTrue(timestamp > 0);
        Assertions.assertNotNull(threadInfo);
        Assertions.assertNotNull(context);
        Assertions.assertNotNull(extra);
        Assertions.assertNotNull(level);
    }

    @Test
    void testLevelSetter() {
        TestSpecificLog log = new TestSpecificLog();

        TestSpecificLog result = log.level(LogLevel.DEBUG);

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals(LogLevel.DEBUG, log.level());
    }

    @Test
    void testMessageSetter() {
        TestSpecificLog log = new TestSpecificLog();

        TestSpecificLog result = log.message("Test message");

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals("Test message", log.message());
    }

    @Test
    void testExceptionSetter() {
        TestSpecificLog log = new TestSpecificLog();
        RuntimeException exception = new RuntimeException("Test");

        TestSpecificLog result = log.exception(exception);

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertSame(exception, log.exception());
    }

    @Test
    void testContextSetterWithKeyValue() {
        TestSpecificLog log = new TestSpecificLog();

        TestSpecificLog result = log.context("key", "value");

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals("value", log.context().toMap().get("key"));
    }

    @Test
    void testContextSetterWithConsumer() {
        TestSpecificLog log = new TestSpecificLog();

        TestSpecificLog result = log.context(c -> {
            c.put("key1", "value1");
            c.put("key2", "value2");
        });

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals("value1", log.context().toMap().get("key1"));
        Assertions.assertEquals("value2", log.context().toMap().get("key2"));
    }

    @Test
    void testClassificationSetter() {
        TestSpecificLog log = new TestSpecificLog();
        List<String> classification = List.of("cat1", "cat2", "cat3");

        TestSpecificLog result = log.classification(classification);

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals(classification, log.classification());
    }

    @Test
    void testChaining() {
        TestSpecificLog log = new TestSpecificLog();
        RuntimeException exception = new RuntimeException("Test");
        List<String> classification = List.of("test");

        TestSpecificLog result = log
                .level(LogLevel.WARNING)
                .message("Chained message")
                .exception(exception)
                .context("key1", "value1")
                .context("key2", "value2")
                .classification(classification);

        Assertions.assertSame(log, result);
        Assertions.assertEquals(LogLevel.WARNING, log.level());
        Assertions.assertEquals("Chained message", log.message());
        Assertions.assertSame(exception, log.exception());
        Assertions.assertEquals("value1", log.context().toMap().get("key1"));
        Assertions.assertEquals("value2", log.context().toMap().get("key2"));
        Assertions.assertEquals(classification, log.classification());
    }

    @Test
    void testNullClassification() {
        TestSpecificLog log = new TestSpecificLog();
        log.classification(List.of("test"));

        TestSpecificLog result = log.classification(null);

        Assertions.assertSame(log, result);
        Assertions.assertNull(log.classification());
    }

    @Test
    void testNullContextValue() {
        TestSpecificLog log = new TestSpecificLog();

        log.context("key", null);

        Assertions.assertNull(log.context().toMap().get("key"));
    }

    @Test
    void testExtra() {
        TestSpecificLog log = new TestSpecificLog();

        Map<String, Object> extra = log.extra();
        Assertions.assertNotNull(extra);
        Assertions.assertTrue(extra.isEmpty());

        // extra() returns a mutable map
        extra.put("test", "value");
        Assertions.assertEquals("value", log.extra().get("test"));
    }

    @Test
    void testGetImplementation() {
        TestSpecificLog log = new TestSpecificLog();

        TestSpecificLog implementation = log.getImplementation();

        Assertions.assertSame(log, implementation);
    }

    @Test
    void testContextWithNullValue() {
        TestSpecificLog log = new TestSpecificLog();

        log.context("nullKey", null);

        Assertions.assertTrue(log.context().toMap().containsKey("nullKey"));
        Assertions.assertNull(log.context().toMap().get("nullKey"));
    }

    @Test
    void testEmptyClassification() {
        TestSpecificLog log = new TestSpecificLog();

        log.classification(new ArrayList<>());

        Assertions.assertNotNull(log.classification());
        Assertions.assertTrue(log.classification().isEmpty());
    }

    // Test implementation of SpecificLog
    private static class TestSpecificLog extends SpecificLog<TestSpecificLog> {
        public TestSpecificLog() {
            super();
        }

        public TestSpecificLog(SpecificLog<?> specificLog) {
            super(specificLog);
        }
    }
}

