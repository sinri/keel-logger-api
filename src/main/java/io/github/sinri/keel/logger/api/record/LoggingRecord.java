package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a log record with timestamp and contents.
 *
 * @since 5.0.0
 */
public class LoggingRecord {
    @Nonnull
    private final List<LoggingRecordContent> loggingRecordContents;
    private long timestamp;

    public LoggingRecord() {
        this.timestamp = System.currentTimeMillis();
        this.loggingRecordContents = new ArrayList<>();
    }

    @Nonnull
    public LoggingRecord timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public final long timestamp() {
        return timestamp;
    }

    @Nonnull
    public LoggingRecord content(@Nonnull String key, @Nonnull String value) {
        this.loggingRecordContents.add(new LoggingRecordContent(key, value));
        return this;
    }

    @Nullable
    protected final Object content(String key) {
        for (var content : this.loggingRecordContents) {
            if (content.key().equals(key)) return content.value();
        }
        return null;
    }

    @Nonnull
    public List<LoggingRecordContent> contents() {
        return loggingRecordContents;
    }

}
