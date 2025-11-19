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

    default void log(@NotNull T specificLog) {
        if (visibleLevel() == LogLevel.SILENT || specificLog.level().isNegligibleThan(visibleLevel())) return;
        adapter().accept(topic(), specificLog);
    }

    default void log(@NotNull Consumer<T> consumer) {
        T t = specificLogSupplier().get();
        consumer.accept(t);
        this.log(t);
    }

    default void trace(@NotNull String message) {
        this.log(log -> log.level(LogLevel.TRACE).message(message));
    }

    default void debug(@NotNull String message) {
        this.log(log -> log.level(LogLevel.DEBUG).message(message));
    }

    default void info(@NotNull String message) {
        this.log(log -> log.level(LogLevel.INFO).message(message));
    }

    default void notice(@NotNull String message) {
        this.log(log -> log.level(LogLevel.NOTICE).message(message));
    }

    default void warning(@NotNull String message) {
        this.log(log -> log.level(LogLevel.WARNING).message(message));
    }

    default void error(@NotNull String message) {
        this.log(log -> log.level(LogLevel.ERROR).message(message));
    }

    default void fatal(@NotNull String message) {
        this.log(log -> log.level(LogLevel.FATAL).message(message));
    }

    default void trace(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.TRACE).message(message).context(contextConsumer));
    }

    default void debug(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.DEBUG).message(message).context(contextConsumer));
    }

    default void info(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.INFO).message(message).context(contextConsumer));
    }

    default void notice(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.NOTICE).message(message).context(contextConsumer));
    }

    default void warning(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.WARNING).message(message).context(contextConsumer));
    }

    default void error(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.ERROR).message(message).context(contextConsumer));
    }

    default void fatal(@NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> log.level(LogLevel.FATAL).message(message).context(contextConsumer));
    }

    default void trace(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.TRACE);
        });
    }

    default void debug(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.DEBUG);
        });
    }

    default void info(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.INFO);
        });
    }

    default void notice(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.NOTICE);
        });
    }

    default void warning(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.WARNING);
        });
    }

    default void error(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.ERROR);
        });
    }

    default void fatal(@NotNull Consumer<T> building) {
        this.log(log -> {
            building.accept(log);
            log.level(LogLevel.FATAL);
        });
    }

    default void exception(@NotNull Throwable throwable, @Nullable Consumer<T> building) {
        this.log(log -> {
            log.level(LogLevel.ERROR);
            if (building != null) {
                building.accept(log);
            }
            log.exception(throwable);
        });
    }

    default void exception(@NotNull Throwable throwable) {
        this.log(log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
        });
    }

    default void exception(@NotNull Throwable throwable, @NotNull String message) {
        this.log(log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
            log.message(message);
        });
    }

    default void exception(@NotNull Throwable throwable, @NotNull String message, @NotNull Consumer<LogContext> contextConsumer) {
        this.log(log -> {
            log.level(LogLevel.ERROR);
            log.exception(throwable);
            log.message(message);
            log.context(contextConsumer);
        });
    }

}
