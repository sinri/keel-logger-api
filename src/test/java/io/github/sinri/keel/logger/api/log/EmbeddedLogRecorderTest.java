package io.github.sinri.keel.logger.api.log;

import org.junit.jupiter.api.Test;

class EmbeddedLogRecorderTest {
    LogRecorder logRecorder;

    public EmbeddedLogRecorderTest() {
        logRecorder = EmbeddedLogRecorder.getInstance();
    }

    @Test
    public void test() {
        LogRecord record = new LogRecord();
        record.addContent("a", "b");
        logRecorder.recordLog(record);
    }
}