package io.github.sinri.keel.logger.api.record;

import org.junit.jupiter.api.Test;

class EmbeddedLoggingRecorderTest {
    LoggingRecorder loggingRecorder;

    public EmbeddedLoggingRecorderTest() {
        loggingRecorder = LoggingRecorder.embedded(getClass().getSimpleName());
    }

    @Test
    public void test() {
        LoggingRecord record = new LoggingRecord();
        record.content("a", "b");
        loggingRecorder.recordLog(record);
    }
}