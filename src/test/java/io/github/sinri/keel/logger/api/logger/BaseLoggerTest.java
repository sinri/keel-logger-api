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

class BaseLoggerTest {

    @Test
    void testLog() {
        MockLogWriterAdapter logWriterAdapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", logWriterAdapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.info("test message");

        Assertions.assertEquals(1, logWriterAdapter.capturedTopics.size());
        Assertions.assertEquals(1, logWriterAdapter.capturedLogs.size());
        Assertions.assertEquals("test-topic", logWriterAdapter.capturedTopics.get(0));
        Assertions.assertEquals("test message", logWriterAdapter.capturedLogs.get(0).message());
        Assertions.assertEquals(LogLevel.INFO, logWriterAdapter.capturedLogs.get(0).level());
    }

    @Test
    void testLevelFilter() {
        MockLogWriterAdapter logWriterAdapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", logWriterAdapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.debug("debug message");
        Assertions.assertTrue(logWriterAdapter.capturedLogs.isEmpty());

        logger.info("info message");
        Assertions.assertEquals(1, logWriterAdapter.capturedLogs.size());
        Assertions.assertEquals("info message", logWriterAdapter.capturedLogs.get(0).message());
    }

    @Test
    void testTrace() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace("trace message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.TRACE, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("trace message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testDebug() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug("debug message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.DEBUG, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("debug message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testNotice() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.NOTICE);

        logger.notice("notice message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.NOTICE, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("notice message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testWarning() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.WARNING);

        logger.warning("warning message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.WARNING, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("warning message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testError() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.ERROR);

        logger.error("error message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.ERROR, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("error message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testFatal() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.FATAL);

        logger.fatal("fatal message");

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals(LogLevel.FATAL, adapter.capturedLogs.get(0).level());
        Assertions.assertEquals("fatal message", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testTraceWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace("trace message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testDebugWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug("debug message", c -> {
            c.put("key1", "value1");
            c.put("key2", "value2");
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value1", adapter.capturedLogs.get(0).context().toMap().get("key1"));
        Assertions.assertEquals("value2", adapter.capturedLogs.get(0).context().toMap().get("key2"));
    }

    @Test
    void testInfoWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.info("info message", c -> c.put("contextKey", "contextValue"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("contextValue", adapter.capturedLogs.get(0).context().toMap().get("contextKey"));
    }

    @Test
    void testNoticeWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.NOTICE);

        logger.notice("notice message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testWarningWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.WARNING);

        logger.warning("warning message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testErrorWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.ERROR);

        logger.error("error message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testFatalWithContext() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.FATAL);

        logger.fatal("fatal message", c -> c.put("key", "value"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testTraceWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.TRACE);

        logger.trace(log -> {
            log.message("custom trace");
            log.context("key", "value");
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom trace", adapter.capturedLogs.get(0).message());
        Assertions.assertEquals("value", adapter.capturedLogs.get(0).context().toMap().get("key"));
    }

    @Test
    void testDebugWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.DEBUG);

        logger.debug(log -> log.message("custom debug"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom debug", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testInfoWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.info(log -> {
            log.message("custom info");
            log.context("key1", "value1");
            log.exception(new RuntimeException("test"));
        });

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom info", adapter.capturedLogs.get(0).message());
        Assertions.assertEquals("value1", adapter.capturedLogs.get(0).context().toMap().get("key1"));
        Assertions.assertNotNull(adapter.capturedLogs.get(0).exception());
    }

    @Test
    void testNoticeWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.NOTICE);

        logger.notice(log -> log.message("custom notice"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom notice", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testWarningWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.WARNING);

        logger.warning(log -> log.message("custom warning"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom warning", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testErrorWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.ERROR);

        logger.error(log -> log.message("custom error"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom error", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testFatalWithConsumer() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.FATAL);

        logger.fatal(log -> log.message("custom fatal"));

        Assertions.assertEquals(1, adapter.capturedLogs.size());
        Assertions.assertEquals("custom fatal", adapter.capturedLogs.get(0).message());
    }

    @Test
    void testExceptionDeprecated() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
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
        BaseLogger logger = new BaseLogger("test-topic", adapter);
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
        BaseLogger logger = new BaseLogger("test-topic", adapter);
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
        BaseLogger logger = new BaseLogger("test-topic", adapter);
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

    @Test
    void testLevelFilteringAllLevels() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.INFO);

        logger.trace("trace");
        logger.debug("debug");
        Assertions.assertEquals(0, adapter.capturedLogs.size(), "Trace and debug should be filtered");

        logger.info("info");
        Assertions.assertEquals(1, adapter.capturedLogs.size());

        logger.notice("notice");
        Assertions.assertEquals(2, adapter.capturedLogs.size());

        logger.warning("warning");
        Assertions.assertEquals(3, adapter.capturedLogs.size());

        logger.error("error");
        Assertions.assertEquals(4, adapter.capturedLogs.size());

        logger.fatal("fatal");
        Assertions.assertEquals(5, adapter.capturedLogs.size());
    }

    @Test
    void testLogMethod() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
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
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.INFO);

        Log log = new Log();
        log.level(LogLevel.DEBUG).message("filtered log");

        logger.log(log);

        Assertions.assertEquals(0, adapter.capturedLogs.size(), "DEBUG level should be filtered");
    }

    @Test
    void testSilentLevel() {
        MockLogWriterAdapter adapter = new MockLogWriterAdapter();
        BaseLogger logger = new BaseLogger("test-topic", adapter);
        logger.visibleLevel(LogLevel.SILENT);

        logger.info("info message");
        logger.error("error message");
        logger.fatal("fatal message");

        Assertions.assertEquals(0, adapter.capturedLogs.size(), "SILENT level should filter all logs");
    }

    @Test
    void testSpecificLogSupplier() {
        BaseLogger logger = new BaseLogger("test-topic");

        Supplier<Log> supplier = logger.specificLogSupplier();
        Assertions.assertNotNull(supplier);

        Log log1 = supplier.get();
        Log log2 = supplier.get();
        Assertions.assertNotSame(log1, log2, "Should create new instances");
    }

    @Test
    void testAdapter() {
        BaseLogWriter adapter = BaseLogWriter.getInstance();
        BaseLogger logger = new BaseLogger("test-topic", adapter);

        Assertions.assertSame(adapter, logger.adapter());
    }

    @Test
    void testTopic() {
        BaseLogger logger = new BaseLogger("my-topic");

        Assertions.assertEquals("my-topic", logger.topic());
    }

    @Test
    void testVisibleLevelGetterSetter() {
        BaseLogger logger = new BaseLogger("test-topic");

        Assertions.assertEquals(LogLevel.INFO, logger.visibleLevel());

        Logger result = logger.visibleLevel(LogLevel.DEBUG);
        Assertions.assertSame(logger, result, "Should return self for chaining");
        Assertions.assertEquals(LogLevel.DEBUG, logger.visibleLevel());
    }

    @Test
    void testConstructorWithoutAdapter() {
        BaseLogger logger = new BaseLogger("test-topic");

        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(BaseLogWriter.getInstance(), logger.adapter());
        Assertions.assertEquals(LogLevel.INFO, logger.visibleLevel());
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
