package io.github.sinri.keel.logger.api.render;

import javax.annotation.Nonnull;

/**
 * @param <L> the type of raw entity
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface Render<L, R> {
    @Nonnull
    R render(@Nonnull String topic, @Nonnull L loggingEntity);
}
