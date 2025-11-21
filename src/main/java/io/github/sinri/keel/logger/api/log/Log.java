package io.github.sinri.keel.logger.api.log;

import org.jetbrains.annotations.NotNull;

/**
 * 通用的日志记录
 *
 * @since 5.0.0
 */
public final class Log extends SpecificLog<Log> {

    public Log() {
        super();
    }

    public Log(@NotNull SpecificLog<?> specificLog) {
        super(specificLog);
    }

}
