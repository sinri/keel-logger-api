package io.github.sinri.keel.logger.api.log;

import org.jspecify.annotations.NullMarked;

/**
 * 通用日志记录。
 * <p>
 * 这是一个不附加额外字段的 {@link SpecificLog} 实现，用于满足通用日志记录场景。
 *
 * @since 5.0.0
 */
@NullMarked
public final class Log extends SpecificLog<Log> {

    /**
     * 构造一条通用日志记录。
     */
    public Log() {
        super();
    }

    /**
     * 基于给定的日志记录创建一条通用日志记录。
     * <p>
     * 新实例将复用给定实例的时间戳、日志级别、线程信息、上下文等字段。
     *
     * @param specificLog 作为来源的日志记录
     */
    public Log(SpecificLog<?> specificLog) {
        super(specificLog);
    }

}
