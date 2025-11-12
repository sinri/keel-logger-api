package io.github.sinri.keel.logger.base.factory;

import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.factory.RecorderFactory;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.base.consumer.BaseTopicRecordConsumer;
import io.github.sinri.keel.logger.base.event.BaseEventRecorder;
import io.github.sinri.keel.logger.base.issue.BaseIssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BaseRecorderFactory implements RecorderFactory {
    private final TopicRecordConsumer sharedTopicRecordConsumer = new BaseTopicRecordConsumer();

    @Override
    public TopicRecordConsumer sharedTopicRecordConsumer() {
        return sharedTopicRecordConsumer;
    }

    @Override
    public EventRecorder createEventRecorder(@Nonnull String topic) {
        return new BaseEventRecorder(topic, sharedTopicRecordConsumer());
    }

    @Override
    public <L extends IssueRecord<L>> IssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier) {
        return new BaseIssueRecorder<>(topic, issueRecordSupplier, sharedTopicRecordConsumer());
    }
}
