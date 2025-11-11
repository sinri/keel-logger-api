package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.adapter.Render;
import io.github.sinri.keel.logger.api.event.Event2LogRender;
import io.github.sinri.keel.logger.api.record.LogRecord;

import javax.annotation.Nonnull;

public interface Issue2LogRender<T extends IssueRecord<T>> extends Render<T, LogRecord> {
    @Nonnull
    Issue2EventRender<T> issue2EventRender();

    @Nonnull
    Event2LogRender event2LogRender();

    @Nonnull
    @Override
    default LogRecord render(@Nonnull String topic, @Nonnull T loggingEntity) {
        return event2LogRender()
                .render(
                        topic,
                        issue2EventRender()
                                .render(topic, loggingEntity)
                );
    }
}
