package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.adapter.SilentLogWriter;
import io.github.sinri.keel.logger.api.log.Log;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

/**
 * 一个保持沉默的日志记录器。
 * <p>
 * 不可以变更日志显示级别，会抛出运行时异常。
 *
 * @since 5.0.0
 */
@NullMarked
public final class SilentLogger implements Logger {
    private final String topic;
    private final LogWriterAdapter adapter;

    private SilentLogger(String topic) {
        this.topic = topic;
        adapter = SilentLogWriter.getInstance();
    }

    @Override
    public Supplier<Log> specificLogSupplier() {
        return Log::new;
    }

    @Override
    public LogWriterAdapter adapter() {
        return adapter;
    }

    @Override
    public LogLevel visibleLevel() {
        return LogLevel.SILENT;
    }

    @Override
    public SpecificLogger<Log> visibleLevel(LogLevel level) {
        throw new RuntimeException("SilentLogger can not change visible level!");
    }

    @Override
    public String topic() {
        return topic;
    }
}
