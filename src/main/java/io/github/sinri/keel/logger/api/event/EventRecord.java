package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.log.LogRecord;
import io.github.sinri.keel.logger.api.log.LogRecordCompatible;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collectors;

public class EventRecord implements LogRecordCompatible {
    public final static String MapKeyContext = "context";
    public final static String MapKeyMessage = "message";
    //public final static String MapKeyClassification = "classification";
    public final static String MapKeyLevel = "level";
    public final static String MapKeyException = "exception";
    @Nonnull
    private final String threadInfo;
    @Nonnull
    private final Context context;
    private final long timestamp;
    @Nullable
    private String message;
    @Nonnull
    private LogLevel level;
    @Nullable
    private Throwable exception;

    public EventRecord() {
        this.timestamp = System.currentTimeMillis();
        this.level = LogLevel.INFO;
        this.threadInfo = Thread.currentThread().toString();
        this.context = new Context();
    }

    public EventRecord level(LogLevel level) {
        this.level = level;
        return this;
    }

    public EventRecord message(@Nonnull String message) {
        this.message = message;
        return this;
    }

    public EventRecord exception(@Nonnull Throwable throwable) {
        this.exception = throwable;
        return this;
    }

    public EventRecord context(@Nonnull String contextKey, @Nullable Object contextValue) {
        this.context.put(contextKey, contextValue);
        return this;
    }


    public long timestamp() {
        return timestamp;
    }

    @Nonnull
    public String threadInfo() {
        return threadInfo;
    }

    @Nonnull
    public Context context() {
        return context;
    }

    @Nullable
    public String message() {
        return message;
    }

    @Nonnull
    public LogLevel level() {
        return level;
    }

    @Nullable
    public Throwable exception() {
        return exception;
    }

    @Override
    public LogRecord toLogRecord() {
        LogRecord logRecord = new LogRecord();
        logRecord.timestamp(timestamp());
        logRecord.addContent(EventRecord.MapKeyLevel, level().toString());

        String message = message();
        if (message != null) {
            logRecord.addContent(EventRecord.MapKeyMessage, message);
        }

        Throwable exception = exception();
        if (exception != null) {
            logRecord.addContent(EventRecord.MapKeyException, exception.toString());
        }

        Map<String, Object> map = context.toMap();
        if (!map.isEmpty()) {
            var s = map.entrySet().stream()
                       .map(entry -> entry.getKey() + "=" + entry.getValue())
                       .collect(Collectors.joining(" "));
            logRecord.addContent(EventRecord.MapKeyContext, s);
        }

        return logRecord;
    }
}
