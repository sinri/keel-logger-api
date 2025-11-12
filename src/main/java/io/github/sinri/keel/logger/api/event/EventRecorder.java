package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * The interface represents a recorder for recording {@link EventRecord}.
 *
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface EventRecorder<R> {
    //    @Nonnull
    //    static EventRecorder<String> embedded(@Nonnull String topic) {
    //        return new BaseStringToStdoutEventRecorder(topic);
    //    }

    @Nonnull
    LogLevel visibleLevel();

    @Nonnull
    EventRecorder<R> visibleLevel(@Nonnull LogLevel level);

    @Nonnull
    String topic();

    @Nonnull
    Adapter<EventRecord, R> adapter();

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

    default void trace(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.TRACE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void debug(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void info(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.INFO, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void notice(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void warning(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.WARNING, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void error(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.ERROR, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void fatal(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.FATAL, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void trace(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.TRACE, null, building);
    }

    default void debug(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.DEBUG, null, building);
    }

    default void info(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.INFO, null, building);
    }

    default void notice(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.NOTICE, null, building);
    }

    default void warning(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.WARNING, null, building);
    }

    default void error(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.ERROR, null, building);
    }

    default void fatal(@Nonnull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.FATAL, null, building);
    }

    default void exception(@Nonnull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<EventRecordContext> contextConsumer) {
        recordEvent(eventRecordBuilder -> {
            eventRecordBuilder.exception(throwable);
            eventRecordBuilder.level(Objects.requireNonNullElse(level, LogLevel.ERROR));
            if (message != null) {
                eventRecordBuilder.message(message);
            }
            if (contextConsumer != null) {
                new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
            }
        });
    }

    default void exception(@Nonnull Throwable throwable) {
        this.exception(throwable, null, null, null);
    }

    default void exception(@Nonnull Throwable throwable, @Nonnull String message) {
        this.exception(throwable, null, message, null);
    }

    default void exception(@Nonnull Throwable throwable, @Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
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
    default void recordEvent(@Nonnull EventRecord eventRecord) {
        adapter().renderAndWrite(topic(), eventRecord);
    }

    private void recordEvent(@Nonnull LogLevel level, @Nullable String message, @Nullable Consumer<EventRecord> building) {
        if (level.isEnoughSeriousAs(visibleLevel())) {
            this.recordEvent(builder -> {
                if (building != null) {
                    building.accept(builder);
                }
                builder.level(level);
                if (message != null) {
                    builder.message(message);
                }
            });
        }
    }


}
