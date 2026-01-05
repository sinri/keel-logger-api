package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将{@link SpecificLog}实例渲染为字符串表述。
 *
 * @since 5.0.0
 */
@NullMarked
public interface LogTextRender {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    /**
     * 渲染日志的分类信息为字符串。
     *
     * @param classification 日志的分类信息
     * @return 渲染后的分类信息字符串
     */
    default String renderClassification(List<String> classification) {
        return String.join(",", classification);
    }

    /**
     * 渲染日志的异常信息为字符串。
     *
     * @param throwable 日志对应的异常对象
     * @return 渲染后的异常信息字符串
     */
    default String renderThrowable(Throwable throwable) {
        return ThrowableRender.renderThrowableChain(throwable);
    }

    /**
     * 渲染日志的上下文信息为字符串。
     *
     * @param context 日志的上下文信息
     * @return 渲染后的上下文信息字符串
     */
    default String renderContext(Map<String, @Nullable Object> context) {
        return context.entrySet().stream()
                      .map(entry -> "\t%s:\t%s".formatted(entry.getKey(), entry.getValue()))
                      .collect(Collectors.joining("\n"));
    }

    /**
     * 渲染日志为字符串。
     *
     * @param topic 主题
     * @param log   日志
     * @return 日志经渲染后的字符串
     */
    default String render(String topic, SpecificLog<?> log) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(log.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("* ")
          .append(zonedDateTime.format(formatter)).append(" ")
          .append("[").append(log.level().name()).append("] ")
          .append(topic)
          .append(" <").append(log.threadInfo()).append(">");

        List<String> classification = log.classification();
        if (classification != null && !classification.isEmpty()) {
            sb.append("\n").append(renderClassification(classification));
        }

        String message = log.message();
        if (message != null && !message.isBlank()) {
            sb.append("\n").append(message);
        }
        Throwable exception = log.exception();
        if (exception != null) {
            sb.append("\n")
              .append(renderThrowable(exception));
        }
        Map<String, @Nullable Object> map = log.context().toMap();
        if (!map.isEmpty()) {
            sb.append("\n")
              .append(renderContext(map));
        }

        Map<String, @Nullable Object> extra = log.extra();
        if (!extra.isEmpty()) {
            sb.append("\nExtra as following:\n")
              .append(renderContext(extra));
        }

        return sb.toString();
    }
}
