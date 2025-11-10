package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;

class EmbeddedEventLogRecorder implements EventLogRecorder<String> {
    private final static EmbeddedEventLogRecorder instance = new EmbeddedEventLogRecorder();

    private EmbeddedEventLogRecorder() {
    }

    public static EmbeddedEventLogRecorder getInstance() {
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
