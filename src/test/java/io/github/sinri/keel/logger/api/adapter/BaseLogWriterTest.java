package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class BaseLogWriterTest {

    @Test
    void testRender() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("[INFO] test-topic"));
        Assertions.assertTrue(rendered.contains("test message"));
        Assertions.assertTrue(rendered.contains("key:\tvalue"));
        Assertions.assertTrue(rendered.contains("test"));
    }

    @Test
    void testRenderWithException() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        RuntimeException exception = new RuntimeException("Test exception");
        log.exception(exception);

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("Test exception"));
        Assertions.assertTrue(rendered.contains("RuntimeException"));
    }

    @Test
    void testRenderWithNestedException() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        RuntimeException cause = new RuntimeException("Cause exception");
        RuntimeException exception = new RuntimeException("Outer exception", cause);
        log.exception(exception);

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("Outer exception"));
        Assertions.assertTrue(rendered.contains("Cause exception"));
    }

    @Test
    void testRenderContext() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.context("key1", "value1");
        log.context("key2", "value2");
        log.context("key3", 123);

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("key1:\tvalue1"));
        Assertions.assertTrue(rendered.contains("key2:\tvalue2"));
        Assertions.assertTrue(rendered.contains("key3:\t123"));
    }

    @Test
    void testRenderClassification() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.classification(List.of("cat1", "cat2", "cat3"));

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("cat1,cat2,cat3"));
    }

    @Test
    void testRenderExtra() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.extra().put("extraKey", "extraValue");
        log.extra().put("extraNumber", 42);

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("Extra as following"));
        Assertions.assertTrue(rendered.contains("extraKey:\textraValue"));
        Assertions.assertTrue(rendered.contains("extraNumber:\t42"));
    }

    @Test
    void testRenderWithEmptyMessage() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.message("   "); // Blank message

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Blank message should not appear
    }

    @Test
    void testRenderWithNullException() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        // exception is null by default

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Exception should not appear if null
    }

    @Test
    void testRenderWithNullClassification() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.classification(null);

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Classification should not appear if null
    }

    @Test
    void testRenderWithEmptyClassification() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.classification(List.of());

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Empty classification should not appear
    }

    @Test
    void testRenderWithEmptyContext() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        // Context is empty by default

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Context section should not appear if empty
    }

    @Test
    void testRenderWithEmptyExtra() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        // Extra is empty by default

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Extra section should not appear if empty
    }

    @Test
    void testRenderWithAllLevels() {
        BaseLogWriter writer = new BaseLogWriter();
        String[] levels = {"TRACE", "DEBUG", "INFO", "NOTICE", "WARNING", "ERROR", "FATAL"};

        for (String levelName : levels) {
            TestSpecificLog log = new TestSpecificLog();
            log.level(LogLevel.valueOf(levelName));
            log.message("Message for " + levelName);

            String rendered = writer.render("test-topic", log);
            Assertions.assertTrue(rendered.contains("[" + levelName + "]"));
        }
    }

    @Test
    void testRenderContextWithNullValue() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.context("nullKey", null);

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("nullKey:\tnull"));
    }

    @Test
    void testRenderContextWithComplexValue() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.context("list", List.of("a", "b", "c"));
        log.context("map", Map.of("key", "value"));

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Complex values should be rendered as strings
    }

    @Test
    void testRenderClassificationMultiple() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        log.classification(List.of("module", "component", "action"));

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains("module,component,action"));
    }

    @Test
    void testRenderTimestamp() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();

        String rendered = writer.render("test-topic", log);
        Assertions.assertNotNull(rendered);
        // Timestamp should be rendered in the format
        Assertions.assertTrue(rendered.contains("㏒"));
    }

    @Test
    void testRenderThreadInfo() {
        BaseLogWriter writer = new BaseLogWriter();
        TestSpecificLog log = new TestSpecificLog();
        String threadInfo = log.threadInfo();

        String rendered = writer.render("test-topic", log);
        Assertions.assertTrue(rendered.contains(threadInfo));
    }

    private static class TestSpecificLog extends SpecificLog<TestSpecificLog> {
        public TestSpecificLog() {
            super();
            this.level(LogLevel.INFO)
                .message("test message")
                .context("key", "value")
                .classification(List.of("test"));
        }
    }
}
