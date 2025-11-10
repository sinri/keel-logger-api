package io.github.sinri.keel.logger.api.event;

public interface EventLogRecorderFactory<T> {
    EventLogRecorder<T> createEventLogRecorder();
}
