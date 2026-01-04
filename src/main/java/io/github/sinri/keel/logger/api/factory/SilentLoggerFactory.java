package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.adapter.SilentLogWriter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import io.github.sinri.keel.logger.api.logger.BaseLogger;
import io.github.sinri.keel.logger.api.logger.BaseSpecificLogger;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * 日志记录器工厂的沉默实现。
 *
 * @since 5.0.0
 */
@NullMarked
public final class SilentLoggerFactory implements LoggerFactory {
    private static final SilentLoggerFactory instance = new SilentLoggerFactory();

    private SilentLoggerFactory() {
    }

    public static SilentLoggerFactory getInstance() {
        return instance;
    }

    @Override
    public LogWriterAdapter sharedAdapter() {
        return SilentLogWriter.getInstance();
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
