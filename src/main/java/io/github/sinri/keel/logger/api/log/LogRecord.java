package io.github.sinri.keel.logger.api.log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogRecord {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");
    private final List<Content> contents;
    private long timestamp;

    public LogRecord() {
        this.timestamp = System.currentTimeMillis();
        this.contents = new ArrayList<>();
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
        this.contents.add(new Content(key, value));
        return this;
    }

    @Nullable
    protected final Object getContent(String key) {
        for (var content : this.contents) {
            if (content.key().equals(key)) return content.value();
        }
        return null;
    }

    @Nonnull
    public List<Content> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(this.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("[").append(zonedDateTime.format(formatter)).append("]");
        this.getContents().forEach(content -> {
            sb.append(" ").append(content.key()).append("=").append(content.value());
        });
        return sb.toString();
    }

    public record Content(@Nonnull String key, @Nonnull Object value) {
    }
}
