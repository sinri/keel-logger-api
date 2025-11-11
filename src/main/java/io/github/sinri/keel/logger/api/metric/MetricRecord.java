package io.github.sinri.keel.logger.api.metric;


import javax.annotation.Nonnull;
import java.util.Map;

public interface MetricRecord {

    @Nonnull
    String metricName();

    long timestamp();

    double value();

    @Nonnull
    Map<String, String> labels();

    // MetricRecord label(String name, String value);
}
