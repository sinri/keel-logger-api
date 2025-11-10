package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.StdoutStringWriter;

import javax.annotation.Nonnull;

class EmbeddedLogger extends Logger {
    public EmbeddedLogger(@Nonnull String topic, @Nonnull LogLevel visibleLevel) {
        super(topic, visibleLevel, Adapter.build(EmbeddedEventRender.getInstance(), StdoutStringWriter.getInstance()));
    }

    public EmbeddedLogger(@Nonnull String topic) {
        this(topic, LogLevel.INFO);
    }
}
