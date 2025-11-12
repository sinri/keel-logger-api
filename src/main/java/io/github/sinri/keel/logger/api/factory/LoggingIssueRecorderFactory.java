package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.LoggingIssueRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface LoggingIssueRecorderFactory extends IssueRecorderFactory<LoggingRecord> {
    <L extends IssueRecord<L>> LoggingIssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier);
}
