package io.github.sinri.keel.logger.api.adapter;

import java.io.Closeable;

/**
 * 持久性日志写入适配器。
 *
 * @since 5.0.0
 */
public non-sealed interface PersistentLogWriterAdapter extends LogWriterAdapter, Closeable {
}
