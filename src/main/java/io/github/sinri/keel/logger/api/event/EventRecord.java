package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * The class represents a log mapped to an event with timestamp, level, message, context, exception.
 *
 * @since 5.0.0
 */
public class EventRecord {
    public final static String MapKeyContext = "context";
    public final static String MapKeyMessage = "message";
    public final static String MapKeyClassification = "classification";
    public final static String MapKeyLevel = "level";
    public final static String MapKeyException = "exception";
    @Nonnull
    private final String threadInfo;
    @Nonnull
    private final EventRecordContext eventRecordContext;
    private final long timestamp;
    @Nonnull
    private final Map<String, Object> extra = new TreeMap<>();
    @Nullable
    private String message;
    @Nonnull
    private LogLevel level;
    @Nullable
    private Throwable exception;
    @Nullable
    private List<String> classification;

    public EventRecord() {
        this.timestamp = System.currentTimeMillis();
        this.level = LogLevel.INFO;
        this.threadInfo = Thread.currentThread().toString();
        this.eventRecordContext = new EventRecordContext();
    }

    public EventRecord level(@Nonnull LogLevel level) {
        this.level = level;
        return this;
    }

    public EventRecord message(@Nonnull String message) {
        this.message = message;
        return this;
    }

    public EventRecord exception(@Nonnull Throwable throwable) {
        this.exception = throwable;
        return this;
    }

    public EventRecord context(@Nonnull String contextKey, @Nullable Object contextValue) {
        this.eventRecordContext.put(contextKey, contextValue);
        return this;
    }

    public EventRecord context(@Nonnull Consumer<EventRecordContext> contextConsumer) {
        contextConsumer.accept(this.eventRecordContext);
        return this;
    }


    public long timestamp() {
        return timestamp;
    }

    @Nonnull
    public String threadInfo() {
        return threadInfo;
    }

    @Nonnull
    public EventRecordContext context() {
        return eventRecordContext;
    }

    @Nullable
    public String message() {
        return message;
    }

    @Nonnull
    public LogLevel level() {
        return level;
    }

    @Nullable
    public Throwable exception() {
        return exception;
    }

    @Nullable
    public List<String> classification() {
        return classification;
    }

    @Nonnull
    public EventRecord classification(@Nullable List<String> classification) {
        this.classification = classification;
        return this;
    }

    @Nonnull
    protected EventRecord extra(@Nonnull String key, @Nullable Object value) {
        this.extra.put(key, value);
        return this;
    }

    @Nonnull
    public Map<String, Object> extra() {
        return extra;
    }
}
