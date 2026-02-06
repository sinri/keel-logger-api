package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.LogContext;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 特定日志记录器。
 * <p>
 * 本接口定义了“在某一主题（topic）下记录特定日志记录”的通用能力。
 * <p>
 * 为避免不必要的对象创建，本接口的若干默认方法会在构建日志之前进行日志级别前置检查。
 *
 * @param <T> 特定日志记录
 * @since 5.0.0
 */
@NullMarked
public interface SpecificLogger<T extends SpecificLog<T>> {
    /**
     * 获取用于创建特定日志记录实例的构造器（供应器）。
     *
     * @return 特定日志记录构造器
     */
    Supplier<T> specificLogSupplier();

    /**
     * 获取日志写入适配器。
     * <p>
     * 日志写入适配器负责接收日志主题与日志记录，并将其写入到具体介质（如标准输出、文件、远程日志系统等）。
     *
     * @return 日志写入适配器
     */
    LogWriterAdapter adapter();

    /**
     * 获取最低可见日志级别。
     * <p>
     * 当记录的日志级别低于该值时，日志将被忽略。
     *
     * @return 最低可见日志级别
     */
    LogLevel visibleLevel();

    /**
     * 设置最低可见日志级别。
     *
     * @param level 最低可见日志级别
     * @return 当前日志记录器实例
     */
    SpecificLogger<T> visibleLevel(LogLevel level);

    /**
     * 获取日志主题。
     *
     * @return 日志主题
     */
    String topic();

    private boolean isLogEnabled(LogLevel level) {
        return visibleLevel() != LogLevel.SILENT && !level.isNegligibleThan(visibleLevel());
    }

    /**
     * 前置检查是否需要记录指定级别的日志，若不需要则直接返回，避免日志对象的创建与组装。
     */
    private void logIfEnabled(LogLevel level, Consumer<T> consumer) {
        if (!isLogEnabled(level)) return;
        T t = specificLogSupplier().get();
        consumer.accept(t);
        t.level(level);
        this.log(t);
    }

    /**
     * 记录一条特定日志记录。
     *
     * @param specificLog 特定日志记录
     */
    default void log(T specificLog) {
        if (visibleLevel() == LogLevel.SILENT || specificLog.level().isNegligibleThan(visibleLevel())) return;
        adapter().accept(topic(), specificLog);
    }

    /**
     * 以指定日志级别记录一条简单消息。
     * <p>
     * 若日志级别不可见，则不会构建日志记录实例。
     *
     * @param level   日志级别
     * @param message 消息模板
     * @param args    模板参数（将用于 {@link String#formatted(Object...)}）
     */
    default void message(LogLevel level, String message, Object... args) {
        if (visibleLevel() == LogLevel.SILENT || level.isNegligibleThan(visibleLevel())) return;
        adapter().accept(topic(), specificLogSupplier().get().message(message.formatted(args)));
    }

    /**
     * 记录一条 TRACE 级别日志消息。
     *
     * @param message 消息内容
     */
    default void trace(String message) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message));
    }

    /**
     * 记录一条 DEBUG 级别日志消息。
     *
     * @param message 消息内容
     */
    default void debug(String message) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message));
    }

    /**
     * 记录一条 INFO 级别日志消息。
     *
     * @param message 消息内容
     */
    default void info(String message) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message));
    }

    /**
     * 记录一条 NOTICE 级别日志消息。
     *
     * @param message 消息内容
     */
    default void notice(String message) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message));
    }

    /**
     * 记录一条 WARNING 级别日志消息。
     *
     * @param message 消息内容
     */
    default void warning(String message) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message));
    }

    /**
     * 记录一条 ERROR 级别日志消息。
     *
     * @param message 消息内容
     */
    default void error(String message) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message));
    }

    /**
     * 记录一条 FATAL 级别日志消息。
     *
     * @param message 消息内容
     */
    default void fatal(String message) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message));
    }

    /**
     * 记录一条 TRACE 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void trace(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message).context(contextConsumer));
    }

    /**
     * 记录一条 DEBUG 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void debug(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message).context(contextConsumer));
    }

    /**
     * 记录一条 INFO 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void info(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message).context(contextConsumer));
    }

    /**
     * 记录一条 NOTICE 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void notice(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message).context(contextConsumer));
    }

    /**
     * 记录一条 WARNING 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void warning(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message)
                                                      .context(contextConsumer));
    }

    /**
     * 记录一条 ERROR 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void error(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message).context(contextConsumer));
    }

    /**
     * 记录一条 FATAL 级别日志消息，并附带上下文信息。
     *
     * @param message         消息内容
     * @param contextConsumer 上下文构建逻辑
     */
    default void fatal(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message).context(contextConsumer));
    }

    /**
     * 以 TRACE 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void trace(Consumer<T> building) {
        this.logIfEnabled(LogLevel.TRACE, log -> {
            building.accept(log);
            log.level(LogLevel.TRACE);
        });
    }

    /**
     * 以 DEBUG 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void debug(Consumer<T> building) {
        this.logIfEnabled(LogLevel.DEBUG, log -> {
            building.accept(log);
            log.level(LogLevel.DEBUG);
        });
    }

    /**
     * 以 INFO 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void info(Consumer<T> building) {
        this.logIfEnabled(LogLevel.INFO, log -> {
            building.accept(log);
            log.level(LogLevel.INFO);
        });
    }

    /**
     * 以 NOTICE 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void notice(Consumer<T> building) {
        this.logIfEnabled(LogLevel.NOTICE, log -> {
            building.accept(log);
            log.level(LogLevel.NOTICE);
        });
    }

    /**
     * 以 WARNING 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void warning(Consumer<T> building) {
        this.logIfEnabled(LogLevel.WARNING, log -> {
            building.accept(log);
            log.level(LogLevel.WARNING);
        });
    }

    /**
     * 以 ERROR 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void error(Consumer<T> building) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            building.accept(log);
            log.level(LogLevel.ERROR);
        });
    }

    /**
     * 以 FATAL 级别构建并记录一条日志。
     *
     * @param building 日志构建逻辑
     */
    default void fatal(Consumer<T> building) {
        this.logIfEnabled(LogLevel.FATAL, log -> {
            building.accept(log);
            log.level(LogLevel.FATAL);
        });
    }

    /**
     * 基于本实例，获得对应的 {@link Logger} 实例，应保证每次返回的结果等效。
     *
     * @return 可复用的 {@link Logger} 实例，具备和本实例同样的 topic 和 {@link LogWriterAdapter}；一般还应同步最低可见日志级别
     */
    Logger normalizedLogger();
}
