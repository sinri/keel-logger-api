package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.log.Log;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class LoggerFactoryTest {

    @Test
    void testBaseLoggerFactory() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();
        Assertions.assertNotNull(factory);
        Assertions.assertEquals(BaseLogWriter.getInstance(), factory.sharedAdapter());
    }

    @Test
    void testCreateLogger() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();
        Logger logger = factory.createLogger("test-topic");
        Assertions.assertNotNull(logger);
        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertEquals(factory.sharedAdapter(), logger.adapter());
    }

    @Test
    void testCreateLoggerWithSupplier() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();
        Supplier<Log> logSupplier = Log::new;
        SpecificLogger<Log> logger = factory.createLogger("test-topic", logSupplier);

        Assertions.assertNotNull(logger);
        Assertions.assertEquals("test-topic", logger.topic());
        Assertions.assertSame(factory.sharedAdapter(), logger.adapter());
        Assertions.assertSame(logSupplier, logger.specificLogSupplier());
    }

    @Test
    void testCreatedLoggerAndSpecificLoggerUseSameAdapter() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();

        Logger logger = factory.createLogger("logger-topic");
        SpecificLogger<Log> specificLogger = factory.createLogger("specific-topic", Log::new);

        Assertions.assertSame(factory.sharedAdapter(), logger.adapter());
        Assertions.assertSame(factory.sharedAdapter(), specificLogger.adapter());
        Assertions.assertSame(logger.adapter(), specificLogger.adapter(),
                "Both loggers should use the same shared adapter");
    }

    @Test
    void testCreateLoggerWithCustomSupplier() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();

        Supplier<Log> customSupplier = () -> {
            Log log = new Log();
            log.context("source", "custom");
            return log;
        };

        SpecificLogger<Log> logger = factory.createLogger("custom-topic", customSupplier);

        Assertions.assertNotNull(logger);
        Assertions.assertEquals("custom-topic", logger.topic());
        Assertions.assertSame(customSupplier, logger.specificLogSupplier());

        // Verify the custom supplier is used
        Log log = logger.specificLogSupplier().get();
        Assertions.assertEquals("custom", log.context().toMap().get("source"));
    }

    @Test
    void testMultipleLoggersFromSameFactory() {
        LoggerFactory factory = BaseLoggerFactory.getInstance();

        Logger logger1 = factory.createLogger("topic1");
        Logger logger2 = factory.createLogger("topic2");
        SpecificLogger<Log> logger3 = factory.createLogger("topic3", Log::new);

        Assertions.assertEquals("topic1", logger1.topic());
        Assertions.assertEquals("topic2", logger2.topic());
        Assertions.assertEquals("topic3", logger3.topic());

        // All should use the same adapter
        Assertions.assertSame(logger1.adapter(), logger2.adapter());
        Assertions.assertSame(logger2.adapter(), logger3.adapter());
    }

    @Test
    void testFactorySingleton() {
        LoggerFactory factory1 = BaseLoggerFactory.getInstance();
        LoggerFactory factory2 = BaseLoggerFactory.getInstance();

        Assertions.assertSame(factory1, factory2, "Factory should be a singleton");
    }
}
