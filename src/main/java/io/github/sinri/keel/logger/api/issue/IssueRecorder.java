package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecordContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 特定问题日志记录器
 *
 * @param <T> 特定问题日志记录
 * @since 5.0.0
 */
public interface IssueRecorder<T extends IssueRecord<T>> {
    @NotNull
    Supplier<T> issueRecordSupplier();

    @NotNull
    TopicRecordConsumer consumer();

    @NotNull
    LogLevel visibleLevel();

    @NotNull
    IssueRecorder<T> visibleLevel(@NotNull LogLevel level);

    @NotNull
    String topic();

    default void recordIssue(@NotNull T issueRecord) {
        consumer().accept(topic(), issueRecord.toEventRecord());
    }

    default void recordIssue(@NotNull Consumer<T> issueRecordConsumer) {
        T t = issueRecordSupplier().get();
        issueRecordConsumer.accept(t);
        this.recordIssue(t);
    }

    private void recordIssue(@NotNull LogLevel level, @Nullable String message, @Nullable Consumer<T> building) {
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


    default void trace(@NotNull String message) {
        this.recordIssue(LogLevel.TRACE, message, null);
    }

    default void debug(@NotNull String message) {
        this.recordIssue(LogLevel.DEBUG, message, null);
    }

    default void info(@NotNull String message) {
        this.recordIssue(LogLevel.INFO, message, null);
    }

    default void notice(@NotNull String message) {
        this.recordIssue(LogLevel.NOTICE, message, null);
    }

    default void warning(@NotNull String message) {
        this.recordIssue(LogLevel.WARNING, message, null);
    }

    default void error(@NotNull String message) {
        this.recordIssue(LogLevel.ERROR, message, null);
    }

    default void fatal(@NotNull String message) {
        this.recordIssue(LogLevel.FATAL, message, null);
    }

    default void trace(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.TRACE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void debug(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void info(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.INFO, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void notice(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void warning(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.WARNING, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void error(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.ERROR, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void fatal(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordIssue(LogLevel.FATAL, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    default void trace(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.TRACE, null, building);
    }

    default void debug(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.DEBUG, null, building);
    }

    default void info(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.INFO, null, building);
    }

    default void notice(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.NOTICE, null, building);
    }

    default void warning(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.WARNING, null, building);
    }

    default void error(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.ERROR, null, building);
    }

    default void fatal(@NotNull Consumer<T> building) {
        this.recordIssue(LogLevel.FATAL, null, building);
    }

    default void exception(@NotNull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<EventRecordContext> contextConsumer) {
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

    default void exception(@NotNull Throwable throwable) {
        this.exception(throwable, null, null, null);
    }

    default void exception(@NotNull Throwable throwable, @NotNull String message) {
        this.exception(throwable, null, message, null);
    }

    default void exception(@NotNull Throwable throwable, @NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.exception(throwable, null, message, contextConsumer);
    }


}
