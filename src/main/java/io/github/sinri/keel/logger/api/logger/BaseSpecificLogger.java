package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;

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
    private final Supplier<T> issueRecordSupplier;
    @NotNull
    private final LogWriterAdapter consumer;
    @NotNull
    private LogLevel visibleLevel;

    /**
     * 特定日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param issueRecordSupplier 问题日志记录的构造器
     * @param consumer            主题化日志记录处理器
     */
    public BaseSpecificLogger(@NotNull String topic, @NotNull Supplier<T> issueRecordSupplier, @NotNull LogWriterAdapter consumer) {
        this.topic = topic;
        this.visibleLevel = LogLevel.INFO;
        this.consumer = consumer;
        this.issueRecordSupplier = issueRecordSupplier;
    }

    /**
     * 特定日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param issueRecordSupplier 问题日志记录的构造器
     */
    public BaseSpecificLogger(@NotNull String topic, @NotNull Supplier<T> issueRecordSupplier) {
        this(topic, issueRecordSupplier, BaseLogWriter.getInstance());
    }

    @NotNull
    @Override
    public Supplier<T> issueRecordSupplier() {
        return issueRecordSupplier;
    }

    @NotNull
    @Override
    public LogWriterAdapter consumer() {
        return consumer;
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
        return this;
    }

    @NotNull
    @Override
    public String topic() {
        return topic;
    }
}
