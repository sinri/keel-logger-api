package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;

public class LoggingRecordToStdoutWriter implements InstantLogWriter<LoggingRecord> {
    @Override
    public void write(@Nonnull String topic, @Nonnull LoggingRecord renderedEntity) {
        StringToStdoutWriter.getInstance().write(topic, renderLoggingRecord(topic, renderedEntity));
    }

    @Nonnull
    protected String renderLoggingRecord(@Nonnull String topic, @Nonnull LoggingRecord loggingRecord) {
        return "For " + topic + " " + loggingRecord;
    }
}
