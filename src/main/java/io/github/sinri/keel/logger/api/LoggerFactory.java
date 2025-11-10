package io.github.sinri.keel.logger.api;

import javax.annotation.Nonnull;

public interface LoggerFactory {
    @Nonnull
    Logger createLogger(@Nonnull String topic);
}
