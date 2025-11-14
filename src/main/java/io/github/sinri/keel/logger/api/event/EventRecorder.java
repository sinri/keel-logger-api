package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 事件日志记录器。
 *
 * @since 5.0.0
 */
public interface EventRecorder {

    /**
     * 获取事件日志记录器的最低可见日志严重性等级。
     *
     * @return 本日志记录器的最低可见日志严重性等级
     */
    @NotNull
    LogLevel visibleLevel();

    /**
     * 设置事件日志记录器的最低可见日志严重性等级。
     *
     * @param level 最低可见日志严重性等级
     * @return 当前事件日志记录器
     */
    @NotNull
    EventRecorder visibleLevel(@NotNull LogLevel level);

    /**
     * 获取事件日志记录器的主题。
     *
     * @return 事件日志记录器的主题
     */
    @NotNull
    String topic();

    /**
     * 获取事件日志记录器的主题记录消费者。
     *
     * @return 事件日志记录器的主题化日志记录处理器
     */
    @NotNull
    TopicRecordConsumer consumer();

    /**
     * 记录一条跟踪级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void trace(@NotNull String message) {
        this.recordEvent(LogLevel.TRACE, message, null);
    }

    /**
     * 记录一条调试级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void debug(@NotNull String message) {
        this.recordEvent(LogLevel.DEBUG, message, null);
    }

    /**
     * 记录一条信息级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void info(@NotNull String message) {
        this.recordEvent(LogLevel.INFO, message, null);
    }

    /**
     * 记录一条通知级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void notice(@NotNull String message) {
        this.recordEvent(LogLevel.NOTICE, message, null);
    }

    /**
     * 记录一条警告级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void warning(@NotNull String message) {
        this.recordEvent(LogLevel.WARNING, message, null);
    }

    /**
     * 记录一条错误级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void error(@NotNull String message) {
        this.recordEvent(LogLevel.ERROR, message, null);
    }

    /**
     * 记录一条严重级别的事件日志记录。
     *
     * @param message 事件日志记录的消息内容
     */
    default void fatal(@NotNull String message) {
        this.recordEvent(LogLevel.FATAL, message, null);
    }

    /**
     * 记录一条跟踪级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void trace(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.TRACE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条调试级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void debug(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条信息级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void info(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.INFO, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条通知级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void notice(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条警告级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void warning(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.WARNING, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条错误级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void error(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.ERROR, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条严重级别的事件日志记录，并设置上下文信息。
     *
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void fatal(@NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.recordEvent(LogLevel.FATAL, message, eventRecordBuilder -> {
            new EventRecordContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条跟踪级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void trace(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.TRACE, null, building);
    }

    /**
     * 记录一条调试级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void debug(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.DEBUG, null, building);
    }

    /**
     * 记录一条信息级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void info(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.INFO, null, building);
    }

    /**
     * 记录一条通知级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void notice(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.NOTICE, null, building);
    }

    /**
     * 记录一条警告级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void warning(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.WARNING, null, building);
    }

    /**
     * 记录一条错误级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void error(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.ERROR, null, building);
    }

    /**
     * 记录一条严重级别的事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void fatal(@NotNull Consumer<EventRecord> building) {
        this.recordEvent(LogLevel.FATAL, null, building);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的事件日志记录。
     *
     * @param throwable       异常信息
     * @param level           日志严重性等级
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void exception(@NotNull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<EventRecordContext> contextConsumer) {
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

    /**
     * 基于一个发生的异常，记录一条错误级别的事件日志记录。
     *
     * @param throwable 异常信息
     */
    default void exception(@NotNull Throwable throwable) {
        this.exception(throwable, null, null, null);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的事件日志记录。
     *
     * @param throwable 异常信息
     * @param message   事件日志记录的消息内容
     */
    default void exception(@NotNull Throwable throwable, @NotNull String message) {
        this.exception(throwable, null, message, null);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的事件日志记录。
     *
     * @param throwable       异常信息
     * @param message         事件日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void exception(@NotNull Throwable throwable, @NotNull String message, @NotNull Consumer<EventRecordContext> contextConsumer) {
        this.exception(throwable, null, message, contextConsumer);
    }

    /**
     * 记录一条事件日志记录，并设置事件日志记录的构建器。
     *
     * @param building 事件日志记录的构建器
     */
    default void recordEvent(@NotNull Consumer<EventRecord> building) {
        EventRecord eventRecord = new EventRecord();
        building.accept(eventRecord);
        this.recordEvent(eventRecord);
    }

    /**
     * 记录一条事件日志记录。
     *
     * @param eventRecord 事件日志记录
     */
    default void recordEvent(@NotNull EventRecord eventRecord) {
        consumer().accept(topic(), eventRecord);
    }

    /**
     * 记录一条事件日志记录。
     *
     * @param level    日志严重性等级
     * @param message  事件日志记录的消息内容
     * @param building 事件日志记录的构建器
     */
    private void recordEvent(@NotNull LogLevel level, @Nullable String message, @Nullable Consumer<EventRecord> building) {
        if (visibleLevel() != LogLevel.SILENT) {
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

}
