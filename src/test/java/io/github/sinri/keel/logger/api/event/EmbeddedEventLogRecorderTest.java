package io.github.sinri.keel.logger.api.event;

import org.junit.jupiter.api.Test;

import java.util.List;

class EmbeddedEventLogRecorderTest {
    private final EventLogRecorder<String> eventLogRecorder = EventLogRecorder.embedded();

    @Test
    public void test() {
        eventLogRecorder.debug("test");
        eventLogRecorder.info("test", context -> {
            context.put("a", List.of("1", "2", "3"));
        });
        eventLogRecorder.exception(new RuntimeException("test"));
    }
}