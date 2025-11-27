package io.github.sinri.keel.logger.api.metric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class MetricRecordImplTest {

    @Test
    void testConstructorWithNullLabels() {
        // Test through MetricRecord.create which uses MetricRecordImpl
        MetricRecord record = MetricRecord.create(1000L, "test.metric", 50.0, null);

        Assertions.assertNotNull(record);
        Assertions.assertEquals(1000L, record.timestamp());
        Assertions.assertEquals("test.metric", record.metricName());
        Assertions.assertEquals(50.0, record.value(), 0.001);
        Assertions.assertNotNull(record.labels());
        Assertions.assertTrue(record.labels().isEmpty(), "Labels should be empty when null is passed");
    }

    @Test
    void testConstructorWithLabels() {
        Map<String, String> labels = new HashMap<>();
        labels.put("key1", "value1");
        labels.put("key2", "value2");

        MetricRecord record = MetricRecord.create(2000L, "test.metric", 75.5, labels);

        Assertions.assertNotNull(record);
        Assertions.assertEquals(2000L, record.timestamp());
        Assertions.assertEquals("test.metric", record.metricName());
        Assertions.assertEquals(75.5, record.value(), 0.001);
        Assertions.assertNotNull(record.labels());
        Assertions.assertEquals(2, record.labels().size());
        Assertions.assertEquals("value1", record.labels().get("key1"));
        Assertions.assertEquals("value2", record.labels().get("key2"));
    }

    @Test
    void testGetters() {
        long timestamp = 3000L;
        String metricName = "getter.test";
        double value = 123.456;
        Map<String, String> labels = new HashMap<>();
        labels.put("test", "value");

        MetricRecord record = MetricRecord.create(timestamp, metricName, value, labels);

        Assertions.assertEquals(timestamp, record.timestamp());
        Assertions.assertEquals(metricName, record.metricName());
        Assertions.assertEquals(value, record.value(), 0.001);
        Assertions.assertEquals("value", record.labels().get("test"));
    }

    @Test
    void testLabelsAreCopiedNotReferenced() {
        Map<String, String> originalLabels = new HashMap<>();
        originalLabels.put("original", "value");

        MetricRecord record = MetricRecord.create(4000L, "test.metric", 1.0, originalLabels);

        // Modify original map
        originalLabels.put("new", "newValue");

        // Record labels should not be affected
        Assertions.assertFalse(record.labels().containsKey("new"));
        Assertions.assertEquals(1, record.labels().size());
        Assertions.assertEquals("value", record.labels().get("original"));
    }

    @Test
    void testLabelsAreMutable() {
        Map<String, String> labels = new HashMap<>();
        labels.put("initial", "value");

        MetricRecord record = MetricRecord.create(5000L, "test.metric", 1.0, labels);

        // Labels returned should be mutable
        Map<String, String> recordLabels = record.labels();
        recordLabels.put("added", "newValue");

        Assertions.assertEquals(2, recordLabels.size());
        Assertions.assertEquals("newValue", recordLabels.get("added"));
    }

    @Test
    void testZeroTimestamp() {
        MetricRecord record = MetricRecord.create(0L, "test.metric", 1.0, null);

        Assertions.assertEquals(0L, record.timestamp());
    }

    @Test
    void testNegativeTimestamp() {
        MetricRecord record = MetricRecord.create(-1000L, "test.metric", 1.0, null);

        Assertions.assertEquals(-1000L, record.timestamp());
    }

    @Test
    void testEmptyMetricName() {
        MetricRecord record = MetricRecord.create(6000L, "", 1.0, null);

        Assertions.assertEquals("", record.metricName());
    }

    @Test
    void testEmptyLabelsMap() {
        Map<String, String> emptyLabels = new HashMap<>();

        MetricRecord record = MetricRecord.create(7000L, "test.metric", 1.0, emptyLabels);

        Assertions.assertNotNull(record.labels());
        Assertions.assertTrue(record.labels().isEmpty());
    }

    @Test
    void testLabelsWithNullValues() {
        Map<String, String> labels = new HashMap<>();
        labels.put("key1", "value1");
        labels.put("key2", null);

        MetricRecord record = MetricRecord.create(8000L, "test.metric", 1.0, labels);

        Assertions.assertEquals("value1", record.labels().get("key1"));
        Assertions.assertNull(record.labels().get("key2"));
    }
}

