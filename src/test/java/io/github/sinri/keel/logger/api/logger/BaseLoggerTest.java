package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.InstantLogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    private static class MockLogWriterAdapter implements InstantLogWriterAdapter {
        public List<String> capturedTopics = new ArrayList<>();
        public List<SpecificLog<?>> capturedLogs = new ArrayList<>();

        @Override
        public void accept(@NotNull String topic, @NotNull SpecificLog<?> log) {
            capturedTopics.add(topic);
            capturedLogs.add(log);
        }
    }
}
