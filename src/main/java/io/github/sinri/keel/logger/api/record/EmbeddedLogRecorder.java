package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;

/**
 * A simple log recorder that records logs to the console.
 *
 * @since 5.0.0
 */
class EmbeddedLogRecorder implements LogRecorder<String> {
    private final String topic;

    public EmbeddedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
    }

    @Override
    public LogRecordRender<String> render() {
        return EmbeddedLogRecordRender.getInstance();
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Override
    public void recordLog(@Nonnull LogRecord record) {
        var s = render().render(topic(), record);
        System.out.println(s);
    }
}
