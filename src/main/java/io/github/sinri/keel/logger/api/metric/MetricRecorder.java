package io.github.sinri.keel.logger.api.metric;

import org.jetbrains.annotations.NotNull;

/**
 * 定量指标记录器
 *
 * @since 5.0.0
 */
public interface MetricRecorder {
    /**
     * 记录一条定量指标记录。
     *
     * @param metricRecord 定量指标记录
     */
    void recordMetric(@NotNull MetricRecord metricRecord);
}
