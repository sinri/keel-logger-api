package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;

/**
 * This interface defines the contract for recording log records.
 *
 * @since 5.0.0
 */
public interface LogRecorder {
    @Nonnull
    static LogRecorder embedded(@Nonnull String topic) {
        return new EmbeddedLogRecorder(topic);
    }

    @Nonnull
    String topic();

    //    @Nonnull
    //    Adapter<LogRecord, R> adapter();

    //    /**
    //     * Record a log record, to the target such as STDOUT, any other kind of output stream, and so on.
    //     *
    //     * @param record the log record to record
    //     */
    //    default void recordLog(@Nonnull LogRecord record) {
    //        adapter().renderAndWrite(topic(), record);
    //    }

    void recordLog(@Nonnull LogRecord record);
}
