package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.adapter.SilentLogWriter;
import io.github.sinri.keel.logger.api.log.Log;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class SilentLoggerFactoryTest {

    @Test
    void testGetInstance() {
        SilentLoggerFactory instance1 = SilentLoggerFactory.getInstance();
        SilentLoggerFactory instance2 = SilentLoggerFactory.getInstance();

        Assertions.assertNotNull(instance1);
        Assertions.assertSame(instance1, instance2, "Should return the same singleton instance");
    }

    @Test
    void testSharedAdapter() {
        SilentLoggerFactory factory = SilentLoggerFactory.getInstance();
        LogWriterAdapter adapter = factory.sharedAdapter();

        Assertions.assertNotNull(adapter);
        Assertions.assertSame(SilentLogWriter.getInstance(), adapter, "Should return SilentLogWriter instance");
    }

    @Test
    void testCreateLogger() {
        SilentLoggerFactory factory = SilentLoggerFactory.getInstance();
        Logger logger = factory.createLogger("test-topic");

        Assertions.assertNotNull(logger);
        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(factory.sharedAdapter(), logger.adapter());
        Assertions.assertNotNull(logger.specificLogSupplier());
    }

    @Test
    void testCreateLoggerWithSupplier() {
        SilentLoggerFactory factory = SilentLoggerFactory.getInstance();
        Supplier<Log> logSupplier = Log::new;
        SpecificLogger<Log> logger = factory.createLogger("test-topic", logSupplier);

        Assertions.assertNotNull(logger);
        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(factory.sharedAdapter(), logger.adapter());
        Assertions.assertSame(logSupplier, logger.specificLogSupplier());
    }

    @Test
    void testCreateLoggerWithCustomLogType() {
        SilentLoggerFactory factory = SilentLoggerFactory.getInstance();

        // Test with a custom SpecificLog type
        SpecificLogger<Log> logger = factory.createLogger("custom-topic", Log::new);

        Assertions.assertNotNull(logger);
        Assertions.assertEquals("custom-topic", logger.topic());

        // Verify the logger uses SilentLogWriter
        Log testLog = new Log();
        Assertions.assertDoesNotThrow(() -> {
            logger.log(testLog);
        });
    }
}

