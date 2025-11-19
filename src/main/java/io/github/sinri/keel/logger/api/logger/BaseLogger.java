package io.github.sinri.keel.logger.api.logger;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.BaseLogWriter;
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.Log;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


/**
 * 日志记录器的基础实现。
 *
 * @since 5.0.0
 */
public class BaseLogger implements Logger {
    @NotNull
    private final String topic;
    private final LogWriterAdapter logWriterAdapter;
    private LogLevel level;

    /**
     * 日志记录器的基础实现的构造方法。
     *
     * @param topic            主题
     * @param logWriterAdapter 主题化日志记录处理器
     */
    public BaseLogger(@NotNull String topic, @NotNull LogWriterAdapter logWriterAdapter) {
        this.topic = topic;
        this.level = LogLevel.INFO;
        this.logWriterAdapter = logWriterAdapter;
    }

    /**
     * 日志记录器的基础实现的构造方法。
     *
     * @param topic 主题
     */
    public BaseLogger(@NotNull String topic) {
        this(topic, BaseLogWriter.getInstance());
    }

    /**
     * 获取日志记录器的最低可见日志严重性等级。
     *
     * @return 日志记录器的最低可见日志严重性等级
     */
    @NotNull
    @Override
    public LogLevel visibleLevel() {
        return level;
    }

    /**
     * 设置日志记录器的最低可见日志严重性等级。
     *
     * @param level 最低可见日志严重性等级
     * @return 当前日志记录器
     */
    @NotNull
    @Override
    public Logger visibleLevel(@NotNull LogLevel level) {
        this.level = level;
        return this;
    }

    /**
     * 获取日志记录器的主题。
     *
     * @return 日志记录器的主题
     */
    @NotNull
    @Override
    public String topic() {
        return topic;
    }

    @Override
    public final @NotNull Supplier<Log> specificLogSupplier() {
        return Log::new;
    }

    /**
     * 获取日志记录器的主题记录消费者。
     *
     * @return 日志记录器的主题化日志记录处理器
     */
    @NotNull
    @Override
    public LogWriterAdapter adapter() {
        return logWriterAdapter;
    }

}
