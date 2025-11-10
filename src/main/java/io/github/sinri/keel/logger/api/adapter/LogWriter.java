package io.github.sinri.keel.logger.api.adapter;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface LogWriter<R> {
    void write(@Nonnull R renderedEntity);

    default void writeBatch(@Nonnull List<R> renderedEntities) {
        renderedEntities.forEach(this::write);
    }
}
