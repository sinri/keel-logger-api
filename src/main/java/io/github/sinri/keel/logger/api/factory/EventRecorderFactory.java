package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.EventRecorder;

import javax.annotation.Nonnull;

/**
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface EventRecorderFactory<R> {
    EventRecorder<R> createEventRecorder(@Nonnull String topic);
}
