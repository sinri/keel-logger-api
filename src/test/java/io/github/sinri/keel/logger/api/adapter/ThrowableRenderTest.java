package io.github.sinri.keel.logger.api.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThrowableRenderTest {

    @Test
    void renderThrowableChain() {
        RuntimeException cause = new RuntimeException("cause");
        RuntimeException exception = new RuntimeException("exception", cause);

        String result = ThrowableRender.renderThrowableChain(exception);
        Assertions.assertTrue(result.contains("java.lang.RuntimeException: exception"));
        Assertions.assertTrue(result.contains("java.lang.RuntimeException: cause"));
        Assertions.assertTrue(result.contains("\t↑ "));
    }

    @Test
    void renderThrowableChainWithNull() {
        String result = ThrowableRender.renderThrowableChain(null);
        Assertions.assertEquals("", result);
    }
}
