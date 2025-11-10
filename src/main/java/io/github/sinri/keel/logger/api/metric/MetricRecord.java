package io.github.sinri.keel.logger.api.metric;


import javax.annotation.Nonnull;
import java.util.Map;

public interface MetricRecord<T extends MetricRecord<T>> {
    @Nonnull
    String metricName();

    double value();

    @Nonnull
    Map<String, String> labels();

    T label(String name, String value);

    @SuppressWarnings("unchecked")
    default T getImplementation() {
        return (T) this;
    }
}
