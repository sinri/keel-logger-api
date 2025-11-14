package io.github.sinri.keel.logger.base.factory;

import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.factory.RecorderFactory;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.base.consumer.BaseTopicRecordConsumer;
import io.github.sinri.keel.logger.base.event.BaseEventRecorder;
import io.github.sinri.keel.logger.base.issue.BaseIssueRecorder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 日志记录器工厂的基础实现。
 * <p>
 * 各方法实现均依赖于基础实现。
 *
 * @since 5.0.0
 */
public class BaseRecorderFactory implements RecorderFactory {
    private final static BaseRecorderFactory instance = new BaseRecorderFactory();
    private final TopicRecordConsumer sharedTopicRecordConsumer;

    protected BaseRecorderFactory() {
        sharedTopicRecordConsumer = BaseTopicRecordConsumer.getInstance();
    }

    public static BaseRecorderFactory getInstance() {
        return instance;
    }

    @Override
    public TopicRecordConsumer sharedTopicRecordConsumer() {
        return sharedTopicRecordConsumer;
    }

    @Override
    public EventRecorder createEventRecorder(@NotNull String topic) {
        return new BaseEventRecorder(topic, sharedTopicRecordConsumer());
    }

    @Override
    public <L extends IssueRecord<L>> IssueRecorder<L> createIssueRecorder(@NotNull String topic, @NotNull Supplier<L> issueRecordSupplier) {
        return new BaseIssueRecorder<>(topic, issueRecordSupplier, sharedTopicRecordConsumer());
    }
}
