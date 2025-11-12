package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.EventRecorder;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public interface EventRecorderFactory {
    EventRecorder createEventRecorder(@Nonnull String topic);
}
