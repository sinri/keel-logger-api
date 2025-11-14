package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 日志记录器工厂。
 * <p>
 * 日志记录器工厂负责创建通用和特定格式的日志记录器。
 *
 * @since 5.0.0
 */
public interface RecorderFactory {
    /**
     * 本日志记录器工厂所创建的日志记录器所共用的主题化日志记录处理器。
     *
     * @return 主题化日志记录处理器
     */
    TopicRecordConsumer sharedTopicRecordConsumer();

    /**
     * 创建某一主题下的事件日志记录器。
     *
     * @param topic 主题
     * @return 事件日志记录器。
     */
    EventRecorder createEventRecorder(@NotNull String topic);

    /**
     * 创建某一主题下的问题日志记录器。
     *
     * @param topic               主题
     * @param issueRecordSupplier 问题日志记录的构造器。
     * @param <L>                 问题日志记录的类型。
     * @return 问题日志记录器。
     */
    <L extends IssueRecord<L>> IssueRecorder<L> createIssueRecorder(@NotNull String topic, @NotNull Supplier<L> issueRecordSupplier);

}
