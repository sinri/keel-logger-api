package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.log.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SilentLogWriterTest {

    @Test
    void testGetInstance() {
        SilentLogWriter instance1 = SilentLogWriter.getInstance();
        SilentLogWriter instance2 = SilentLogWriter.getInstance();

        Assertions.assertNotNull(instance1);
        Assertions.assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    @Test
    void testAccept() {
        SilentLogWriter writer = SilentLogWriter.getInstance();

        // Create a log with various fields
        Log log = new Log();
        log.level(LogLevel.ERROR)
           .message("Test message")
           .context("key", "value");

        // Should not throw any exception and do nothing
        Assertions.assertDoesNotThrow(() -> {
            writer.accept("test-topic", log);
        });

        // Verify the log is unchanged (accept should not modify the log)
        Assertions.assertEquals(LogLevel.ERROR, log.level());
        Assertions.assertEquals("Test message", log.message());
        Assertions.assertEquals("value", log.context().toMap().get("key"));
    }

    @Test
    void testAcceptWithNullMessage() {
        SilentLogWriter writer = SilentLogWriter.getInstance();

        Log log = new Log();
        log.level(LogLevel.INFO);
        // message is null

        Assertions.assertDoesNotThrow(() -> {
            writer.accept("test-topic", log);
        });
    }

    @Test
    void testAcceptWithException() {
        SilentLogWriter writer = SilentLogWriter.getInstance();

        Log log = new Log();
        log.level(LogLevel.ERROR)
           .message("Error occurred")
           .exception(new RuntimeException("Test exception"));

        Assertions.assertDoesNotThrow(() -> {
            writer.accept("test-topic", log);
        });
    }
}

