package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;

class SilentLoggerTest {

    @Test
    void testVisibleLevel() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Assertions.assertEquals(LogLevel.SILENT, logger.visibleLevel());
    }

    @Test
    void testVisibleLevelSetterThrowsException() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Assertions.assertThrows(RuntimeException.class, () -> {
            logger.visibleLevel(LogLevel.INFO);
        }, "Should throw exception when trying to change visible level");
    }

    @Test
    void testTopic() throws Exception {
        SilentLogger logger = createSilentLogger("my-topic");

        Assertions.assertEquals("my-topic", logger.topic());
    }

    @Test
    void testSpecificLogSupplier() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Supplier<Log> supplier = logger.specificLogSupplier();
        Assertions.assertNotNull(supplier);

        Log log1 = supplier.get();
        Log log2 = supplier.get();
        Assertions.assertNotSame(log1, log2, "Should create new instances");
    }

    @Test
    void testAdapter() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        LogWriterAdapter adapter = logger.adapter();
        Assertions.assertNotNull(adapter);
    }

    @Test
    void testAllLogMethodsDoNothing() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        // All log methods should not throw exceptions and do nothing
        Assertions.assertDoesNotThrow(() -> {
            logger.trace("trace message");
            logger.debug("debug message");
            logger.info("info message");
            logger.notice("notice message");
            logger.warning("warning message");
            logger.error("error message");
            logger.fatal("fatal message");
        });
    }

    @Test
    void testLogMethodsWithContext() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Assertions.assertDoesNotThrow(() -> {
            logger.trace("trace", c -> c.put("key", "value"));
            logger.debug("debug", c -> c.put("key", "value"));
            logger.info("info", c -> c.put("key", "value"));
            logger.notice("notice", c -> c.put("key", "value"));
            logger.warning("warning", c -> c.put("key", "value"));
            logger.error("error", c -> c.put("key", "value"));
            logger.fatal("fatal", c -> c.put("key", "value"));
        });
    }

    @Test
    void testLogMethodsWithConsumer() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Assertions.assertDoesNotThrow(() -> {
            logger.trace(log -> log.message("trace"));
            logger.debug(log -> log.message("debug"));
            logger.info(log -> log.message("info"));
            logger.notice(log -> log.message("notice"));
            logger.warning(log -> log.message("warning"));
            logger.error(log -> log.message("error"));
            logger.fatal(log -> log.message("fatal"));
        });
    }

    @Test
    void testLogMethod() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");

        Log log = new Log();
        log.level(LogLevel.ERROR).message("test");

        // Should not throw exception and do nothing
        Assertions.assertDoesNotThrow(() -> {
            logger.log(log);
        });
    }

    @Test
    void testExceptionMethods() throws Exception {
        SilentLogger logger = createSilentLogger("test-topic");
        RuntimeException exception = new RuntimeException("test");

        Assertions.assertDoesNotThrow(() -> {
            logger.error(log -> log.exception(exception));
            logger.error(log -> log.exception(exception).message("message"));
            logger.error(log -> log.exception(exception).message("message").context("key", "value"));
            logger.error(log -> {
                log.exception(exception);
                log.message("custom");
            });
        });
    }

    /**
     * Create a SilentLogger instance using reflection since the constructor is private.
     */
    private SilentLogger createSilentLogger(String topic) throws Exception {
        Constructor<SilentLogger> constructor = SilentLogger.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        return constructor.newInstance(topic);
    }
}

