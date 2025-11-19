package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.InstantLogWriterAdapter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 一个保持沉默的日志记录器。
 * <p>
 * 不可以变更日志显示级别，会抛出运行时异常。
 *
 * @since 5.0.0
 */
public final class SilentLogger implements Logger {
    private final String topic;
    private final LogWriterAdapter adapter;

    private SilentLogger(String topic) {
        this.topic = topic;
        adapter = (InstantLogWriterAdapter) (anyTopic, log) -> {
            // do nothing
        };
    }

    @Override
    public @NotNull Supplier<Log> specificLogSupplier() {
        return Log::new;
    }

    @Override
    public @NotNull LogWriterAdapter adapter() {
        return adapter;
    }

    @Override
    public @NotNull LogLevel visibleLevel() {
        return LogLevel.SILENT;
    }

    @Override
    public @NotNull SpecificLogger<Log> visibleLevel(@NotNull LogLevel level) {
        throw new RuntimeException("SilentLogger can not change visible level!");
    }

    @Override
    public @NotNull String topic() {
        return topic;
    }
}
