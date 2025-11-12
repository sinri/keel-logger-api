package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.base.event.BaseStringToStdoutEventRecorder;
import org.junit.jupiter.api.Test;

import java.util.List;

class EmbeddedEventRecorderTest {
    private final EventRecorder<String> eventLogRecorder = new BaseStringToStdoutEventRecorder(getClass().getSimpleName());

    @Test
    public void test() {
        eventLogRecorder.debug("test");
        eventLogRecorder.info("test", context -> {
            context.put("a", List.of("1", "2", "3"));
        });
        eventLogRecorder.exception(new RuntimeException("test"));
    }
}