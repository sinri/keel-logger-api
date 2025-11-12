package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;

import javax.annotation.Nonnull;

public abstract class Logger implements EventRecorder<String> {
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

    public static Logger embedded(@Nonnull String topic) {
        return new EmbeddedLogger(topic);
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
