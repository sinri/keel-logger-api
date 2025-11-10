package io.github.sinri.keel.logger.api.writer;

import javax.annotation.Nonnull;

public interface LogWriter<R> {
    void write(@Nonnull R renderedEntity);
}
