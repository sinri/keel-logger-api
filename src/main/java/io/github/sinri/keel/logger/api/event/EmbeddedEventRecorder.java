package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;

class EmbeddedEventRecorder implements EventRecorder<String> {
    private final static EmbeddedEventRecorder instance = new EmbeddedEventRecorder();

    private EmbeddedEventRecorder() {
    }

    public static EmbeddedEventRecorder getInstance() {
        return instance;
    }


    @Override
    public EventRender<String> getEventRender() {
        return EmbeddedEventRender.getInstance();
    }

    @Override
    public void recordEvent(@Nonnull EventRecord eventRecord) {
        var s = getEventRender().render(eventRecord);
        System.out.println(s);
    }

}
