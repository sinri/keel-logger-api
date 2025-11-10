package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;

import javax.annotation.Nonnull;

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
        return "";
    }

    @Override
    public EventRender<String> render() {
        return EmbeddedEventRender.getInstance();
    }

    @Override
    public void recordEvent(@Nonnull EventRecord eventRecord) {
        var s = render().render(topic(), eventRecord);
        System.out.println(s);
    }

}
