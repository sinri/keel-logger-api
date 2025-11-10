package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LogRecord {
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
    public LogRecord addContent(@Nonnull String key, @Nonnull String value) {
        this.logRecordContents.add(new LogRecordContent(key, value));
        return this;
    }

    @Nullable
    protected final Object getContent(String key) {
        for (var content : this.logRecordContents) {
            if (content.key().equals(key)) return content.value();
        }
        return null;
    }

    @Nonnull
    public List<LogRecordContent> getContents() {
        return logRecordContents;
    }

}
