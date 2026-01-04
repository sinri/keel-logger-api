package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import io.github.sinri.keel.logger.api.logger.BaseLogger;
import io.github.sinri.keel.logger.api.logger.BaseSpecificLogger;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * 日志记录器工厂的基础实现。
 * <p>
 * 各方法实现均依赖于基础实现。
 *
 * @since 5.0.0
 */
@NullMarked
public class BaseLoggerFactory implements LoggerFactory {
    private final static BaseLoggerFactory instance = new BaseLoggerFactory();
    private final LogWriterAdapter sharedLogWriterAdapter;

    protected BaseLoggerFactory(LogWriterAdapter logWriterAdapter) {
        this.sharedLogWriterAdapter = logWriterAdapter;
    }

    protected BaseLoggerFactory() {
        this(BaseLogWriter.getInstance());
    }

    public static BaseLoggerFactory getInstance() {
        return instance;
    }

    @Override
    public LogWriterAdapter sharedAdapter() {
        return sharedLogWriterAdapter;
    }

    @Override
    public Logger createLogger(String topic) {
        return new BaseLogger(topic, sharedAdapter());
    }

    @Override
    public <L extends SpecificLog<L>> SpecificLogger<L> createLogger(String topic, Supplier<L> specificLogSupplier) {
        return new BaseSpecificLogger<>(topic, specificLogSupplier, sharedAdapter());
    }
}
