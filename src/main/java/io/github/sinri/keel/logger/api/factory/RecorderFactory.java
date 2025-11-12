package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface RecorderFactory {
    TopicRecordConsumer sharedTopicRecordConsumer();

    EventRecorder createEventRecorder(@Nonnull String topic);

    <L extends IssueRecord<L>> IssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier);

}
