package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.api.writer.LogWriter;
import io.github.sinri.keel.logger.api.writer.StdoutStringWriter;

import javax.annotation.Nonnull;

/**
 * A simple log recorder that records logs to the console.
 *
 * @since 5.0.0
 */
class EmbeddedLogRecorder implements LogRecorder<String> {
    private final String topic;

    public EmbeddedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
    }

    @Override
    public LogRecordRender<String> render() {
        return EmbeddedLogRecordRender.getInstance();
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Nonnull
    @Override
    public LogWriter<String> writer() {
        return StdoutStringWriter.getInstance();
    }

    @Override
    public void recordLog(@Nonnull LogRecord record) {
        var s = render().render(topic(), record);
        writer().write(s);
    }
}
