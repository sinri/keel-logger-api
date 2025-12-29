package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.log.Log;
import org.jetbrains.annotations.NotNull;

/**
 * 日志记录器。
 *
 * @since 5.0.0
 */
public interface Logger extends SpecificLogger<Log> {
    @Override
    default @NotNull Logger normalizedLogger() {
        return this;
    }
}
