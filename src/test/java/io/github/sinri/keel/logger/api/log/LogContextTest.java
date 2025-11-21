package io.github.sinri.keel.logger.api.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
