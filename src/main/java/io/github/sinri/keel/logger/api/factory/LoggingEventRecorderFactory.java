package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.LoggingEventRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;

public interface LoggingEventRecorderFactory extends EventRecorderFactory<LoggingRecord> {
    LoggingEventRecorder createEventRecorder(@Nonnull String topic);
}
