package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public class Context {
    private final Map<String, Object> contentMap;

    public Context() {
        this.contentMap = new java.util.TreeMap<>();
    }

    public Context(@Nonnull Consumer<Context> contextConsumer) {
        this();
        contextConsumer.accept(this);
    }

    public Context put(String key, Object value) {
        this.contentMap.put(key, value);
        return this;
    }

    @Nonnull
    public Map<String, Object> toMap() {
        return contentMap;
    }
}
