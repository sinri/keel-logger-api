package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public final class EventRecordContext {
    private final Map<String, Object> contentMap;

    public EventRecordContext() {
        this.contentMap = new java.util.TreeMap<>();
    }

    public EventRecordContext(@Nonnull Consumer<EventRecordContext> contextConsumer) {
        this();
        contextConsumer.accept(this);
    }

    public EventRecordContext put(String key, Object value) {
        this.contentMap.put(key, value);
        return this;
    }

    @Nonnull
    public Map<String, Object> toMap() {
        return contentMap;
    }
}
