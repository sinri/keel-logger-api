package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.StdoutStringWriter;

import javax.annotation.Nonnull;

/**
 * A simple log recorder that records logs to the console.
 *
 * @since 5.0.0
 */
class EmbeddedLogRecorder implements LogRecorder<String> {
    private final String topic;
    private final Adapter<LogRecord, String> adapter;

    public EmbeddedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
        this.adapter = Adapter.build(EmbeddedLogRecordRender.getInstance(), StdoutStringWriter.getInstance());
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Nonnull
    @Override
    public Adapter<LogRecord, String> adapter() {
        return adapter;
    }
}
