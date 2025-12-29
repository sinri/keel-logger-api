package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 特定日志记录器的基础实现。
 *
 * @param <T> 特定日志记录的类型
 */
public class BaseSpecificLogger<T extends SpecificLog<T>> implements SpecificLogger<T> {
    @NotNull
    private final String topic;
    @NotNull
    private final Supplier<T> specificLogSupplier;
    @NotNull
    private final LogWriterAdapter logWriterAdapter;
    @NotNull
    private final AtomicReference<Logger> normalizedLoggerRef = new AtomicReference<>(null);
    @NotNull
    private LogLevel visibleLevel;

    /**
     * 特定日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param specificLogSupplier 问题日志记录的构造器
     * @param logWriterAdapter    主题化日志记录处理器
     */
    public BaseSpecificLogger(@NotNull String topic, @NotNull Supplier<T> specificLogSupplier, @NotNull LogWriterAdapter logWriterAdapter) {
        this.topic = topic;
        this.visibleLevel = LogLevel.INFO;
        this.logWriterAdapter = logWriterAdapter;
        this.specificLogSupplier = specificLogSupplier;
    }

    /**
     * 特定日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param specificLogSupplier 问题日志记录的构造器
     */
    public BaseSpecificLogger(@NotNull String topic, @NotNull Supplier<T> specificLogSupplier) {
        this(topic, specificLogSupplier, BaseLogWriter.getInstance());
    }

    @NotNull
    @Override
    public final Supplier<T> specificLogSupplier() {
        return specificLogSupplier;
    }

    @NotNull
    @Override
    public final LogWriterAdapter adapter() {
        return logWriterAdapter;
    }

    @NotNull
    @Override
    public LogLevel visibleLevel() {
        return visibleLevel;
    }

    @NotNull
    @Override
    public SpecificLogger<T> visibleLevel(@NotNull LogLevel level) {
        this.visibleLevel = level;

        Logger plain = this.normalizedLoggerRef.getPlain();
        if (plain != null) {
            plain.visibleLevel(level);
        }

        return this;
    }

    @NotNull
    @Override
    public final String topic() {
        return topic;
    }

    protected @NotNull Logger normalize() {
        Logger logger = normalizedLoggerRef.get();
        if (logger == null) {
            synchronized (normalizedLoggerRef) {
                logger = new BaseLogger(topic, adapter()).visibleLevel(visibleLevel);
                normalizedLoggerRef.set(logger);
            }
        }
        return logger;
    }

    @Override
    public @NotNull Logger normalizedLogger() {
        return normalize();
    }
}
