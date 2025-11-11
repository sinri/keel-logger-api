package io.github.sinri.keel.logger.api.adapter;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.util.List;

/**
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface LogWriter<R> extends Closeable {
    void write(@Nonnull String topic, @Nonnull R renderedEntity);

    default void writeBatch(@Nonnull String topic, @Nonnull List<R> renderedEntities) {
        renderedEntities.forEach(x -> this.write(topic, x));
    }
}
