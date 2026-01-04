package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.log.Log;
import org.jspecify.annotations.NullMarked;

/**
 * 日志记录器。
 *
 * @since 5.0.0
 */
@NullMarked
public interface Logger extends SpecificLogger<Log> {
    @Override
    default Logger normalizedLogger() {
        return this;
    }
}
