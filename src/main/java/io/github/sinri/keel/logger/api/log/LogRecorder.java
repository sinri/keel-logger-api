package io.github.sinri.keel.logger.api.log;

import javax.annotation.Nonnull;

public interface LogRecorder {
    static LogRecorder embedded() {
        return EmbeddedLogRecorder.getInstance();
    }

    /**
     * Record a log record, to the target such as STDOUT, any other kind of output stream, and so on.
     *
     * @param record the log record to record
     */
    void recordLog(@Nonnull LogRecord record);

    default void recordLog(@Nonnull LogRecordCompatible recordCompatible) {
        recordLog(recordCompatible.toLogRecord());
    }
}
