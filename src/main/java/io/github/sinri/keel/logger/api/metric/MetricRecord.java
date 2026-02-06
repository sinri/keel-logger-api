package io.github.sinri.keel.logger.api.metric;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * 定量指标记录。
 * <p>
 * 通常用于时序日志记录等场景。
 *
 * @since 5.0.0
 */
@NullMarked
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
    static MetricRecord create(String metricName, double value, @Nullable Map<String, String> labels) {
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
    static MetricRecord create(long timestamp, String metricName, double value, @Nullable Map<String, String> labels) {
        return new MetricRecordImpl(timestamp, metricName, value, labels);
    }

    /**
     * 获取定量指标记录的名称。
     *
     * @return 定量指标记录的名称
     */
    String metricName();

    /**
     * 获取定量指标记录的时间戳。
     *
     * @return 定量指标记录的时间戳
     */
    long timestamp();

    /**
     * 获取定量指标记录的值。
     *
     * @return 定量指标的值
     */
    double value();

    /**
     * 获取定量指标记录的标签集合。
     *
     * @return 定量指标的标签集合
     */
    Map<String, String> labels();
}
