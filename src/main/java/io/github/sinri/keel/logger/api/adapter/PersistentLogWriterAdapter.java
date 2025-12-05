package io.github.sinri.keel.logger.api.adapter;

import java.io.Closeable;

/**
 * 持久性日志写入适配器。
 *
 * @see Closeable
 * @since 5.0.0
 */
public interface PersistentLogWriterAdapter extends Closeable, LogWriterAdapter {
}
