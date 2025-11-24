package io.github.sinri.keel.logger.api.metric;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 定量指标记录。
 * <p>
 * 通常用于时序日志记录等场景。
 *
 * @since 5.0.0
 */
public interface MetricRecord {
    /**
     * 创建一条定量指标记录。
     * <p>
     * 定量指标对应的时间戳默认使用当前时间生成。
     *
     * @param metricName 定量指标记录的名称
     * @param value      定量指标的值
     * @param labels     定量指标的标签集合
     * @return 根据上面的参数创建出来的一条定量指标记录
     */
    @NotNull
    static MetricRecord create(@NotNull String metricName, double value, @Nullable Map<String, String> labels) {
        return new MetricRecordImpl(System.currentTimeMillis(), metricName, value, labels);
    }

    /**
     * 创建一条定量指标记录。
     *
     * @param timestamp  定量指标对应的时间戳
     * @param metricName 定量指标记录的名称
     * @param value      定量指标的值
     * @param labels     定量指标的标签集合
     * @return 根据上面的参数创建出来的一条定量指标记录
     */
    @NotNull
    static MetricRecord create(long timestamp, @NotNull String metricName, double value, @Nullable Map<String, String> labels) {
        return new MetricRecordImpl(timestamp, metricName, value, labels);
    }

    /**
     * 获取定量指标记录的名称。
     *
     * @return 定量指标记录的名称
     */
    @NotNull
    String metricName();

    /**
     * 获取定量指标记录的名称。
     *
     * @return 定量指标记录的时间戳
     */
    long timestamp();

    /**
     * 获取定量指标记录的名称。
     *
     * @return 定量指标的值
     */
    double value();

    /**
     * 获取定量指标记录的名称。
     *
     * @return 定量指标的标签集合
     */
    @NotNull
    Map<String, String> labels();
}
