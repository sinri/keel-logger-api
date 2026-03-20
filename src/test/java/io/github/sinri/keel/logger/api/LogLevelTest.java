package io.github.sinri.keel.logger.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogLevelTest {

    @Test
    void testOrder() {
        Assertions.assertTrue(LogLevel.TRACE.severity() < LogLevel.DEBUG.severity());
        Assertions.assertTrue(LogLevel.DEBUG.severity() < LogLevel.INFO.severity());
        Assertions.assertTrue(LogLevel.INFO.severity() < LogLevel.NOTICE.severity());
        Assertions.assertTrue(LogLevel.NOTICE.severity() < LogLevel.WARNING.severity());
        Assertions.assertTrue(LogLevel.WARNING.severity() < LogLevel.ERROR.severity());
        Assertions.assertTrue(LogLevel.ERROR.severity() < LogLevel.FATAL.severity());
        Assertions.assertTrue(LogLevel.FATAL.severity() < LogLevel.SILENT.severity());
    }

    @Test
    void isEnoughSeriousAs() {
        Assertions.assertTrue(LogLevel.INFO.isEnoughSeriousAs(LogLevel.INFO));
        Assertions.assertTrue(LogLevel.INFO.isEnoughSeriousAs(LogLevel.DEBUG));
        Assertions.assertFalse(LogLevel.INFO.isEnoughSeriousAs(LogLevel.WARNING));
    }

    @Test
    void isNegligibleThan() {
        Assertions.assertFalse(LogLevel.INFO.isNegligibleThan(LogLevel.INFO));
        Assertions.assertFalse(LogLevel.INFO.isNegligibleThan(LogLevel.DEBUG));
        Assertions.assertTrue(LogLevel.INFO.isNegligibleThan(LogLevel.WARNING));
    }
}
