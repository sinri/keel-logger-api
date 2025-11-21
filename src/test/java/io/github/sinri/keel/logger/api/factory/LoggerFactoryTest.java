package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.logger.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
