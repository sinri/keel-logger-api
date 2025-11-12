package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface IssueRecorderFactory {
    <L extends IssueRecord<L>> IssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier);
}
