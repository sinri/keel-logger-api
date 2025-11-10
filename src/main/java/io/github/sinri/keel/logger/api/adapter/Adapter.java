package io.github.sinri.keel.logger.api.adapter;

import javax.annotation.Nonnull;

/**
 * @param <T> the type of log entity
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface Adapter<T, R> {
    static <T, R> Adapter<T, R> build(@Nonnull Render<T, R> render, @Nonnull LogWriter<R> writer) {
        return new Adapter<T, R>() {
            @Nonnull
            @Override
            public Render<T, R> render() {
                return render;
            }

            @Nonnull
            @Override
            public LogWriter<R> writer() {
                return writer;
            }
        };
    }

    static <T> Adapter<T, String> buildWithStdoutStringWriter(@Nonnull Render<T, String> render) {
        return build(render, StdoutStringWriter.getInstance());
    }

    @Nonnull
    Render<T, R> render();

    @Nonnull
    LogWriter<R> writer();

    default void renderAndWrite(@Nonnull String topic, @Nonnull T record) {
        writer().write(render().render(topic, record));
    }
}
