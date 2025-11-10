package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.log.LogRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public interface EventLogRecorder<T> {
    static EventLogRecorder<String> embedded() {
        return EmbeddedEventLogRecorder.getInstance();
    }

    /**
     * Get the event render instance,
     * which is used to render the event record to {@link String} and {@link LogRecord}.
     *
     * @return the event render instance.
     */
    EventRender<T> getEventRender();

    default void trace(@Nonnull String message) {
        this.recordEvent(LogLevel.TRACE, message, null);
    }

    default void debug(@Nonnull String message) {
        this.recordEvent(LogLevel.DEBUG, message, null);
    }

    default void info(@Nonnull String message) {
        this.recordEvent(LogLevel.INFO, message, null);
    }

    default void notice(@Nonnull String message) {
        this.recordEvent(LogLevel.NOTICE, message, null);
    }

    default void warning(@Nonnull String message) {
        this.recordEvent(LogLevel.WARNING, message, null);
    }

    default void error(@Nonnull String message) {
        this.recordEvent(LogLevel.ERROR, message, null);
    }

    default void fatal(@Nonnull String message) {
        this.recordEvent(LogLevel.FATAL, message, null);
    }

    default void trace(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.TRACE, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void debug(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void info(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.INFO, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void notice(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void warning(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.WARNING, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void error(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.ERROR, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void fatal(@Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.recordEvent(LogLevel.FATAL, message, eventRecordBuilder -> {
            new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void trace(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.TRACE, building);
    }

    default void debug(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.DEBUG, building);
    }

    default void info(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.INFO, building);
    }

    default void notice(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.NOTICE, building);
    }

    default void warning(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.WARNING, building);
    }

    default void error(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.ERROR, building);
    }

    default void fatal(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.FATAL, building);
    }

    default void exception(@Nonnull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<Context> contextConsumer) {
        recordEvent(eventRecordBuilder -> {
            eventRecordBuilder.exception(throwable);
            eventRecordBuilder.level(Objects.requireNonNullElse(level, LogLevel.ERROR));
            if (message != null) {
                eventRecordBuilder.message(message);
            }
            if (contextConsumer != null) {
                new Context(contextConsumer).toMap().forEach(eventRecordBuilder::context);
            }
        });
    }

    default void exception(@Nonnull Throwable throwable) {
        this.exception(throwable, null, null, null);
    }

    default void exception(@Nonnull Throwable throwable, @Nonnull String message) {
        this.exception(throwable, null, message, null);
    }

    default void exception(@Nonnull Throwable throwable, @Nonnull String message, @Nonnull Consumer<Context> contextConsumer) {
        this.exception(throwable, null, message, contextConsumer);
    }

    default void recordEvent(@Nonnull Consumer<EventRecord> building) {
        EventRecord eventRecord = new EventRecord();
        building.accept(eventRecord);
        this.recordEvent(eventRecord);
    }

    /**
     * Record an event record.
     *
     * @param eventRecord the event record.
     */
    void recordEvent(@Nonnull EventRecord eventRecord);

    private void recordEvent(@Nonnull LogLevel level, @Nullable Consumer<EventRecord> building) {
        this.recordEvent(builder -> {
            if (building != null) {
                building.accept(builder);
            }
            builder.level(level);
        });
    }

    private void recordEvent(@Nonnull LogLevel level, @Nonnull String message, @Nullable Consumer<EventRecord> building) {
        this.recordEvent(builder -> {
            if (building != null) {
                building.accept(builder);
            }
            builder.level(level);
            builder.message(message);
        });
    }

}
