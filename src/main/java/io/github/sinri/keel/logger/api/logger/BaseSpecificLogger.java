package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LateObject;
import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * 特定日志记录器的基础实现。
 *
 * @param <T> 特定日志记录的类型
 */
@NullMarked
public class BaseSpecificLogger<T extends SpecificLog<T>> implements SpecificLogger<T> {
    private final String topic;
    private final Supplier<T> specificLogSupplier;
    private final LogWriterAdapter logWriterAdapter;
    private final LateObject<Logger> lateNormalizedLogger = new LateObject<>();
    private LogLevel visibleLevel;

    /**
     * 特定日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param specificLogSupplier 问题日志记录的构造器
     * @param logWriterAdapter    主题化日志记录处理器
     */
    public BaseSpecificLogger(String topic, Supplier<T> specificLogSupplier, LogWriterAdapter logWriterAdapter) {
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
    public BaseSpecificLogger(String topic, Supplier<T> specificLogSupplier) {
        this(topic, specificLogSupplier, BaseLogWriter.getInstance());
    }

    @Override
    public final Supplier<T> specificLogSupplier() {
        return specificLogSupplier;
    }

    @Override
    public final LogWriterAdapter adapter() {
        return logWriterAdapter;
    }

    @Override
    public LogLevel visibleLevel() {
        return visibleLevel;
    }

    @Override
    public SpecificLogger<T> visibleLevel(LogLevel level) {
        this.visibleLevel = level;

        if (lateNormalizedLogger.isInitialized()) {
            lateNormalizedLogger.get().visibleLevel(level);
        }

        return this;
    }

    @Override
    public final String topic() {
        return topic;
    }

    protected Logger normalize() {
        return lateNormalizedLogger.ensure(
                () -> new BaseLogger(topic, adapter())
                        .visibleLevel(visibleLevel));
    }

    @Override
    public Logger normalizedLogger() {
        return normalize();
    }
}
