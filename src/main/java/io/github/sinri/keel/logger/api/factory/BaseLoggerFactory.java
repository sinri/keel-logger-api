package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.consumer.BaseLogWriter;
import io.github.sinri.keel.logger.api.consumer.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import io.github.sinri.keel.logger.api.logger.BaseLogger;
import io.github.sinri.keel.logger.api.logger.BaseSpecificLogger;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 日志记录器工厂的基础实现。
 * <p>
 * 各方法实现均依赖于基础实现。
 *
 * @since 5.0.0
 */
public class BaseLoggerFactory implements LoggerFactory {
    private final static BaseLoggerFactory instance = new BaseLoggerFactory();
    private final LogWriterAdapter sharedLogWriterAdapter;

    protected BaseLoggerFactory() {
        sharedLogWriterAdapter = BaseLogWriter.getInstance();
    }

    public static BaseLoggerFactory getInstance() {
        return instance;
    }

    @Override
    public LogWriterAdapter sharedAdapter() {
        return sharedLogWriterAdapter;
    }

    @Override
    public Logger createLogger(@NotNull String topic) {
        return new BaseLogger(topic, sharedAdapter());
    }

    @Override
    public <L extends SpecificLog<L>> SpecificLogger<L> createLogger(@NotNull String topic, @NotNull Supplier<L> specificLogSupplier) {
        return new BaseSpecificLogger<>(topic, specificLogSupplier, sharedAdapter());
    }
}
