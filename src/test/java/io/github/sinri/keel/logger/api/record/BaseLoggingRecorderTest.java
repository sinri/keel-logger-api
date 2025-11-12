package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.base.record.BaseLoggingRecorder;
import org.junit.jupiter.api.Test;

class BaseLoggingRecorderTest {
    LoggingRecorder loggingRecorder;

    public BaseLoggingRecorderTest() {
        loggingRecorder = new BaseLoggingRecorder(getClass().getSimpleName());
    }

    @Test
    public void test() {
        LoggingRecord record = new LoggingRecord();
        record.content("a", "b");
        loggingRecorder.recordLog(record);
    }
}