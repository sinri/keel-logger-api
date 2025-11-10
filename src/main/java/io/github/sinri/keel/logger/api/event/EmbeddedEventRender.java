package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

class EmbeddedEventRender implements EventRender<String> {
    private static final EmbeddedEventRender instance = new EmbeddedEventRender();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    private EmbeddedEventRender() {
    }

    public static EmbeddedEventRender getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public String render(@Nonnull String topic, @Nonnull EventRecord eventRecord) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(eventRecord.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("㏒ ")
          .append(zonedDateTime.format(formatter)).append(" ")
          .append("[").append(eventRecord.level().name()).append("] ")
          .append(topic)
          .append(" <").append(eventRecord.threadInfo()).append(">");
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
}
