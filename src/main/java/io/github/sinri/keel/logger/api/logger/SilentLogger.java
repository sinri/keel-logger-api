package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.consumer.InstantLogWriterAdapter;
import io.github.sinri.keel.logger.api.consumer.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import org.jetbrains.annotations.NotNull;

/**
 * 一个保持沉默的日志记录器。
 * <p>
 * 不可以变更日志显示级别，会抛出运行时异常。
 *
 * @since 5.0.0
 */
public final class SilentLogger implements Logger {
    private static final SilentConsumer silentConsumer = new SilentConsumer();
    private final String topic;

    public SilentLogger(@NotNull String topic) {
        this.topic = topic;
    }

    @Override
    @NotNull
    public LogLevel visibleLevel() {
        return LogLevel.SILENT;
    }

    @NotNull
    @Override
    public Logger visibleLevel(@NotNull LogLevel level) {
        throw new UnsupportedOperationException("SilentEventRecorder can not change visible level");
    }

    @Override
    public @NotNull String topic() {
        return topic;
    }

    @Override
    public @NotNull LogWriterAdapter consumer() {
        return silentConsumer;
    }

    @Override
    public void recordEvent(@NotNull Log log) {
        // do nothing to keep silent
    }

    private static class SilentConsumer implements InstantLogWriterAdapter {

        @Override
        public void accept(@NotNull String topic, @NotNull Log loggingEntity) {

        }
    }
}
