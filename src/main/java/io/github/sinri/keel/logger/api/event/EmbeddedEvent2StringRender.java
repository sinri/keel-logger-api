package io.github.sinri.keel.logger.api.event;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 5.0.0
 */
class EmbeddedEvent2StringRender implements EventRender<String> {
    private static final EmbeddedEvent2StringRender instance = new EmbeddedEvent2StringRender();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    private EmbeddedEvent2StringRender() {
    }

    public static EmbeddedEvent2StringRender getInstance() {
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
}
