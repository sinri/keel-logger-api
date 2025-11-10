package io.github.sinri.keel.logger.api;

import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.event.EventRecord;
import io.github.sinri.keel.logger.api.event.EventRecorder;

import javax.annotation.Nonnull;

public class Logger implements EventRecorder<String> {
    @Nonnull
    private final Adapter<EventRecord, String> adapter;
    @Nonnull
    private final String topic;
    @Nonnull
    private LogLevel visibleLevel;

    public Logger(@Nonnull String topic, @Nonnull LogLevel visibleLevel, @Nonnull Adapter<EventRecord, String> adapter) {
        this.topic = topic;
        this.visibleLevel = visibleLevel;
        this.adapter = adapter;
    }

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return visibleLevel;
    }

    @Nonnull
    @Override
    public Logger visibleLevel(@Nonnull LogLevel level) {
        this.visibleLevel = level;
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
