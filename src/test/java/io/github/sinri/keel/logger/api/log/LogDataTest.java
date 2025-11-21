package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LogDataTest {

    @Test
    void testLogData() {
        LogData logData = new LogData();
        logData.level = LogLevel.INFO;
        logData.message = "test message";
        logData.timestamp = System.currentTimeMillis();
        logData.threadInfo = Thread.currentThread().getName();
        logData.classification = List.of("test");
        logData.exception = new RuntimeException("test exception");

        Assertions.assertEquals(LogLevel.INFO, logData.level);
        Assertions.assertEquals("test message", logData.message);
        Assertions.assertNotNull(logData.logContext);
        Assertions.assertNotNull(logData.extra);
        Assertions.assertEquals("test", logData.classification.get(0));
        Assertions.assertEquals("test exception", logData.exception.getMessage());
    }
}
