package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecordContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @param <T> the type of the mapped implementation of {@link IssueRecord}
 * @since 5.0.0
 */
public interface IssueRecorder<T extends IssueRecord<T>> {
    @Nonnull
    Supplier<T> issueRecordSupplier();

    @Nonnull
    TopicRecordConsumer consumer();

    @Nonnull
    LogLevel visibleLevel();

    @Nonnull
    IssueRecorder<T> visibleLevel(@Nonnull LogLevel level);

    @Nonnull
    String topic();

    default void recordIssue(@Nonnull T issueRecord) {
        consumer().accept(topic(), issueRecord.toEventRecord());
    }

    default void recordIssue(@Nonnull Consumer<T> issueRecordConsumer) {
        T t = issueRecordSupplier().get();
        issueRecordConsumer.accept(t);
        this.recordIssue(t);
    }

    private void recordIssue(@Nonnull LogLevel level, @Nullable String message, @Nullable Consumer<T> building) {
        if (level.isEnoughSeriousAs(visibleLevel())) {
            this.recordIssue(builder -> {
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


    default void trace(@Nonnull String message) {
        this.recordIssue(LogLevel.TRACE, message, null);
    }

    default void debug(@Nonnull String message) {
        this.recordIssue(LogLevel.DEBUG, message, null);
    }

    default void info(@Nonnull String message) {
        this.recordIssue(LogLevel.INFO, message, null);
    }

    default void notice(@Nonnull String message) {
        this.recordIssue(LogLevel.NOTICE, message, null);
    }

    default void warning(@Nonnull String message) {
        this.recordIssue(LogLevel.WARNING, message, null);
    }

    default void error(@Nonnull String message) {
        this.recordIssue(LogLevel.ERROR, message, null);
    }

    default void fatal(@Nonnull String message) {
        this.recordIssue(LogLevel.FATAL, message, null);
    }

    default void trace(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.TRACE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void debug(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void info(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.INFO, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void notice(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void warning(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.WARNING, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void error(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.ERROR, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void fatal(@Nonnull String message, @Nonnull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.FATAL, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void trace(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.TRACE, null, building);
    }

    default void debug(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.DEBUG, null, building);
    }

    default void info(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.INFO, null, building);
    }

    default void notice(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.NOTICE, null, building);
    }

    default void warning(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.WARNING, null, building);
    }

    default void error(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.ERROR, null, building);
    }

    default void fatal(@Nonnull Consumer<T> building) {
        this.recordIssue(LogLevel.FATAL, null, building);
    }

    default void exception(@Nonnull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<EventRecordContext> contextConsumer) {
        recordIssue(issueRecord -> {
            issueRecord.exception(throwable);
            issueRecord.level(Objects.requireNonNullElse(level, LogLevel.ERROR));
            if (message != null) {
                issueRecord.message(message);
            }
            if (contextConsumer != null) {
                new EventRecordContext(contextConsumer).toMap().forEach(issueRecord::context);
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


}
