package io.github.sinri.keel.logger.api;


import org.jspecify.annotations.NullMarked;

/**
 * 日志严重性等级。
 * <p>
 * 非沉默级别分为七个，自 TRACE 到 FATAL，严重性（seriousness）逐渐递增；
 * 此外设置一个日志记录不可达的最顶级 SILENT 级别，用以实现不记录日志。
 *
 * @since 5.0.0
 */
@NullMarked
public enum LogLevel {
    TRACE, DEBUG, INFO, NOTICE, WARNING, ERROR, FATAL, SILENT;

    /**
     * 判断本日志严重性等级是否不低于给定的参照日志严重性等级
     *
     * @param standardLevel 参照日志严重性等级
     * @return 本日志严重性等级不低于给定的参照日志严重性等级时返回{@code true}，反之返回{@code false}。
     */
    public boolean isEnoughSeriousAs(LogLevel standardLevel) {
        return this.ordinal() >= standardLevel.ordinal();
    }

    /**
     * 判断本日志严重性等级是否低于给定的参照日志严重性等级
     *
     * @param standardLevel 参照日志严重性等级
     * @return 本日志严重性等级低于给定的参照日志严重性等级时返回{@code true}，反之返回{@code false}。
     */
    public boolean isNegligibleThan(LogLevel standardLevel) {
        return this.ordinal() < standardLevel.ordinal();
    }
}
