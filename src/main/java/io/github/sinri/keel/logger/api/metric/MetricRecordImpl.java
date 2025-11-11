package io.github.sinri.keel.logger.api.metric;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

class MetricRecordImpl implements MetricRecord {
    private final @Nonnull Map<String, String> labelMap = new HashMap<>();
    private final @Nonnull String metricName;
    private final double value;
    private final long timestamp;

    public MetricRecordImpl(@Nonnull String metricName, double value, @Nullable Map<String, String> labels) {
        this.timestamp = System.currentTimeMillis();
        this.metricName = metricName;
        this.value = value;
        if (labels != null) {
            this.labelMap.putAll(labels);
        }
    }

    @Nonnull
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

    @Nonnull
    @Override
    public Map<String, String> labels() {
        return labelMap;
    }
}
