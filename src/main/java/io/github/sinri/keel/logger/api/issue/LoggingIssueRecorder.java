package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.record.LoggingRecord;

@Deprecated
public interface LoggingIssueRecorder<T extends IssueRecord<T>> extends IssueRecorder<T, LoggingRecord> {
}
