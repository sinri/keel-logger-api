package io.github.sinri.keel.logger.api.consumer;

import io.github.sinri.keel.logger.api.event.EventRecord;

/**
 * 主题化日志记录即时处理器。
 * <p>
 * 其特征为不依赖外部持久存在即可实现对象的消费处理。
 * 例如，将 {@link EventRecord} 直接写入控制台，此类操作无需额外维护一个可能独立于本消费者实现的持久化服务。
 *
 * @since 5.0.0
 */
public non-sealed interface InstantTopicRecordConsumer extends TopicRecordConsumer {
}
