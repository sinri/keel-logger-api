package io.github.sinri.keel.logger.api.factory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 共享的日志记录器工厂持有者，用于管理全局唯一的日志记录器工厂实例。
 *
 * @since 5.0.0
 */
final class SharedLoggerFactoryHolder {
    static final AtomicReference<LoggerFactory> REFERENCE = new AtomicReference<>(new BaseLoggerFactory());
}
