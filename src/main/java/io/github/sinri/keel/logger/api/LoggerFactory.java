package io.github.sinri.keel.logger.api;

import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.record.LogRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface LoggerFactory<R> {
    LogRecorder<R> createLogRecorder();

    EventRecorder<R> createEventLogRecorder();

    <L extends IssueRecord<L>> IssueRecorder<L, R> createIssueRecorder(@Nonnull Supplier<L> issueRecordSupplier);
}
