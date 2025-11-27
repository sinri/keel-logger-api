package io.github.sinri.keel.logger.api.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LogContextTest {

    @Test
    void testLogContext() {
        LogContext context = new LogContext();
        context.put("key", "value");
        Map<String, Object> map = context.toMap();
        Assertions.assertEquals("value", map.get("key"));
    }

    @Test
    void testLogContextConsumer() {
        LogContext context = new LogContext(c -> c.put("key", "value"));
        Map<String, Object> map = context.toMap();
        Assertions.assertEquals("value", map.get("key"));
    }

    @Test
    void testComplexDataStructures() {
        LogContext context = new LogContext();

        // Test with List
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        context.put("list", list);

        // Test with nested Map
        Map<String, Object> nestedMap = new HashMap<>();
        nestedMap.put("nestedKey", "nestedValue");
        context.put("nestedMap", nestedMap);

        // Test with array
        int[] array = {1, 2, 3};
        context.put("array", array);

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals(list, map.get("list"));
        Assertions.assertEquals(nestedMap, map.get("nestedMap"));
        Assertions.assertArrayEquals(array, (int[]) map.get("array"));
    }

    @Test
    void testNullValue() {
        LogContext context = new LogContext();
        context.put("nullKey", null);

        Map<String, Object> map = context.toMap();
        Assertions.assertTrue(map.containsKey("nullKey"));
        Assertions.assertNull(map.get("nullKey"));
    }

    @Test
    void testToMapReturnsSameInstance() {
        LogContext context = new LogContext();
        context.put("key", "value");

        Map<String, Object> map1 = context.toMap();
        Map<String, Object> map2 = context.toMap();

        // toMap() returns the same instance, not a copy
        Assertions.assertSame(map1, map2);

        // Should have same content
        Assertions.assertEquals(map1, map2);
    }

    @Test
    void testToMapIsMutable() {
        LogContext context = new LogContext();
        context.put("key", "value");

        Map<String, Object> map = context.toMap();

        // toMap() returns a mutable map that directly references the internal map
        // Modifications to the returned map will affect the original LogContext
        map.put("newKey", "newValue");

        // The modification should be reflected in the original context
        Map<String, Object> map2 = context.toMap();
        Assertions.assertTrue(map2.containsKey("newKey"));
        Assertions.assertEquals("newValue", map2.get("newKey"));
        Assertions.assertEquals(2, map2.size());
    }

    @Test
    void testMultiplePutSameKey() {
        LogContext context = new LogContext();
        context.put("key", "value1");
        context.put("key", "value2");

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals("value2", map.get("key"), "Should overwrite previous value");
        Assertions.assertEquals(1, map.size());
    }

    @Test
    void testEmptyContext() {
        LogContext context = new LogContext();

        Map<String, Object> map = context.toMap();
        Assertions.assertNotNull(map);
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    void testMultipleKeys() {
        LogContext context = new LogContext();
        context.put("key1", "value1");
        context.put("key2", "value2");
        context.put("key3", "value3");

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("value1", map.get("key1"));
        Assertions.assertEquals("value2", map.get("key2"));
        Assertions.assertEquals("value3", map.get("key3"));
    }

    @Test
    void testConsumerWithMultipleOperations() {
        LogContext context = new LogContext(c -> {
            c.put("key1", "value1");
            c.put("key2", "value2");
            c.put("key3", 123);
        });

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals(3, map.size());
        Assertions.assertEquals("value1", map.get("key1"));
        Assertions.assertEquals("value2", map.get("key2"));
        Assertions.assertEquals(123, map.get("key3"));
    }

    @Test
    void testDifferentValueTypes() {
        LogContext context = new LogContext();
        context.put("string", "text");
        context.put("integer", 42);
        context.put("double", 3.14);
        context.put("boolean", true);
        context.put("null", null);

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals("text", map.get("string"));
        Assertions.assertEquals(42, map.get("integer"));
        Assertions.assertEquals(3.14, map.get("double"));
        Assertions.assertEquals(true, map.get("boolean"));
        Assertions.assertNull(map.get("null"));
    }

    @Test
    void testNestedList() {
        LogContext context = new LogContext();
        List<List<String>> nestedList = new ArrayList<>();
        List<String> innerList1 = new ArrayList<>();
        innerList1.add("a");
        innerList1.add("b");
        List<String> innerList2 = new ArrayList<>();
        innerList2.add("c");
        nestedList.add(innerList1);
        nestedList.add(innerList2);

        context.put("nestedList", nestedList);

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals(nestedList, map.get("nestedList"));
    }

    @Test
    void testLargeContext() {
        LogContext context = new LogContext();
        for (int i = 0; i < 100; i++) {
            context.put("key" + i, "value" + i);
        }

        Map<String, Object> map = context.toMap();
        Assertions.assertEquals(100, map.size());
        Assertions.assertEquals("value50", map.get("key50"));
    }
}
