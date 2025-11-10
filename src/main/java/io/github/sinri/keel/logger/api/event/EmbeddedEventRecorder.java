package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.writer.LogWriter;
import io.github.sinri.keel.logger.api.writer.StdoutStringWriter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
class EmbeddedEventRecorder implements EventRecorder<String> {
    @Nonnull
    private final String topic;
    private LogLevel level;

    public EmbeddedEventRecorder(@Nonnull String topic) {
        this.topic = topic;
        level = LogLevel.INFO;
    }

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return level;
    }

    @Nonnull
    @Override
    public EventRecorder<String> visibleLevel(@Nonnull LogLevel level) {
        this.level = level;
        return this;
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Override
    public EventRender<String> render() {
        return EmbeddedEventRender.getInstance();
    }

    @Override
    public void recordEvent(@Nonnull EventRecord eventRecord) {
        var s = render().render(topic(), eventRecord);
        writer().write(s);
    }

    @Override
    public LogWriter<String> writer() {
        return StdoutStringWriter.getInstance();
    }
}
