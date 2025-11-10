package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;

import javax.annotation.Nonnull;

record EmbeddedEventRecorder(@Nonnull String topic) implements EventRecorder<String> {

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return LogLevel.INFO;
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
