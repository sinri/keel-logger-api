package io.github.sinri.keel.logger.api.metric;

import org.jspecify.annotations.NullMarked;

/**
 * 定量指标记录器。
 *
 * @since 5.0.0
 */
@NullMarked
public interface MetricRecorder {
    /**
     * 记录一条定量指标记录。
     *
     * @param metricRecord 定量指标记录
     */
    void recordMetric(MetricRecord metricRecord);
}
