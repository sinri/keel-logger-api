package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;

/**
 * 永远保持沉默的日志写入适配器。
 *
 * @since 5.0.0
 */
@NullMarked
public final class SilentLogWriter implements LogWriterAdapter {
    private static final SilentLogWriter instance = new SilentLogWriter();

    private SilentLogWriter() {
    }

    public static SilentLogWriter getInstance() {
        return instance;
    }

    @Override
    public void accept(String topic, SpecificLog<?> log) {
        // do nothing
    }
}
