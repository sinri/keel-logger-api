package io.github.sinri.keel.logger.api;


import org.jspecify.annotations.NullMarked;

/**
 * 日志级别。
 * <p>
 * 非沉默级别分为七个，自 TRACE 到 FATAL，级别对应的严重性逐渐递增。
 * <p>
 * 另提供一个最高的 {@link #SILENT} 级别，用于实现不记录日志。
 * <p>
 * 各级别带有显式 {@link #severity()} 数值：`TRACE`～`FATAL` 按 {@code 100} 递增，相邻档之间留有空间；
 * {@link #SILENT} 使用 {@link Integer#MAX_VALUE}，保证高于一切常规级别。
 * <p>
 * {@link #isEnoughSeriousAs(LogLevel)} / {@link #isNegligibleThan(LogLevel)} 均基于该数值而非
 * {@link Enum#ordinal()}，以便在枚举声明顺序调整或未来插入新级别时保持稳定的严重性关系。
 *
 * @since 5.0.0
 */
@NullMarked
public enum LogLevel {
    TRACE(100),
    DEBUG(200),
    INFO(300),
    NOTICE(400),
    WARNING(500),
    ERROR(600),
    FATAL(700),
    SILENT(Integer.MAX_VALUE);

    private final int severity;

    LogLevel(int severity) {
        this.severity = severity;
    }

    /**
     * 返回本级别的严重度数值；数值越大表示越严重。
     * <p>
     * 常规级别为 {@code 100}、{@code 200}、…、{@code 700}，档间可插入中间值（如 {@code 250}）以扩展枚举；
     * {@link #SILENT} 恒为 {@link Integer#MAX_VALUE}。
     *
     * @return 严重度数值
     */
    public int severity() {
        return severity;
    }

    /**
     * 判断本日志级别是否不低于给定的参照日志级别。
     *
     * @param standardLevel 参照日志级别
     * @return 本日志级别不低于给定的参照日志级别时返回 {@code true}，反之返回 {@code false}。
     */
    public boolean isEnoughSeriousAs(LogLevel standardLevel) {
        return this.severity >= standardLevel.severity;
    }

    /**
     * 判断本日志级别是否低于给定的参照日志级别。
     *
     * @param standardLevel 参照日志级别
     * @return 本日志级别低于给定的参照日志级别时返回 {@code true}，反之返回 {@code false}。
     */
    public boolean isNegligibleThan(LogLevel standardLevel) {
        return this.severity < standardLevel.severity;
    }
}
