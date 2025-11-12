package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;

public interface Issue2EventRender<T extends IssueRecord<T>> extends IssueRecordRender<T, EventRecord> {
    @Nonnull
    @Override
    default EventRecord render(@Nonnull String topic, @Nonnull T loggingEntity) {
        return loggingEntity;
    }
}
