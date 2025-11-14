package io.github.sinri.keel.logger.api.consumer;

import io.github.sinri.keel.logger.api.event.EventRecord;
import org.jetbrains.annotations.NotNull;


/**
 * 主题化日志记录处理器。
 * <p>
 * 在本项目中，各日志记录器作为特定主题下的日志记录的生产者，
 * 主题化日志记录处理器则作为处理各主题下日志记录的消费者。
 * <p>
 * 主题化日志记录处理器仅支持处理事件日志记录。
 * 特定问题日志记录需要转换后再处理。
 * <p>
 * 根据处理器的特性，可以分为即时处理器和持久处理器。
 *
 * @since 5.0.0
 */
public sealed interface TopicRecordConsumer permits InstantTopicRecordConsumer, PersistentTopicRecordConsumer {
    /**
     * 在指定主题下处理一个事件日志记录。
     *
     * @param topic         主题名称
     * @param loggingEntity 事件日志记录
     */
    void accept(@NotNull String topic, @NotNull EventRecord loggingEntity);
}
