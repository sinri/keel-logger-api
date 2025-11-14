package io.github.sinri.keel.logger.api;


import org.jetbrains.annotations.NotNull;

/**
 * 日志严重性等级。
 * <p>
 * 自 TRACE 到 FATAL，分为七个级别，严重性（seriousness）逐渐递增。
 *
 * @since 5.0.0
 */
public enum LogLevel {
    TRACE, DEBUG, INFO, NOTICE, WARNING, ERROR, FATAL;

    /**
     * 判断本日志严重性等级是否不低于给定的参照日志严重性等级
     *
     * @param standardLevel 参照日志严重性等级
     * @return 本日志严重性等级不低于给定的参照日志严重性等级时返回{@code true}，反之返回{@code false}。
     */
    public boolean isEnoughSeriousAs(@NotNull LogLevel standardLevel) {
        return this.ordinal() >= standardLevel.ordinal();
    }

    /**
     * 判断本日志严重性等级是否低于给定的参照日志严重性等级
     *
     * @param standardLevel 参照日志严重性等级
     * @return 本日志严重性等级低于给定的参照日志严重性等级时返回{@code true}，反之返回{@code false}。
     */
    public boolean isNegligibleThan(@NotNull LogLevel standardLevel) {
        return this.ordinal() < standardLevel.ordinal();
    }
}
