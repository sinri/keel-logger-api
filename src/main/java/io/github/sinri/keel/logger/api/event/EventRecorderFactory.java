package io.github.sinri.keel.logger.api.event;

public interface EventRecorderFactory<T> {
    EventRecorder<T> createEventLogRecorder();
}
