package io.github.sinri.keel.logger.base.adapter;

import io.github.sinri.keel.logger.api.adapter.InstantTopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseTopicRecordConsumer implements InstantTopicRecordConsumer {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    @Override
    public void accept(@Nonnull String topic, @Nonnull EventRecord loggingEntity) {
        write(render(topic, loggingEntity));
    }

    protected String renderClassification(@Nonnull List<String> classification) {
        return String.join(",", classification);
    }

    protected String renderThrowable(@Nonnull Throwable throwable) {
        try (StringWriter sw = new StringWriter(); PrintWriter printWriter = new PrintWriter(sw)) {
            sw.append(throwable.toString()).append("\n");
            throwable.printStackTrace(printWriter);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String renderContext(@Nonnull Map<String, Object> context) {
        return context.entrySet().stream()
                      .map(entry -> "\t" + entry.getKey() + ":\t" + entry.getValue())
                      .collect(Collectors.joining("\n"));
    }

    protected String render(@Nonnull String topic, @Nonnull EventRecord eventRecord) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(eventRecord.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("㏒ ")
          .append(zonedDateTime.format(formatter)).append(" ")
          .append("[").append(eventRecord.level().name()).append("] ")
          .append(topic)
          .append(" <").append(eventRecord.threadInfo()).append(">");

        List<String> classification = eventRecord.classification();
        if (classification != null && !classification.isEmpty()) {
            sb.append("\n").append(renderClassification(classification));
        }

        String message = eventRecord.message();
        if (message != null && !message.isBlank()) {
            sb.append("\n").append(message);
        }
        Throwable exception = eventRecord.exception();
        if (exception != null) {
            sb.append("\n")
              .append(renderThrowable(exception));
        }
        Map<String, Object> map = eventRecord.context().toMap();
        if (!map.isEmpty()) {
            sb.append("\n")
              .append(renderContext(map));
        }
        return sb.toString();
    }

    protected void write(@Nonnull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
