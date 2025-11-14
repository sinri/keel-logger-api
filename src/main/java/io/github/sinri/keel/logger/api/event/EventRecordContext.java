package io.github.sinri.keel.logger.api.event;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 事件日志记录的上下文类。
 * <p>
 * 该类以 Map 形式存放数据条目，可据此构建出一个 JSON 对象。
 *
 * @since 5.0.0
 */
public final class EventRecordContext {
    @NotNull
    private final Map<String, Object> contentMap;

    public EventRecordContext() {
        this.contentMap = new java.util.TreeMap<>();
    }

    public EventRecordContext(@NotNull Consumer<EventRecordContext> contextConsumer) {
        this();
        contextConsumer.accept(this);
    }

    @NotNull
    public EventRecordContext put(String key, Object value) {
        this.contentMap.put(key, value);
        return this;
    }

    @NotNull
    public Map<String, Object> toMap() {
        return contentMap;
    }
}
