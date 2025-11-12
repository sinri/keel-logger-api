package io.github.sinri.keel.logger.base.issue;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.base.adapter.BaseTopicRecordConsumer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BaseIssueRecorder<T extends IssueRecord<T>> implements IssueRecorder<T> {
    @Nonnull
    private final String topic;
    @Nonnull
    private final Supplier<T> issueRecordSupplier;
    @Nonnull
    private final TopicRecordConsumer consumer;
    @Nonnull
    private LogLevel visibleLevel;

    public BaseIssueRecorder(@Nonnull String topic, @Nonnull Supplier<T> issueRecordSupplier, @Nonnull TopicRecordConsumer consumer) {
        this.topic = topic;
        this.visibleLevel = LogLevel.INFO;
        this.consumer = consumer;
        this.issueRecordSupplier = issueRecordSupplier;
    }

    public BaseIssueRecorder(@Nonnull String topic, @Nonnull Supplier<T> issueRecordSupplier) {
        this(topic, issueRecordSupplier, new BaseTopicRecordConsumer());
    }

    @Nonnull
    @Override
    public Supplier<T> issueRecordSupplier() {
        return issueRecordSupplier;
    }

    @Nonnull
    @Override
    public TopicRecordConsumer consumer() {
        return consumer;
    }

    @Nonnull
    @Override
    public LogLevel visibleLevel() {
        return visibleLevel;
    }

    @Nonnull
    @Override
    public IssueRecorder<T> visibleLevel(@Nonnull LogLevel level) {
        this.visibleLevel = level;
        return this;
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }
}
