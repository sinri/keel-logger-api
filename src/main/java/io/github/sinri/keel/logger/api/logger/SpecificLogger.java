package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.LogContext;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 特定日志记录器
 *
 * @param <T> 特定日志记录
 * @since 5.0.0
 */
@NullMarked
public interface SpecificLogger<T extends SpecificLog<T>> {
    Supplier<T> specificLogSupplier();

    LogWriterAdapter adapter();

    LogLevel visibleLevel();

    SpecificLogger<T> visibleLevel(LogLevel level);

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

    default void log(T specificLog) {
        if (visibleLevel() == LogLevel.SILENT || specificLog.level().isNegligibleThan(visibleLevel())) return;
        adapter().accept(topic(), specificLog);
    }

    default void trace(String message) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message));
    }

    default void debug(String message) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message));
    }

    default void info(String message) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message));
    }

    default void notice(String message) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message));
    }

    default void warning(String message) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message));
    }

    default void error(String message) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message));
    }

    default void fatal(String message) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message));
    }

    default void trace(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message).context(contextConsumer));
    }

    default void debug(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message).context(contextConsumer));
    }

    default void info(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message).context(contextConsumer));
    }

    default void notice(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message).context(contextConsumer));
    }

    default void warning(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message)
                                                      .context(contextConsumer));
    }

    default void error(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message).context(contextConsumer));
    }

    default void fatal(String message, Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message).context(contextConsumer));
    }

    default void trace(Consumer<T> building) {
        this.logIfEnabled(LogLevel.TRACE, log -> {
            building.accept(log);
            log.level(LogLevel.TRACE);
        });
    }

    default void debug(Consumer<T> building) {
        this.logIfEnabled(LogLevel.DEBUG, log -> {
            building.accept(log);
            log.level(LogLevel.DEBUG);
        });
    }

    default void info(Consumer<T> building) {
        this.logIfEnabled(LogLevel.INFO, log -> {
            building.accept(log);
            log.level(LogLevel.INFO);
        });
    }

    default void notice(Consumer<T> building) {
        this.logIfEnabled(LogLevel.NOTICE, log -> {
            building.accept(log);
            log.level(LogLevel.NOTICE);
        });
    }

    default void warning(Consumer<T> building) {
        this.logIfEnabled(LogLevel.WARNING, log -> {
            building.accept(log);
            log.level(LogLevel.WARNING);
        });
    }

    default void error(Consumer<T> building) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            building.accept(log);
            log.level(LogLevel.ERROR);
        });
    }

    default void fatal(Consumer<T> building) {
        this.logIfEnabled(LogLevel.FATAL, log -> {
            building.accept(log);
            log.level(LogLevel.FATAL);
        });
    }

    /**
     * 基于本实例，获得对应的 {@link Logger} 实例，应保证每次返回的结果等效。
     *
     * @return 可复用的 {@link Logger} 实例，具备和本实例同样的 topic 和 LogWriterAdapter；一般，还应同步 visibleLogLevel。
     */
    Logger normalizedLogger();
}
