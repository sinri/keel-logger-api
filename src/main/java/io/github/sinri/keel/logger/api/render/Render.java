package io.github.sinri.keel.logger.api.render;

import javax.annotation.Nonnull;

public interface Render<L, R> {
    @Nonnull
    R render(@Nonnull L loggingEntity);
}
