package io.github.sinri.keel.logger.api.adapter;

import org.jspecify.annotations.NullMarked;

import java.io.Closeable;

/**
 * 持久性日志写入适配器。
 *
 * @see Closeable
 * @since 5.0.0
 */
@NullMarked
public interface PersistentLogWriterAdapter extends Closeable, LogWriterAdapter {
}
