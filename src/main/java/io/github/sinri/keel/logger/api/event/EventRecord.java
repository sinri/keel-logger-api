package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * 事件日志记录
 *
 * @since 5.0.0
 */
public class EventRecord {
    public final static String MapKeyContext = "context";
    public final static String MapKeyMessage = "message";
    public final static String MapKeyClassification = "classification";
    public final static String MapKeyLevel = "level";
    public final static String MapKeyException = "exception";
    /**
     * 事件日志记录所在的线程线程
     */
    @NotNull
    private final String threadInfo;
    /**
     * 事件日志记录的上下文
     */
    @NotNull
    private final EventRecordContext eventRecordContext;
    /**
     * 事件日志记录的时间戳
     */
    private final long timestamp;
    /**
     * 事件日志记录在标准字段之外的扩展记录
     */
    @NotNull
    private final Map<String, Object> extra = new TreeMap<>();
    /**
     * 事件日志记录的消息内容（可选）
     */
    @Nullable
    private String message;
    /**
     * 事件日志记录的日志严重性等级
     */
    @NotNull
    private LogLevel level;
    /**
     * 事件日志记录的异常信息（可选）
     */
    @Nullable
    private Throwable exception;
    /**
     * 事件日志记录的分类（可选）
     */
    @Nullable
    private List<String> classification;

    public EventRecord() {
        this.timestamp = System.currentTimeMillis();
        this.level = LogLevel.INFO;
        this.threadInfo = Thread.currentThread().toString();
        this.eventRecordContext = new EventRecordContext();
    }

    /**
     * 设置当前事件日志记录的日志严重性等级。
     *
     * @param level 日志严重性等级
     * @return 当前事件日志记录
     */
    public EventRecord level(@NotNull LogLevel level) {
        this.level = level;
        return this;
    }

    /**
     * 设置当前事件日志记录的消息内容。
     *
     * @param message 消息内容
     * @return 当前事件日志记录
     */
    public EventRecord message(@NotNull String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置当前事件日志记录的异常信息。
     *
     * @param throwable 异常信息
     * @return 当前事件日志记录
     */
    public EventRecord exception(@NotNull Throwable throwable) {
        this.exception = throwable;
        return this;
    }

    /**
     * 为当前事件日志记录添加一条上下文信息。
     *
     * @param contextKey   上下文键
     * @param contextValue 上下文值
     * @return 当前事件日志记录
     */
    public EventRecord context(@NotNull String contextKey, @Nullable Object contextValue) {
        this.eventRecordContext.put(contextKey, contextValue);
        return this;
    }

    /**
     * 读写当前事件日志记录的上下文信息。
     *
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     * @return 当前事件日志记录
     */
    public EventRecord context(@NotNull Consumer<EventRecordContext> contextConsumer) {
        contextConsumer.accept(this.eventRecordContext);
        return this;
    }

    /**
     * 获取当前事件日志记录的时间戳。
     *
     * @return 时间戳
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * 获取当前事件日志记录的线程信息。
     *
     * @return 线程信息
     */
    @NotNull
    public String threadInfo() {
        return threadInfo;
    }

    /**
     * 获取当前事件日志记录的上下文信息。
     *
     * @return 上下文信息
     */
    @NotNull
    public EventRecordContext context() {
        return eventRecordContext;
    }

    /**
     * 获取当前事件日志记录的消息内容。
     *
     * @return 消息内容
     */
    @Nullable
    public String message() {
        return message;
    }

    /**
     * 获取当前事件日志记录的日志严重性等级。
     *
     * @return 日志严重性等级
     */
    @NotNull
    public LogLevel level() {
        return level;
    }

    /**
     * 获取当前事件日志记录的异常信息。
     *
     * @return 异常信息
     */
    @Nullable
    public Throwable exception() {
        return exception;
    }

    /**
     * 获取当前事件日志记录的分类。
     *
     * @return 分类
     */
    @Nullable
    public List<String> classification() {
        return classification;
    }

    /**
     * 设置当前事件日志记录的分类。
     *
     * @param classification 分类
     * @return 当前事件日志记录
     */
    @NotNull
    public EventRecord classification(@Nullable List<String> classification) {
        this.classification = classification;
        return this;
    }

    /**
     * 向事件日志记录的扩展记录添加一条记录。
     *
     * @param key   扩展记录的键
     * @param value 扩展记录的值
     * @return 当前事件日志记录
     */
    @NotNull
    protected EventRecord extra(@NotNull String key, @Nullable Object value) {
        this.extra.put(key, value);
        return this;
    }

    /**
     * 获取当前事件日志记录的扩展记录。
     *
     * @return 扩展记录
     */
    @NotNull
    public Map<String, Object> extra() {
        return extra;
    }
}
