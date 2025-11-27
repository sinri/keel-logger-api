package io.github.sinri.keel.logger.api.metric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class MetricRecordTest {

    @Test
    void testCreateWithCurrentTimestamp() {
        long beforeCreation = System.currentTimeMillis();

        MetricRecord record = MetricRecord.create("test.metric", 42.5, null);

        long afterCreation = System.currentTimeMillis();

        Assertions.assertNotNull(record);
        Assertions.assertEquals("test.metric", record.metricName());
        Assertions.assertEquals(42.5, record.value(), 0.001);
        Assertions.assertNotNull(record.labels());
        Assertions.assertTrue(record.labels().isEmpty());
        Assertions.assertTrue(record.timestamp() >= beforeCreation);
        Assertions.assertTrue(record.timestamp() <= afterCreation);
    }

    @Test
    void testCreateWithCurrentTimestampAndLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("env", "test");
        labels.put("service", "api");

        MetricRecord record = MetricRecord.create("test.metric", 100.0, labels);

        Assertions.assertNotNull(record);
        Assertions.assertEquals("test.metric", record.metricName());
        Assertions.assertEquals(100.0, record.value(), 0.001);
        Assertions.assertNotNull(record.labels());
        Assertions.assertEquals(2, record.labels().size());
        Assertions.assertEquals("test", record.labels().get("env"));
        Assertions.assertEquals("api", record.labels().get("service"));
    }

    @Test
    void testCreateWithSpecifiedTimestamp() {
        long timestamp = 1234567890L;
        Map<String, String> labels = new HashMap<>();
        labels.put("key", "value");

        MetricRecord record = MetricRecord.create(timestamp, "custom.metric", 99.9, labels);

        Assertions.assertNotNull(record);
        Assertions.assertEquals(timestamp, record.timestamp());
        Assertions.assertEquals("custom.metric", record.metricName());
        Assertions.assertEquals(99.9, record.value(), 0.001);
        Assertions.assertEquals("value", record.labels().get("key"));
    }

    @Test
    void testCreateWithNullLabels() {
        MetricRecord record = MetricRecord.create("metric", 1.0, null);

        Assertions.assertNotNull(record);
        Assertions.assertNotNull(record.labels());
        Assertions.assertTrue(record.labels().isEmpty());
    }

    @Test
    void testCreateWithEmptyLabels() {
        Map<String, String> emptyLabels = new HashMap<>();

        MetricRecord record = MetricRecord.create("metric", 1.0, emptyLabels);

        Assertions.assertNotNull(record);
        Assertions.assertNotNull(record.labels());
        Assertions.assertTrue(record.labels().isEmpty());
    }

    @Test
    void testCreateWithZeroValue() {
        MetricRecord record = MetricRecord.create("zero.metric", 0.0, null);

        Assertions.assertEquals(0.0, record.value(), 0.0);
    }

    @Test
    void testCreateWithNegativeValue() {
        MetricRecord record = MetricRecord.create("negative.metric", -10.5, null);

        Assertions.assertEquals(-10.5, record.value(), 0.001);
    }

    @Test
    void testCreateWithLargeValue() {
        MetricRecord record = MetricRecord.create("large.metric", Double.MAX_VALUE, null);

        Assertions.assertEquals(Double.MAX_VALUE, record.value());
    }

    @Test
    void testLabelsAreImmutable() {
        Map<String, String> labels = new HashMap<>();
        labels.put("key", "value");

        MetricRecord record = MetricRecord.create("metric", 1.0, labels);

        // Verify labels are copied, not referenced
        labels.put("newKey", "newValue");
        Assertions.assertFalse(record.labels().containsKey("newKey"));
    }

    @Test
    void testMultipleRecordsWithSameLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("env", "prod");

        MetricRecord record1 = MetricRecord.create("metric1", 1.0, labels);
        MetricRecord record2 = MetricRecord.create("metric2", 2.0, labels);

        Assertions.assertEquals("prod", record1.labels().get("env"));
        Assertions.assertEquals("prod", record2.labels().get("env"));
        Assertions.assertNotSame(record1.labels(), record2.labels(), "Labels should be separate instances");
    }
}

