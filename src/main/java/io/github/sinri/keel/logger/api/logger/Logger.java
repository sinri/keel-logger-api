package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import io.github.sinri.keel.logger.api.log.LogContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 日志记录器。
 *
 * @since 5.0.0
 */
public interface Logger {

    /**
     * 获取日志记录器的最低可见日志严重性等级。
     *
     * @return 本日志记录器的最低可见日志严重性等级
     */
    @NotNull
    LogLevel visibleLevel();

    /**
     * 设置日志记录器的最低可见日志严重性等级。
     *
     * @param level 最低可见日志严重性等级
     * @return 当前日志记录器
     */
    @NotNull
    Logger visibleLevel(@NotNull LogLevel level);

    /**
     * 获取日志记录器的主题。
     *
     * @return 日志记录器的主题
     */
    @NotNull
    String topic();

    /**
     * 获取日志记录器的主题记录消费者。
     *
     * @return 日志记录器的主题化日志记录处理器
     */
    @NotNull
    LogWriterAdapter consumer();

    /**
     * 记录一条跟踪级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void trace(@NotNull String message) {
        this.recordEvent(LogLevel.TRACE, message, null);
    }

    /**
     * 记录一条调试级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void debug(@NotNull String message) {
        this.recordEvent(LogLevel.DEBUG, message, null);
    }

    /**
     * 记录一条信息级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void info(@NotNull String message) {
        this.recordEvent(LogLevel.INFO, message, null);
    }

    /**
     * 记录一条通知级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void notice(@NotNull String message) {
        this.recordEvent(LogLevel.NOTICE, message, null);
    }

    /**
     * 记录一条警告级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void warning(@NotNull String message) {
        this.recordEvent(LogLevel.WARNING, message, null);
    }

    /**
     * 记录一条错误级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void error(@NotNull String message) {
        this.recordEvent(LogLevel.ERROR, message, null);
    }

    /**
     * 记录一条严重级别的日志记录。
     *
     * @param message 日志记录的消息内容
     */
    default void fatal(@NotNull String message) {
        this.recordEvent(LogLevel.FATAL, message, null);
    }

    /**
     * 记录一条跟踪级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void trace(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.TRACE, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条调试级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void debug(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.DEBUG, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条信息级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void info(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.INFO, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条通知级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void notice(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.NOTICE, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条警告级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void warning(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.WARNING, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条错误级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void error(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.ERROR, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条严重级别的日志记录，并设置上下文信息。
     *
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void fatal(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.recordEvent(LogLevel.FATAL, message, eventRecordBuilder -> {
            new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
        });
    }

    /**
     * 记录一条跟踪级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void trace(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.TRACE, null, building);
    }

    /**
     * 记录一条调试级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void debug(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.DEBUG, null, building);
    }

    /**
     * 记录一条信息级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void info(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.INFO, null, building);
    }

    /**
     * 记录一条通知级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void notice(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.NOTICE, null, building);
    }

    /**
     * 记录一条警告级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void warning(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.WARNING, null, building);
    }

    /**
     * 记录一条错误级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void error(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.ERROR, null, building);
    }

    /**
     * 记录一条严重级别的日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void fatal(@NotNull Consumer<Log> building) {
        this.recordEvent(LogLevel.FATAL, null, building);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的日志记录。
     *
     * @param throwable       异常信息
     * @param level           日志严重性等级
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void exception(@NotNull Throwable throwable, @Nullable LogLevel level, @Nullable String message, @Nullable Consumer<LogContext> contextConsumer) {
        recordEvent(eventRecordBuilder -> {
            eventRecordBuilder.exception(throwable);
            eventRecordBuilder.level(Objects.requireNonNullElse(level, LogLevel.ERROR));
            if (message != null) {
                eventRecordBuilder.message(message);
            }
            if (contextConsumer != null) {
                new LogContext(contextConsumer).toMap().forEach(eventRecordBuilder::context);
            }
        });
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的日志记录。
     *
     * @param throwable 异常信息
     */
    default void exception(@NotNull Throwable throwable) {
        this.exception(throwable, null, null, null);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的日志记录。
     *
     * @param throwable 异常信息
     * @param message   日志记录的消息内容
     */
    default void exception(@NotNull Throwable throwable, @NotNull String message) {
        this.exception(throwable, null, message, null);
    }

    /**
     * 基于一个发生的异常，记录一条错误级别的日志记录。
     *
     * @param throwable       异常信息
     * @param message         日志记录的消息内容
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     */
    default void exception(@NotNull Throwable throwable, @NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.exception(throwable, null, message, contextConsumer);
    }

    /**
     * 记录一条日志记录，并设置日志记录的构建器。
     *
     * @param building 日志记录的构建器
     */
    default void recordEvent(@NotNull Consumer<Log> building) {
        Log log = new Log();
        building.accept(log);
        this.recordEvent(log);
    }

    /**
     * 记录一条日志记录。
     *
     * @param log 日志记录
     */
    default void recordEvent(@NotNull Log log) {
        consumer().accept(topic(), log);
    }

    /**
     * 记录一条日志记录。
     *
     * @param level    日志严重性等级
     * @param message  日志记录的消息内容
     * @param building 日志记录的构建器
     */
    private void recordEvent(@NotNull LogLevel level, @Nullable String message, @Nullable Consumer<Log> building) {
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
