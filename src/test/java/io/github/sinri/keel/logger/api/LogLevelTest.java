package io.github.sinri.keel.logger.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogLevelTest {

    @Test
    void testOrder() {
        Assertions.assertTrue(LogLevel.TRACE.ordinal() < LogLevel.DEBUG.ordinal());
        Assertions.assertTrue(LogLevel.DEBUG.ordinal() < LogLevel.INFO.ordinal());
        Assertions.assertTrue(LogLevel.INFO.ordinal() < LogLevel.NOTICE.ordinal());
        Assertions.assertTrue(LogLevel.NOTICE.ordinal() < LogLevel.WARNING.ordinal());
        Assertions.assertTrue(LogLevel.WARNING.ordinal() < LogLevel.ERROR.ordinal());
        Assertions.assertTrue(LogLevel.ERROR.ordinal() < LogLevel.FATAL.ordinal());
        Assertions.assertTrue(LogLevel.FATAL.ordinal() < LogLevel.SILENT.ordinal());
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
