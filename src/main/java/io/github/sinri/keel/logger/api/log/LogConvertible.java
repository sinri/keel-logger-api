package io.github.sinri.keel.logger.api.log;

import org.jetbrains.annotations.NotNull;

public interface LogConvertible {
    /**
     * 转换当前的特定问题日志记录实例为一个标准的日志记录类实例，确保不丢失任何日志内容。
     *
     * @return 日志记录类实例
     */
    @NotNull
    Log toLog();
}
