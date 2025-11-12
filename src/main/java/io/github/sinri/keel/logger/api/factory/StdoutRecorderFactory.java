package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.event.Logger;

import javax.annotation.Nonnull;

public interface StdoutRecorderFactory extends RecorderFactory<String>, LoggerFactory {
    @Nonnull
    @Override
    default Logger createLogger(@Nonnull String topic) {
        return Logger.embedded(topic);
    }

    @Override
    default EventRecorder<String> createEventLogRecorder(@Nonnull String topic) {
        return createLogger(topic);
    }


}
