package io.github.sinri.keel.logger.base.record;

import io.github.sinri.keel.logger.api.record.LoggingRecord;
import io.github.sinri.keel.logger.api.record.LoggingRecorder;
import io.github.sinri.keel.logger.base.adapter.writer.BaseStringToStdoutWriter;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * A simple log recorder that records logs to the console.
 *
 * @since 5.0.0
 */
@Deprecated
public class BaseLoggingRecorder implements LoggingRecorder {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    private final String topic;
    //private final Adapter<LogRecord, String> adapter;

    public BaseLoggingRecorder(@Nonnull String topic) {
        this.topic = topic;
        // this.adapter = Adapter.build(EmbeddedLogRecordRender.getInstance(), StdoutStringWriter.getInstance());
    }

    @Nonnull
    @Override
    public String topic() {
        return topic;
    }

    @Override
    public void recordLog(@Nonnull LoggingRecord record) {
        var s = render(topic, record);
        BaseStringToStdoutWriter.getInstance().write(topic, s);
    }
    //
    //    @Nonnull
    //    @Override
    //    public Adapter<LogRecord, String> adapter() {
    //        return adapter;
    //    }

    @Nonnull
    private String render(@Nonnull String topic, @Nonnull LoggingRecord loggingRecord) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(loggingRecord.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("㏒")
          .append(" ").append(zonedDateTime.format(formatter))
          .append(" <").append(topic).append(">");
        loggingRecord.contents().forEach(content -> {
            sb.append("\n").append(content.key()).append(": ").append(content.value());
        });
        return sb.toString();
    }
}
