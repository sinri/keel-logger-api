package io.github.sinri.keel.logger.api.log;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 事件日志记录的上下文类。
 * <p>
 * 该类以 Map 形式存放数据条目，可据此构建出一个 JSON 对象。
 *
 * @since 5.0.0
 */
@NullMarked
public final class LogContext {
    private final Map<String, @Nullable Object> contentMap;

    public LogContext() {
        this.contentMap = new java.util.TreeMap<>();
    }

    public LogContext(Consumer<LogContext> contextConsumer) {
        this();
        contextConsumer.accept(this);
    }

    public LogContext put(String key, @Nullable Object value) {
        this.contentMap.put(key, value);
        return this;
    }

    /**
     * 返回嵌入的 Map，可对其内容进行读写。
     *
     * @return 嵌入的 Map
     */
    public Map<String, @Nullable Object> toMap() {
        return contentMap;
    }
}
