package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;

public final class Issue2EventRender<T extends IssueRecord<T>> implements IssueRecordRender<T, EventRecord> {
    @Nonnull
    @Override
    public EventRecord render(@Nonnull String topic, @Nonnull T loggingEntity) {
        return loggingEntity;
    }
}
