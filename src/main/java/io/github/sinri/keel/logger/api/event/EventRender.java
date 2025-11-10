package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;

public interface EventRender<T> {

    //    @Nonnull
    //    LogRecord asLogRecord(@Nonnull EventRecord eventRecord);
    //
    //    @Nonnull
    //    String asString(@Nonnull EventRecord eventRecord);
    //
    //    @Nonnull
    //    String asString(@Nonnull LogRecord logRecord);

    @Nonnull
    T render(@Nonnull EventRecord eventRecord);
}
