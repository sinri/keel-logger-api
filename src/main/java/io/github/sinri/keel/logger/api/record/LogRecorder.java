package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.api.writer.LogWriter;

import javax.annotation.Nonnull;

/**
 * This interface defines the contract for recording log records.
 *
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface LogRecorder<R> {
    @Nonnull
    static LogRecorder<String> embedded(@Nonnull String topic) {
        return new EmbeddedLogRecorder(topic);
    }

    LogRecordRender<R> render();

    @Nonnull
    String topic();

    @Nonnull
    LogWriter<R> writer();

    /**
     * Record a log record, to the target such as STDOUT, any other kind of output stream, and so on.
     *
     * @param record the log record to record
     */
    default void recordLog(@Nonnull LogRecord record) {
        var s = render().render(topic(), record);
        writer().write(s);
    }
}
