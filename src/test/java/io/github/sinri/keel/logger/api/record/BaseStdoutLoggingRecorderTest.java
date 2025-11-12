package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.base.record.BaseStdoutLoggingRecorder;
import org.junit.jupiter.api.Test;

class BaseStdoutLoggingRecorderTest {
    LoggingRecorder loggingRecorder;

    public BaseStdoutLoggingRecorderTest() {
        loggingRecorder = new BaseStdoutLoggingRecorder(getClass().getSimpleName());
    }

    @Test
    public void test() {
        LoggingRecord record = new LoggingRecord();
        record.content("a", "b");
        loggingRecorder.recordLog(record);
    }
}