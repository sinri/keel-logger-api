package io.github.sinri.keel.logger.api.consumer;

import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public sealed interface TopicRecordConsumer permits InstantTopicRecordConsumer, PersistentTopicRecordConsumer {
    void accept(@Nonnull String topic, @Nonnull EventRecord loggingEntity);
}
