package io.github.sinri.keel.logger.base.adapter.render;

import io.github.sinri.keel.logger.api.issue.Issue2LogRender;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;

@Deprecated
public class BaseIssue2LogRender<T extends IssueRecord<T>> implements Issue2LogRender<T> {

    private final BaseEvent2LogRender event2LogRender;

    public BaseIssue2LogRender() {
        this.event2LogRender = new BaseEvent2LogRender();
    }


    @Nonnull
    @Override
    public LoggingRecord render(@Nonnull String topic, @Nonnull T loggingEntity) {
        return event2LogRender.render(topic, loggingEntity);
    }
}
