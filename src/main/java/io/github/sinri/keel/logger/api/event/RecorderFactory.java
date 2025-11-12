package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.issue.LoggingIssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface RecorderFactory<R> {
    // LogRecorder<R> createLogRecorder(@Nonnull String topic);

    EventRecorder<R> createEventLogRecorder(@Nonnull String topic);

    <L extends IssueRecord<L>> IssueRecorder<L, R> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier);

    <L extends IssueRecord<L>> LoggingIssueRecorder<L> createLoggingIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier);
}
