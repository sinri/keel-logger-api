package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class BaseSpecificLoggerTest {

    @Test
    void testConstructorWithAdapter() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);

        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(adapter, logger.adapter());
        Assertions.assertNotNull(logger.specificLogSupplier());
        Assertions.assertEquals(LogLevel.INFO, logger.visibleLevel());
    }

    @Test
    void testConstructorWithoutAdapter() {
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new);

        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(BaseLogWriter.getInstance(), logger.adapter());
        Assertions.assertNotNull(logger.specificLogSupplier());
        Assertions.assertEquals(LogLevel.INFO, logger.visibleLevel());
    }

    @Test
    void testSpecificLogSupplier() {
        Supplier<Log> supplier = Log::new;
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", supplier);

        Assertions.assertSame(supplier, logger.specificLogSupplier());

        Log log1 = logger.specificLogSupplier().get();
        Log log2 = logger.specificLogSupplier().get();
        Assertions.assertNotSame(log1, log2, "Should create new instances");
    }

    @Test
    void testAdapter() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);

        Assertions.assertSame(adapter, logger.adapter());
    }

    @Test
    void testVisibleLevelGetterSetter() {
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new);

        Assertions.assertEquals(LogLevel.INFO, logger.visibleLevel());

        SpecificLogger<Log> result = logger.visibleLevel(LogLevel.DEBUG);
        Assertions.assertSame(logger, result, "Should return self for chaining");
        Assertions.assertEquals(LogLevel.DEBUG, logger.visibleLevel());
    }

    @Test
    void testTopic() {
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("my-topic", Log::new);

        Assertions.assertEquals("my-topic", logger.topic());
    }

    @Test
    void testNormalizedLogger() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        Logger normalized = logger.normalizedLogger();

        Assertions.assertNotNull(normalized);
        Assertions.assertEquals("test-topic", normalized.topic());
        Assertions.assertSame(adapter, normalized.adapter());
        Assertions.assertEquals(LogLevel.DEBUG, normalized.visibleLevel());

        Logger normalized2 = logger.normalizedLogger();
        Assertions.assertSame(normalized, normalized2, "Should return the same instance");
    }

    @Test
    void testNormalizeVisibleLevelPropagation() {
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new);
        logger.visibleLevel(LogLevel.DEBUG);

        Logger normalized = logger.normalizedLogger();

        Assertions.assertEquals(LogLevel.DEBUG, normalized.visibleLevel(), "Normalized logger should inherit visibleLevel from BaseSpecificLogger");
    }

    @Test
    void testNormalizeVisibleLevelSyncAfterCreation() {
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new);
        Logger normalized = logger.normalizedLogger();
        Assertions.assertEquals(LogLevel.INFO, normalized.visibleLevel());

        logger.visibleLevel(LogLevel.ERROR);
        Assertions.assertEquals(LogLevel.ERROR, normalized.visibleLevel(), "Normalized logger should stay in sync with BaseSpecificLogger's visibleLevel");
    }

    @Test
    void testTrace() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace("trace message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.TRACE, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("trace message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testDebug() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug("debug message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.DEBUG, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("debug message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testInfo() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.info("info message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.INFO, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("info message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testNotice() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.NOTICE);

        logger.notice("notice message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.NOTICE, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("notice message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testWarning() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.WARNING);

        logger.warning("warning message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.WARNING, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("warning message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testError() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.ERROR);

        logger.error("error message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.ERROR, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("error message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testFatal() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.FATAL);

        logger.fatal("fatal message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.FATAL, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("fatal message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testTraceWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace("trace message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("trace message", adapter.capturedLogs.get(0).message());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testDebugWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug("debug message", c -> c.put("key1", "value1"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value1", adapter.capturedLogs.get(0).context().toMap().get("key1"));
    }

    @Test
    void testInfoWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.info("info message", c -> {
            c.put("key1", "value1");
            c.put("key2", "value2");
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value1", adapter.capturedLogs.get(0).context().toMap().get("key1"));
        Assertions.assertEquals("value2", adapter.capturedLogs.get(0).context().toMap().get("key2"));
    }

    @Test
    void testTraceWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace(log -> {
            log.message("custom message");
            log.context("key", "value");
            log.exception(new RuntimeException("test"));
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Log captured = (Log) adapter.capturedLogs.get(0);
        Assertions.assertEquals(LogLevel.TRACE, captured.level());
        Assertions.assertEquals("custom message", captured.message());
        Assertions.assertEquals("value", captured.context().toMap().get("key"));
        Assertions.assertNotNull(captured.exception());
    }

    @Test
    void testDebugWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug(log -> log.message("debug with consumer"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("debug with consumer", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testLevelFiltering() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.trace("trace message");
        logger.debug("debug message");
        Assertions.assertEquals(0, adapter.capturedLogs.size(), "Trace and debug should be filtered");

        logger.info("info message");
        Assertions.assertEquals(1, adapter.capturedLogs.size());

        logger.notice("notice message");
        Assertions.assertEquals(2, adapter.capturedLogs.size());

        logger.warning("warning message");
        Assertions.assertEquals(3, adapter.capturedLogs.size());

        logger.error("error message");
        Assertions.assertEquals(4, adapter.capturedLogs.size());

        logger.fatal("fatal message");
        Assertions.assertEquals(5, adapter.capturedLogs.size());
    }

    @Test
    void testLogMethod() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.INFO);

        Log log = new Log();
        log.level(LogLevel.INFO).message("direct log");

        logger.log(log);

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("direct log", adapter.capturedLogs.get(0).message());
        Assertions.assertEquals("test-topic", adapter.capturedTopics.get(0));
    }

    @Test
    void testLogMethodWithFilteredLevel() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.INFO);

        Log log = new Log();
        log.level(LogLevel.DEBUG).message("filtered log");

        logger.log(log);

        Assertions.assertEquals(0, adapter.capturedLogs.size(), "DEBUG level should be filtered");
    }

    @Test
    void testSilentLevel() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.SILENT);

        logger.info("info message");
        logger.error("error message");
        logger.fatal("fatal message");

        Assertions.assertEquals(0, adapter.capturedLogs.size(), "SILENT level should filter all logs");
    }

    @Test
    void testExceptionDeprecatedMethods() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.ERROR);

        RuntimeException exception = new RuntimeException("Test exception");

        logger.error(log -> log.exception(exception));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.ERROR, adapter.capturedLogs.get(0).level());
        Assertions.assertSame(exception, adapter.capturedLogs.get(0).exception());
    }

    @Test
    void testExceptionWithMessage() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.ERROR);

        RuntimeException exception = new RuntimeException("Test exception");

        logger.error(log -> log.exception(exception).message("Exception message"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("Exception message", adapter.capturedLogs.get(0).message());
        Assertions.assertSame(exception, adapter.capturedLogs.get(0).exception());
    }

    @Test
    void testExceptionWithMessageAndContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.ERROR);

        RuntimeException exception = new RuntimeException("Test exception");

        logger.error(log -> log.exception(exception).message("Exception message").context("errorCode", "500"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("Exception message", adapter.capturedLogs.get(0).message());
        Assertions.assertSame(exception, adapter.capturedLogs.get(0).exception());
        Assertions.assertEquals("500", adapter.capturedLogs.get(0).context().toMap().get("errorCode"));
    }

    @Test
    void testExceptionWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseSpecificLogger<Log> logger = new BaseSpecificLogger<>("test-topic", Log::new, adapter);
        logger.visibleLevel(LogLevel.ERROR);

        RuntimeException exception = new RuntimeException("Test exception");

        logger.error(log -> {
            log.exception(exception);
            log.message("Custom message");
            log.context("key", "value");
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("Custom message", adapter.capturedLogs.get(0).message());
        Assertions.assertSame(exception, adapter.capturedLogs.get(0).exception());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    private static class MockLogWriterAdapter implements LogWriterAdapter {
        public List<String> capturedTopics = new ArrayList<>();
        public List<SpecificLog<?>> capturedLogs = new ArrayList<>();

        @Override
        public void accept(@NotNull String topic, @NotNull SpecificLog<?> log) {
            capturedTopics.add(topic);
            capturedLogs.add(log);
        }
    }
}

