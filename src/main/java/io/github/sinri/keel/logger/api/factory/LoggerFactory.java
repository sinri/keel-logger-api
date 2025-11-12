package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.event.Logger;

import javax.annotation.Nonnull;

public interface LoggerFactory {
    static LoggerFactory embedded() {
        return Logger::embedded;
    }

    @Nonnull
    Logger createLogger(@Nonnull String topic);
}
