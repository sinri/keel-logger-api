package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;

/**
 * 永远保持沉默的日志写入适配器。
 *
 * @since 5.0.0
 */
public final class SilentLogWriter implements LogWriterAdapter {
    private static final SilentLogWriter instance = new SilentLogWriter();

    private SilentLogWriter() {
    }

    @NotNull
    public static SilentLogWriter getInstance() {
        return instance;
    }

    @Override
    public void accept(@NotNull String topic, @NotNull SpecificLog<?> log) {
        // do nothing
    }
}
