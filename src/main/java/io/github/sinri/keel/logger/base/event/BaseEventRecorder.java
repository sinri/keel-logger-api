package io.github.sinri.keel.logger.base.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.base.adapter.BaseTopicRecordConsumer;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public class BaseEventRecorder implements EventRecorder {
    @Nonnull
    private final String topic;
    private final TopicRecordConsumer topicRecordConsumer;
    private LogLevel level;

    public BaseEventRecorder(@Nonnull String topic) {
        this.topic = topic;
        this.level = LogLevel.INFO;
        this.topicRecordConsumer = new BaseTopicRecordConsumer();
    }

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return level;
    }

    @Nonnull
    @Override
    public EventRecorder visibleLevel(@Nonnull LogLevel level) {
        this.level = level;
        return this;
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Nonnull
    @Override
    public TopicRecordConsumer consumer() {
        return topicRecordConsumer;
    }

}
