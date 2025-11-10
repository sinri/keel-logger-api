package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;

public record LogRecordContent(@Nonnull String key, @Nonnull Object value) {
}
