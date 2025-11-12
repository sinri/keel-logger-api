package io.github.sinri.keel.logger.base.factory;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.adapter.Adapter;
import io.github.sinri.keel.logger.api.adapter.LogWriter;
import io.github.sinri.keel.logger.api.adapter.Render;
import io.github.sinri.keel.logger.api.event.Event2LogRender;
import io.github.sinri.keel.logger.api.event.EventRecord;
import io.github.sinri.keel.logger.api.event.EventRecorder;
import io.github.sinri.keel.logger.api.event.LoggingEventRecorder;
import io.github.sinri.keel.logger.api.factory.LoggingFactory;
import io.github.sinri.keel.logger.api.issue.IssueRecord;
import io.github.sinri.keel.logger.api.issue.IssueRecorder;
import io.github.sinri.keel.logger.api.issue.LoggingIssueRecorder;
import io.github.sinri.keel.logger.api.record.LoggingRecord;
import io.github.sinri.keel.logger.base.adapter.render.BaseEvent2LogRender;
import io.github.sinri.keel.logger.base.adapter.writer.BaseLoggingRecordToStdoutWriter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Deprecated
public class BaseLoggingFactory implements LoggingFactory {
    private final LogWriter<LoggingRecord> writer;
    private final Event2LogRender eventRecordRender;

    public BaseLoggingFactory() {
        this(null, null);
    }

    public BaseLoggingFactory(@Nullable Event2LogRender eventRecordRender, @Nullable LogWriter<LoggingRecord> writer) {
        this.writer = Objects.requireNonNullElseGet(writer, this::initializeDefaultWriter);
        this.eventRecordRender = Objects.requireNonNullElseGet(eventRecordRender, this::initializeDefaultEventRecordRender);
    }

    @Override
    public LoggingEventRecorder createEventRecorder(@Nonnull String topic) {
        return new LoggingEventRecorderImpl(topic, eventRecordRender::render, writer);
    }

    private LogWriter<LoggingRecord> initializeDefaultWriter() {
        return new BaseLoggingRecordToStdoutWriter();
    }

    private Event2LogRender initializeDefaultEventRecordRender() {
        return new BaseEvent2LogRender();
    }

    @Override
    public <L extends IssueRecord<L>> LoggingIssueRecorder<L> createIssueRecorder(@Nonnull String topic, @Nonnull Supplier<L> issueRecordSupplier) {
        return new LoggingIssueRecorderImpl<L>(topic, issueRecordSupplier, eventRecordRender::render, writer);
    }

    static class LoggingEventRecorderImpl implements LoggingEventRecorder {
        private final String topic;
        private final Adapter<EventRecord, LoggingRecord> adapter;
        private LogLevel visibleLevel;

        public LoggingEventRecorderImpl(@Nonnull String topic,
                                        @Nonnull BiFunction<String, EventRecord, LoggingRecord> renderFunc,
                                        @Nonnull LogWriter<LoggingRecord> writer
        ) {
            this.topic = topic;
            this.visibleLevel = LogLevel.INFO;
            this.adapter = Adapter.build(new Render<EventRecord, LoggingRecord>() {
                @Nonnull
                @Override
                public LoggingRecord render(@Nonnull String topic, @Nonnull EventRecord loggingEntity) {
                    return renderFunc.apply(topic, loggingEntity);
                }
            }, writer);
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
                                        BiFunction<String, T, LoggingRecord> renderFunc,
                                        LogWriter<LoggingRecord> writer
        ) {
            this.topic = topic;
            this.visibleLevel = LogLevel.INFO;
            this.issueRecordSupplier = issueRecordSupplier;
            this.adapter = Adapter.build(renderFunc::apply, writer);
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
