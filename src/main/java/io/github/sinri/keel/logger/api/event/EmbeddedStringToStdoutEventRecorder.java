package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.StringToStdoutWriter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
class EmbeddedStringToStdoutEventRecorder implements EventRecorder<String> {
    @Nonnull
    private final String topic;
    private final Adapter<EventRecord, String> adapter;
    private LogLevel level;

    public EmbeddedStringToStdoutEventRecorder(@Nonnull String topic) {
        this.topic = topic;
        this.level = LogLevel.INFO;
        this.adapter = Adapter.build(EmbeddedEvent2StringRender.getInstance(), StringToStdoutWriter.getInstance());
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

    @Nonnull
    @Override
    public Adapter<EventRecord, String> adapter() {
        return adapter;
    }

}
