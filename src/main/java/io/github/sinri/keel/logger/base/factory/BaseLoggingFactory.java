package io.github.sinri.keel.logger.base.factory;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.LogWriter;
import io.github.sinri.keel.logger.api.adapter.Render;
import io.github.sinri.keel.logger.api.event.Event2LogRender;
import io.github.sinri.keel.logger.api.event.EventRecord;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.event.LoggingEventRecorder;
import io.github.sinri.keel.logger.api.factory.LoggingEventRecorderFactory;
import io.github.sinri.keel.logger.api.factory.LoggingIssueRecorderFactory;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.issue.LoggingIssueRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;
import io.github.sinri.keel.logger.base.adapter.render.BaseEvent2LogRender;
import io.github.sinri.keel.logger.base.adapter.render.BaseIssue2LogRender;
import io.github.sinri.keel.logger.base.adapter.writer.LoggingRecordToStdoutWriter;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BaseLoggingFactory implements LoggingEventRecorderFactory, LoggingIssueRecorderFactory {
    private final LogWriter<LoggingRecord> writer;
    private final Event2LogRender eventRecordRender;

    public BaseLoggingFactory() {
        writer = initializeWriter();
        eventRecordRender = initializeEventRecordRender();
    }

    @Override
    public LoggingEventRecorder createEventRecorder(@Nonnull String topic) {
        return new LoggingEventRecorderImpl(topic, eventRecordRender(), writer());
    }

    protected LogWriter<LoggingRecord> initializeWriter() {
        return new LoggingRecordToStdoutWriter();
    }

    protected LogWriter<LoggingRecord> writer() {
        return writer;
    }

    protected Event2LogRender initializeEventRecordRender() {
        return new BaseEvent2LogRender();
    }

    protected Event2LogRender eventRecordRender() {
        return eventRecordRender;
    }

    @Override
    public <L extends IssueRecord<L>> LoggingIssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier) {
        return new LoggingIssueRecorderImpl<L>(topic, issueRecordSupplier, issueRecordRender(), writer());
    }

    protected <L extends IssueRecord<L>> Render<L, LoggingRecord> issueRecordRender() {
        return new BaseIssue2LogRender<>();
    }

    static class LoggingEventRecorderImpl implements LoggingEventRecorder {
        private final String topic;
        private final Adapter<EventRecord, LoggingRecord> adapter;
        private LogLevel visibleLevel;

        public LoggingEventRecorderImpl(@Nonnull String topic,
                                        @Nonnull Render<EventRecord, LoggingRecord> render,
                                        @Nonnull LogWriter<LoggingRecord> writer
        ) {
            this.topic = topic;
            this.visibleLevel = LogLevel.INFO;
            this.adapter = Adapter.build(render, writer);
        }

        @Nonnull
        @Override
        public LogLevel visibleLevel() {
            return visibleLevel;
        }

        @Nonnull
        @Override
        public EventRecorder<LoggingRecord> visibleLevel(@Nonnull LogLevel level) {
            this.visibleLevel = level;
            return this;
        }

        @Nonnull
        @Override
        public String topic() {
            return topic;
        }

        @Nonnull
        @Override
        public Adapter<EventRecord, LoggingRecord> adapter() {
            return adapter;
        }
    }

    static class LoggingIssueRecorderImpl<T extends IssueRecord<T>> implements LoggingIssueRecorder<T> {
        private final String topic;
        private final Adapter<T, LoggingRecord> adapter;
        private final Supplier<T> issueRecordSupplier;
        private LogLevel visibleLevel;

        public LoggingIssueRecorderImpl(String topic,
                                        Supplier<T> issueRecordSupplier,
                                        Render<T, LoggingRecord> render,
                                        LogWriter<LoggingRecord> writer
        ) {
            this.topic = topic;
            this.visibleLevel = LogLevel.INFO;
            this.issueRecordSupplier = issueRecordSupplier;
            this.adapter = Adapter.build(render, writer);
        }

        @Nonnull
        @Override
        public Supplier<T> issueRecordSupplier() {
            return issueRecordSupplier;
        }

        @Nonnull
        @Override
        public Adapter<T, LoggingRecord> adapter() {
            return adapter;
        }

        @Nonnull
        @Override
        public LogLevel visibleLevel() {
            return visibleLevel;
        }

        @Nonnull
        @Override
        public IssueRecorder<T, LoggingRecord> visibleLevel(@Nonnull LogLevel level) {
            this.visibleLevel = level;
            return this;
        }

        @Nonnull
        @Override
        public String topic() {
            return topic;
        }
    }
}
