package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.consumer.BaseTopicRecordConsumer;
import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import org.jetbrains.annotations.NotNull;


/**
 * 事件日志记录器的基础实现。
 *
 * @since 5.0.0
 */
public class BaseEventRecorder implements EventRecorder {
    @NotNull
    private final String topic;
    private final TopicRecordConsumer topicRecordConsumer;
    private LogLevel level;

    /**
     * 事件日志记录器的基础实现的构造方法。
     *
     * @param topic               主题
     * @param topicRecordConsumer 主题化日志记录处理器
     */
    public BaseEventRecorder(@NotNull String topic, @NotNull TopicRecordConsumer topicRecordConsumer) {
        this.topic = topic;
        this.level = LogLevel.INFO;
        this.topicRecordConsumer = topicRecordConsumer;
    }

    /**
     * 事件日志记录器的基础实现的构造方法。
     *
     * @param topic 主题
     */
    public BaseEventRecorder(@NotNull String topic) {
        this(topic, BaseTopicRecordConsumer.getInstance());
    }

    /**
     * 获取事件日志记录器的最低可见日志严重性等级。
     *
     * @return 事件日志记录器的最低可见日志严重性等级
     */
    @NotNull
    @Override
    public LogLevel visibleLevel() {
        return level;
    }

    /**
     * 设置事件日志记录器的最低可见日志严重性等级。
     * @param level 最低可见日志严重性等级
     * @return 当前事件日志记录器
     */
    @NotNull
    @Override
    public EventRecorder visibleLevel(@NotNull LogLevel level) {
        this.level = level;
        return this;
    }

    /**
     * 获取事件日志记录器的主题。
     * @return 事件日志记录器的主题
     */
    @NotNull
    @Override
    public String topic() {
        return topic;
    }

    /**
     * 获取事件日志记录器的主题记录消费者。
     * @return 事件日志记录器的主题化日志记录处理器
     */
    @NotNull
    @Override
    public TopicRecordConsumer consumer() {
        return topicRecordConsumer;
    }

}
