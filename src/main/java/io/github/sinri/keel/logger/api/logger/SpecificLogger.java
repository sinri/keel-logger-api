package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.LogContext;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 特定日志记录器
 *
 * @param <T> 特定日志记录
 * @since 5.0.0
 */
public interface SpecificLogger<T extends SpecificLog<T>> {
    @NotNull
    Supplier<T> specificLogSupplier();

    @NotNull
    LogWriterAdapter adapter();

    @NotNull
    LogLevel visibleLevel();

    @NotNull
    SpecificLogger<T> visibleLevel(@NotNull LogLevel level);

    @NotNull
    String topic();

    private boolean isLogEnabled(@NotNull LogLevel level) {
        return visibleLevel() != LogLevel.SILENT && !level.isNegligibleThan(visibleLevel());
    }

    /**
     * 前置检查是否需要记录指定级别的日志，若不需要则直接返回，避免日志对象的创建与组装。
     */
    private void logIfEnabled(@NotNull LogLevel level, @NotNull Consumer<T> consumer) {
        if (!isLogEnabled(level)) return;
        T t = specificLogSupplier().get();
        consumer.accept(t);
        t.level(level);
        this.log(t);
    }

    default void log(@NotNull T specificLog) {
        if (visibleLevel() == LogLevel.SILENT || specificLog.level().isNegligibleThan(visibleLevel())) return;
        adapter().accept(topic(), specificLog);
    }

    default void trace(@NotNull String message) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message));
    }

    default void debug(@NotNull String message) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message));
    }

    default void info(@NotNull String message) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message));
    }

    default void notice(@NotNull String message) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message));
    }

    default void warning(@NotNull String message) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message));
    }

    default void error(@NotNull String message) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message));
    }

    default void fatal(@NotNull String message) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message));
    }

    default void trace(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.TRACE, log -> log.level(LogLevel.TRACE).message(message).context(contextConsumer));
    }

    default void debug(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.DEBUG, log -> log.level(LogLevel.DEBUG).message(message).context(contextConsumer));
    }

    default void info(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.INFO, log -> log.level(LogLevel.INFO).message(message).context(contextConsumer));
    }

    default void notice(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.NOTICE, log -> log.level(LogLevel.NOTICE).message(message).context(contextConsumer));
    }

    default void warning(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.WARNING, log -> log.level(LogLevel.WARNING).message(message)
                                                      .context(contextConsumer));
    }

    default void error(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.ERROR, log -> log.level(LogLevel.ERROR).message(message).context(contextConsumer));
    }

    default void fatal(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.FATAL, log -> log.level(LogLevel.FATAL).message(message).context(contextConsumer));
    }

    default void trace(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.TRACE, log -> {
            building.accept(log);
            log.level(LogLevel.TRACE);
        });
    }

    default void debug(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.DEBUG, log -> {
            building.accept(log);
            log.level(LogLevel.DEBUG);
        });
    }

    default void info(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.INFO, log -> {
            building.accept(log);
            log.level(LogLevel.INFO);
        });
    }

    default void notice(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.NOTICE, log -> {
            building.accept(log);
            log.level(LogLevel.NOTICE);
        });
    }

    default void warning(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.WARNING, log -> {
            building.accept(log);
            log.level(LogLevel.WARNING);
        });
    }

    default void error(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            building.accept(log);
            log.level(LogLevel.ERROR);
        });
    }

    default void fatal(@NotNull Consumer<T> building) {
        this.logIfEnabled(LogLevel.FATAL, log -> {
            building.accept(log);
            log.level(LogLevel.FATAL);
        });
    }

    /**
     * @deprecated use other methods instead and register {@link Throwable} in building consumer.
     */
    @Deprecated(since = "5.0.0")
    default void exception(@NotNull Throwable throwable, @Nullable Consumer<T> building) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            log.level(LogLevel.ERROR);
            if (building != null) {
                building.accept(log);
            }
            log.exception(throwable);
        });
    }

    /**
     * @deprecated use other methods instead and register {@link Throwable} in building consumer.
     */
    @Deprecated(since = "5.0.0")
    default void exception(@NotNull Throwable throwable) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
        });
    }

    /**
     * @deprecated use other methods instead and register {@link Throwable} in building consumer.
     */
    @Deprecated(since = "5.0.0")
    default void exception(@NotNull Throwable throwable, @NotNull String message) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
            log.message(message);
        });
    }

    /**
     * @deprecated use other methods instead and register {@link Throwable} in building consumer.
     */
    @Deprecated(since = "5.0.0")
    default void exception(@NotNull Throwable throwable, @NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.logIfEnabled(LogLevel.ERROR, log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
            log.message(message);
            log.context(contextConsumer);
        });
    }

}
