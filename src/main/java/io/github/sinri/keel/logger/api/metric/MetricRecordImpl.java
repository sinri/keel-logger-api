package io.github.sinri.keel.logger.api.metric;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 定量指标记录的默认实现类。
 *
 * @since 5.0.0
 */
class MetricRecordImpl implements MetricRecord {
    private final @NotNull Map<String, String> labelMap = new HashMap<>();
    private final @NotNull String metricName;
    private final double value;
    private final long timestamp;

    /**
     * 构建一条当前时间戳下的定量指标记录。
     *
     * @param metricName 定量指标记录的名称
     * @param value      定量指标的值
     * @param labels     定量指标的标签集合
     */
    public MetricRecordImpl(long timestamp, @NotNull String metricName, double value, @Nullable Map<String, String> labels) {
        this.timestamp = timestamp;
        this.metricName = metricName;
        this.value = value;
        if (labels != null) {
            this.labelMap.putAll(labels);
        }
    }

    public MetricRecordImpl(@NotNull String metricName, double value, @Nullable Map<String, String> labels) {
        this(System.currentTimeMillis(), metricName, value, labels);
    }

    public MetricRecordImpl(@NotNull String metricName, double value) {
        this(System.currentTimeMillis(), metricName, value, null);
    }

    @NotNull
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

    @NotNull
    @Override
    public Map<String, String> labels() {
        return labelMap;
    }
}
