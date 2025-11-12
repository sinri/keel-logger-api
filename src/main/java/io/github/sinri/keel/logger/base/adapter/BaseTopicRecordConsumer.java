package io.github.sinri.keel.logger.base.adapter;

import io.github.sinri.keel.logger.api.adapter.InstantTopicRecordConsumer;
import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BaseTopicRecordConsumer implements InstantTopicRecordConsumer {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    @Override
    public void accept(@Nonnull String topic, @Nonnull EventRecord loggingEntity) {
        write(render(topic, loggingEntity));
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
            sb.append("\n").append(String.join(",", classification));
        }

        String message = eventRecord.message();
        if (message != null && !message.isBlank()) {
            sb.append("\n").append(message);
        }
        Throwable exception = eventRecord.exception();
        if (exception != null) {
            sb.append("\n")
              .append(exception);
        }
        var map = eventRecord.context().toMap();
        if (!map.isEmpty()) {
            var s = map.entrySet().stream()
                       .map(entry -> entry.getKey() + "=" + entry.getValue())
                       .collect(Collectors.joining(" "));
            sb.append("\n")
              .append(s);
        }
        return sb.toString();
    }

    protected void write(@Nonnull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
