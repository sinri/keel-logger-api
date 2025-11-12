package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public interface LoggingRecorderFactory extends RecorderFactory<LoggingRecord> {

    default <L extends IssueRecord<L>> IssueRecorder<L, LoggingRecord> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier) {
        return createLoggingIssueRecorder(topic, issueRecordSupplier);
    }

}
