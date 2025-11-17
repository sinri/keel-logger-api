package io.github.sinri.keel.logger.api.logger;

import org.junit.jupiter.api.Test;

import java.util.List;

class EmbeddedLoggerTest {
    private final Logger eventLogRecorder = new BaseLogger(getClass().getSimpleName());

    @Test
    public void test() {
        eventLogRecorder.debug("test");
        eventLogRecorder.info("test", context -> {
            context.put("a", List.of("1", "2", "3"));
        });
        eventLogRecorder.exception(new RuntimeException("test"));
    }
}