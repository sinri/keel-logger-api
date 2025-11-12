package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;

/**
 * @param key   the content key
 * @param value the content value
 * @since 5.0.0
 */
@Deprecated
public record LoggingRecordContent(@Nonnull String key, @Nonnull String value) {
}
