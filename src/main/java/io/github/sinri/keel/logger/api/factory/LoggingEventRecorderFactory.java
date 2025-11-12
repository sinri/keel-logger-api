package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.LoggingEventRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;

@Deprecated
public interface LoggingEventRecorderFactory extends EventRecorderFactory<LoggingRecord> {
    LoggingEventRecorder createEventRecorder(@Nonnull String topic);
}
