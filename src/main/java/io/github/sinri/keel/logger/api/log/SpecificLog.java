package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * 特定日志记录。
 * <p>
 * 本类是可扩展的日志记录实体，内置了时间戳、线程信息、上下文、消息、日志级别、异常与分类等常见字段；
 * 子类可通过 {@link #extra(String, Object)} 写入扩展字段。
 *
 * @since 5.0.0
 */
@NullMarked
public abstract class SpecificLog<T extends SpecificLog<T>> {
    public final static String MapKeyContext = "context";
    public final static String MapKeyMessage = "message";
    public final static String MapKeyClassification = "classification";
    public final static String MapKeyLevel = "level";
    public final static String MapKeyException = "exception";

    /**
     * 特定日志记录所在的线程信息。
     */
    private final String threadInfo;
    /**
     * 特定日志记录的上下文。
     */
    private final LogContext logContext;
    /**
     * 特定日志记录的时间戳。
     */
    private final long timestamp;
    /**
     * 特定日志记录在标准字段之外的扩展字段。
     */
    private final Map<String, @Nullable Object> extra;
    /**
     * 特定日志记录的消息内容（可选）。
     */
    @Nullable
    private String message;
    /**
     * 特定日志记录的日志级别。
     */
    private LogLevel level;
    /**
     * 特定日志记录的异常信息（可选）。
     */
    @Nullable
    private Throwable exception;
    /**
     * 特定日志记录的分类（可选）。
     */
    @Nullable
    private List<String> classification;

    public SpecificLog() {
        this.timestamp = System.currentTimeMillis();
        this.level = LogLevel.INFO;
        this.threadInfo = Thread.currentThread().toString();
        this.logContext = new LogContext();
        this.extra = new TreeMap<>();
    }

    protected SpecificLog(SpecificLog<?> specificLog) {
        super();
        this.timestamp = specificLog.timestamp;
        this.level = specificLog.level;
        this.threadInfo = specificLog.threadInfo;
        this.message = specificLog.message;
        this.exception = specificLog.exception;
        this.classification = specificLog.classification;
        this.logContext = specificLog.logContext;
        this.extra = specificLog.extra;
    }

    /**
     * 返回自身以实现链式调用。
     *
     * @return 当前的特定日志记录实例，即自身
     */
    @SuppressWarnings("unchecked")
    public final T getImplementation() {
        return (T) this;
    }

    /**
     * 设置当前特定日志记录的日志级别。
     *
     * @param level 日志级别
     * @return 当前特定日志记录
     */
    public T level(LogLevel level) {
        this.level = level;
        return getImplementation();
    }

    /**
     * 设置当前特定日志记录的消息内容。
     *
     * @param message 消息内容
     * @return 当前特定日志记录
     */
    public T message(String message) {
        this.message = message;
        return getImplementation();
    }

    /**
     * 设置当前特定日志记录的异常信息。
     *
     * @param throwable 异常信息
     * @return 当前特定日志记录
     */
    public T exception(Throwable throwable) {
        this.exception = throwable;
        return getImplementation();
    }

    /**
     * 为当前特定日志记录添加一条上下文信息。
     *
     * @param contextKey   上下文键
     * @param contextValue 上下文值
     * @return 当前特定日志记录
     */
    public T context(String contextKey, @Nullable Object contextValue) {
        this.logContext.put(contextKey, contextValue);
        return getImplementation();
    }

    /**
     * 读写当前特定日志记录的上下文信息。
     *
     * @param contextConsumer 对上下文信息进行读写的处理逻辑
     * @return 当前特定日志记录
     */
    public T context(Consumer<LogContext> contextConsumer) {
        contextConsumer.accept(this.logContext);
        return getImplementation();
    }

    /**
     * 获取当前特定日志记录的时间戳。
     *
     * @return 时间戳
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * 获取当前特定日志记录的线程信息。
     *
     * @return 线程信息
     */
    public String threadInfo() {
        return threadInfo;
    }

    /**
     * 获取当前特定日志记录的上下文信息。
     *
     * @return 上下文信息
     */
    public LogContext context() {
        return logContext;
    }

    /**
     * 获取当前特定日志记录的消息内容。
     *
     * @return 消息内容
     */
    @Nullable
    public String message() {
        return message;
    }

    /**
     * 获取当前特定日志记录的日志级别。
     *
     * @return 日志级别
     */
    public LogLevel level() {
        return level;
    }

    /**
     * 获取当前特定日志记录的异常信息。
     *
     * @return 异常信息
     */
    @Nullable
    public Throwable exception() {
        return exception;
    }

    /**
     * 获取当前特定日志记录的分类。
     *
     * @return 分类
     */
    @Nullable
    public List<String> classification() {
        return classification;
    }

    /**
     * 设置当前特定日志记录的分类。
     *
     * @param classification 分类
     * @return 当前特定日志记录
     */
    public T classification(@Nullable List<String> classification) {
        this.classification = classification;
        return getImplementation();
    }

    /**
     * 向特定日志记录的扩展字段添加一条记录。
     *
     * @param key   扩展字段的键
     * @param value 扩展字段的值
     * @return 当前特定日志记录
     */
    protected T extra(String key, @Nullable Object value) {
        this.extra.put(key, value);
        return getImplementation();
    }

    /**
     * 获取当前特定日志记录的扩展字段。
     *
     * @return 扩展字段
     */
    public Map<String, @Nullable Object> extra() {
        return extra;
    }
}
