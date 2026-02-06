package io.github.sinri.keel.logger.api.metric;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 定量指标记录的默认实现类。
 *
 * @since 5.0.0
 */
@NullMarked
class MetricRecordImpl implements MetricRecord {
    private final Map<String, String> labelMap = new HashMap<>();
    private final String metricName;
    private final double value;
    private final long timestamp;

    /**
     * 构建一条定量指标记录。
     *
     * @param timestamp  定量指标对应的时间戳
     * @param metricName 定量指标记录的名称
     * @param value      定量指标的值
     * @param labels     定量指标的标签集合
     */
    public MetricRecordImpl(long timestamp, String metricName, double value, @Nullable Map<String, String> labels) {
        this.timestamp = timestamp;
        this.metricName = metricName;
        this.value = value;
        if (labels != null) {
            this.labelMap.putAll(labels);
        }
    }

    @Override
    public String metricName() {
        return metricName;
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public Map<String, String> labels() {
        return labelMap;
    }
}
