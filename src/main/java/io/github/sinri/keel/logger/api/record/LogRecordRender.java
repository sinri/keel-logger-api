package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.api.adapter.Render;

/**
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
@Deprecated
public interface LogRecordRender<R> extends Render<LogRecord, R> {
}
