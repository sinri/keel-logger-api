package io.github.sinri.keel.logger.api.record;

import io.github.sinri.keel.logger.api.adapter.StdoutStringWriter;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A simple log recorder that records logs to the console.
 *
 * @since 5.0.0
 */
class EmbeddedLogRecorder implements LogRecorder {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    private final String topic;
    //private final Adapter<LogRecord, String> adapter;

    public EmbeddedLogRecorder(@Nonnull String topic) {
        this.topic = topic;
        // this.adapter = Adapter.build(EmbeddedLogRecordRender.getInstance(), StdoutStringWriter.getInstance());
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Override
    public void recordLog(@Nonnull LogRecord record) {
        var s = render(topic, record);
        StdoutStringWriter.getInstance().write(topic, s);
    }
    //
    //    @Nonnull
    //    @Override
    //    public Adapter<LogRecord, String> adapter() {
    //        return adapter;
    //    }

    @Nonnull
    private String render(@Nonnull String topic, @Nonnull LogRecord logRecord) {
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
