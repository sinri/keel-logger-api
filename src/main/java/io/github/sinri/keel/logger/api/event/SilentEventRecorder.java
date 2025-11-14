package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.consumer.InstantTopicRecordConsumer;
import io.github.sinri.keel.logger.api.consumer.TopicRecordConsumer;
import org.jetbrains.annotations.NotNull;

/**
 * 一个保持沉默的事件日志记录器。
 * <p>
 * 不可以变更日志显示级别，会抛出运行时异常。
 *
 * @since 5.0.0
 */
public final class SilentEventRecorder implements EventRecorder {
    private static final SilentConsumer silentConsumer = new SilentConsumer();
    private final String topic;

    public SilentEventRecorder(@NotNull String topic) {
        this.topic = topic;
    }

    @Override
    @NotNull
    public LogLevel visibleLevel() {
        return LogLevel.SILENT;
    }

    @NotNull
    @Override
    public EventRecorder visibleLevel(@NotNull LogLevel level) {
        throw new UnsupportedOperationException("SilentEventRecorder can not change visible level");
    }

    @Override
    public @NotNull String topic() {
        return topic;
    }

    @Override
    public @NotNull TopicRecordConsumer consumer() {
        return silentConsumer;
    }

    @Override
    public void recordEvent(@NotNull EventRecord eventRecord) {
        // do nothing to keep silent
    }

    private static class SilentConsumer implements InstantTopicRecordConsumer {

        @Override
        public void accept(@NotNull String topic, @NotNull EventRecord loggingEntity) {

        }
    }
}
