package io.github.sinri.keel.logger.api.record;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A simple log record render that renders logs to the console.
 *
 * @since 5.0.0
 */
class EmbeddedLogRecordRender implements LogRecordRender<String> {
    private final static EmbeddedLogRecordRender instance = new EmbeddedLogRecordRender();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    private EmbeddedLogRecordRender() {
    }

    public static EmbeddedLogRecordRender getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public String render(@Nonnull String topic, @Nonnull LogRecord logRecord) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(logRecord.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("㏒")
          .append(" ").append(zonedDateTime.format(formatter))
          .append(" <").append(topic).append(">");
        logRecord.contents().forEach(content -> {
            sb.append("\n").append(content.key()).append(": ").append(content.value());
        });
        return sb.toString();
    }
}
