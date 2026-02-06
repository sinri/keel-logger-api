package io.github.sinri.keel.logger.api;


import org.jspecify.annotations.NullMarked;

/**
 * 日志级别。
 * <p>
 * 非沉默级别分为七个，自 TRACE 到 FATAL，级别对应的严重性逐渐递增。
 * <p>
 * 另提供一个最高的 {@link #SILENT} 级别，用于实现不记录日志。
 *
 * @since 5.0.0
 */
@NullMarked
public enum LogLevel {
    TRACE, DEBUG, INFO, NOTICE, WARNING, ERROR, FATAL, SILENT;

    /**
     * 判断本日志级别是否不低于给定的参照日志级别。
     *
     * @param standardLevel 参照日志级别
     * @return 本日志级别不低于给定的参照日志级别时返回 {@code true}，反之返回 {@code false}。
     */
    public boolean isEnoughSeriousAs(LogLevel standardLevel) {
        return this.ordinal() >= standardLevel.ordinal();
    }

    /**
     * 判断本日志级别是否低于给定的参照日志级别。
     *
     * @param standardLevel 参照日志级别
     * @return 本日志级别低于给定的参照日志级别时返回 {@code true}，反之返回 {@code false}。
     */
    public boolean isNegligibleThan(LogLevel standardLevel) {
        return this.ordinal() < standardLevel.ordinal();
    }
}
