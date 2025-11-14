package io.github.sinri.keel.logger.api.consumer;

import java.io.Closeable;

/**
 * 主题化日志记录持久处理器。
 * <p>
 * 特征为依赖外部持久存在来实现对象的消费处理；
 * 这一类消费者实现往往是异步完成处理的，加上了 {@link Closeable} 接口实现的要求。
 *
 * @since 5.0.0
 */
public non-sealed interface PersistentTopicRecordConsumer extends TopicRecordConsumer, Closeable {
}
