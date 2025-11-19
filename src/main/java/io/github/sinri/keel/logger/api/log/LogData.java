package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class LogData {
    /**
     * 事件日志记录所在的线程线程
     */
    @NotNull
    public String threadInfo;
    /**
     * 事件日志记录的上下文
     */
    @NotNull
    public LogContext logContext;
    /**
     * 事件日志记录的时间戳
     */
    public long timestamp;
    /**
     * 事件日志记录在标准字段之外的扩展记录
     */
    @NotNull
    public Map<String, Object> extra;
    /**
     * 事件日志记录的消息内容（可选）
     */
    @Nullable
    public String message;
    /**
     * 事件日志记录的日志严重性等级
     */
    @NotNull
    public LogLevel level;
    /**
     * 事件日志记录的异常信息（可选）
     */
    @Nullable
    public Throwable exception;
    /**
     * 事件日志记录的分类（可选）
     */
    @Nullable
    public List<String> classification;

    public LogData() {
        this.logContext = new LogContext();
        this.extra = new TreeMap<>();
    }
}
