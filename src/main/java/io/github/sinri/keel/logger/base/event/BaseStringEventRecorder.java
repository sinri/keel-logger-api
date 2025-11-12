package io.github.sinri.keel.logger.base.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.event.EventRecord;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.base.adapter.render.BaseEvent2StringRender;
import io.github.sinri.keel.logger.base.adapter.writer.BaseStringToStdoutWriter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public class BaseStringEventRecorder implements EventRecorder<String> {
    @Nonnull
    private final String topic;
    private final Adapter<EventRecord, String> adapter;
    private LogLevel level;

    public BaseStringEventRecorder(@Nonnull String topic) {
        this.topic = topic;
        this.level = LogLevel.INFO;
        this.adapter = Adapter.build(BaseEvent2StringRender.getInstance(), BaseStringToStdoutWriter.getInstance());
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
