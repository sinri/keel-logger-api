package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
