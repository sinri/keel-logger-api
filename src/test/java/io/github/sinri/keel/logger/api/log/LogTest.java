package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogTest {

    @Test
    void testNoArgsConstructor() {
        Log log = new Log();

        Assertions.assertNotNull(log);
        Assertions.assertNotNull(log.timestamp());
        Assertions.assertTrue(log.timestamp() > 0);
        Assertions.assertNotNull(log.threadInfo());
        Assertions.assertNotNull(log.context());
        Assertions.assertNotNull(log.extra());
        Assertions.assertEquals(LogLevel.INFO, log.level());
    }

    @Test
    void testCopyConstructor() {
        Log original = new Log();
        original.level(LogLevel.ERROR)
                .message("Original message")
                .context("key1", "value1")
                .classification(java.util.List.of("cat1", "cat2"));

        RuntimeException exception = new RuntimeException("Test exception");
        original.exception(exception);

        Log copy = new Log(original);

        Assertions.assertEquals(original.timestamp(), copy.timestamp());
        Assertions.assertEquals(original.threadInfo(), copy.threadInfo());
        Assertions.assertEquals(original.level(), copy.level());
        Assertions.assertEquals(original.message(), copy.message());
        Assertions.assertEquals(original.exception(), copy.exception());
        Assertions.assertEquals(original.classification(), copy.classification());
        Assertions.assertEquals(original.context().toMap().get("key1"), copy.context().toMap().get("key1"));
    }

    @Test
    void testCopyConstructorWithSpecificLog() {
        // Create a test SpecificLog implementation
        TestSpecificLog original = new TestSpecificLog();
        original.level(LogLevel.WARNING)
                .message("Test message")
                .context("key", "value");

        Log copy = new Log(original);

        Assertions.assertEquals(original.timestamp(), copy.timestamp());
        Assertions.assertEquals(original.level(), copy.level());
        Assertions.assertEquals(original.message(), copy.message());
        Assertions.assertEquals(original.context().toMap().get("key"), copy.context().toMap().get("key"));
    }

    @Test
    void testLogInheritsFromSpecificLog() {
        Log log = new Log();

        // Test that Log can use all SpecificLog methods
        Log result = log.level(LogLevel.DEBUG)
                        .message("Test message")
                        .context("key", "value");

        Assertions.assertSame(log, result, "Should return self for chaining");
        Assertions.assertEquals(LogLevel.DEBUG, log.level());
        Assertions.assertEquals("Test message", log.message());
        Assertions.assertEquals("value", log.context().toMap().get("key"));
    }

    // Test implementation of SpecificLog for testing
    private static class TestSpecificLog extends SpecificLog<TestSpecificLog> {
        public TestSpecificLog() {
            super();
        }
    }
}

