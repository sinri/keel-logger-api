package io.github.sinri.keel.logger.api.event;

import io.github.sinri.keel.logger.api.record.LoggingRecord;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface Event2LogRender extends EventRender<LoggingRecord> {
    @Nonnull
    @Override
    default LoggingRecord render(@Nonnull String topic, @Nonnull EventRecord loggingEntity) {
        var logRecord = new LoggingRecord();
        logRecord.timestamp(loggingEntity.timestamp());
        logRecord.content(EventRecord.MapKeyLevel, loggingEntity.level().toString());
        var message = loggingEntity.message();
        if (message != null) {
            logRecord.content(EventRecord.MapKeyMessage, message);
        }
        Throwable exception = loggingEntity.exception();
        if (exception != null) {
            logRecord.content(EventRecord.MapKeyException, renderThrowable(exception));
        }
        var classification = loggingEntity.classification();
        if (classification != null && !classification.isEmpty()) {
            logRecord.content(EventRecord.MapKeyClassification, renderClassification(classification));
        }
        Map<String, Object> map = loggingEntity.context().toMap();
        if (!map.isEmpty()) {
            logRecord.content(EventRecord.MapKeyContext, renderContext(map));
        }
        return logRecord;
    }

    String renderThrowable(@Nonnull Throwable throwable);

    String renderClassification(@Nonnull List<String> classification);

    String renderContext(@Nonnull Map<String, Object> context);
}
