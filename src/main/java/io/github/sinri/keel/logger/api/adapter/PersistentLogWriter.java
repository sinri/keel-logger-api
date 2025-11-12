package io.github.sinri.keel.logger.api.adapter;


import java.io.Closeable;

public non-sealed interface PersistentLogWriter<R> extends LogWriter<R>, Closeable {
}
