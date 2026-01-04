package io.github.sinri.keel.logger.api.log;

import org.jspecify.annotations.NullMarked;

/**
 * 通用的日志记录
 *
 * @since 5.0.0
 */
@NullMarked
public final class Log extends SpecificLog<Log> {

    public Log() {
        super();
    }

    public Log(SpecificLog<?> specificLog) {
        super(specificLog);
    }

}
