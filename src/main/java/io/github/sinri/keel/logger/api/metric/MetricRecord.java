package io.github.sinri.keel.logger.api.metric;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public interface MetricRecord {
    static MetricRecord create(@Nonnull String metricName, double value, @Nullable Map<String, String> labels) {
        return new MetricRecordImpl(metricName, value, labels);
    }

    @Nonnull
    String metricName();

    long timestamp();

    double value();

    @Nonnull
    Map<String, String> labels();

    // MetricRecord label(String name, String value);
}
