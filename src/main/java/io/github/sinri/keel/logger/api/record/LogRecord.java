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
public class LogRecord {
    @Nonnull
    private final List<LogRecordContent> logRecordContents;
    private long timestamp;

    public LogRecord() {
        this.timestamp = System.currentTimeMillis();
        this.logRecordContents = new ArrayList<>();
    }

    @Nonnull
    public LogRecord timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public final long timestamp() {
        return timestamp;
    }

    @Nonnull
    public LogRecord content(@Nonnull String key, @Nonnull String value) {
        this.logRecordContents.add(new LogRecordContent(key, value));
        return this;
    }

    @Nullable
    protected final Object content(String key) {
        for (var content : this.logRecordContents) {
            if (content.key().equals(key)) return content.value();
        }
        return null;
    }

    @Nonnull
    public List<LogRecordContent> contents() {
        return logRecordContents;
    }

}
