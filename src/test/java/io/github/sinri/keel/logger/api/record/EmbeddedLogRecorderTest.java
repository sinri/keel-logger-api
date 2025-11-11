package io.github.sinri.keel.logger.api.record;

import org.junit.jupiter.api.Test;

class EmbeddedLogRecorderTest {
    LogRecorder logRecorder;

    public EmbeddedLogRecorderTest() {
        logRecorder = LogRecorder.embedded(getClass().getSimpleName());
    }

    @Test
    public void test() {
        LogRecord record = new LogRecord();
        record.content("a", "b");
        logRecorder.recordLog(record);
    }
}