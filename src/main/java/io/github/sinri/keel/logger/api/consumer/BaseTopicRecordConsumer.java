package io.github.sinri.keel.logger.api.consumer;

import io.github.sinri.keel.logger.api.event.EventRecord;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 主题化日志记录即时处理器的基础实现。
 *
 * @since 5.0.0
 */
public class BaseTopicRecordConsumer implements InstantTopicRecordConsumer {
    private static final BaseTopicRecordConsumer instance = new BaseTopicRecordConsumer();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    /**
     * 构造函数。
     * <p>
     * 设定为 protected 级别，确保单例但可继承。
     */
    protected BaseTopicRecordConsumer() {

    }

    /**
     * 获取主题化日志记录即时处理器的基础实现的单例实例。
     *
     * @return 主题化日志记录即时处理器的基础实现的单例实例
     */
    public static BaseTopicRecordConsumer getInstance() {
        return instance;
    }

    @Override
    public void accept(@NotNull String topic, @NotNull EventRecord loggingEntity) {
        write(render(topic, loggingEntity));
    }

    /**
     * 渲染事件日志记录的分类信息为字符串。
     *
     * @param classification 事件日志记录的分类信息
     * @return 渲染后的分类信息字符串
     */
    protected String renderClassification(@NotNull List<String> classification) {
        return String.join(",", classification);
    }

    /**
     * 渲染事件日志记录的异常信息为字符串。
     *
     * @param throwable 异常对象
     * @return 渲染后的异常信息字符串
     */
    protected String renderThrowable(@NotNull Throwable throwable) {
        try (StringWriter sw = new StringWriter(); PrintWriter printWriter = new PrintWriter(sw)) {
            sw.append(throwable.toString()).append("\n");
            throwable.printStackTrace(printWriter);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 渲染事件日志记录的上下文信息为字符串。
     *
     * @param context 事件日志记录的上下文信息
     * @return 渲染后的上下文信息字符串
     */
    protected String renderContext(@NotNull Map<String, Object> context) {
        return context.entrySet().stream()
                      .map(entry -> "\t" + entry.getKey() + ":\t" + entry.getValue())
                      .collect(Collectors.joining("\n"));
    }

    /**
     * 渲染事件日志记录为字符串。
     *
     * @param topic       主题名称
     * @param eventRecord 事件日志记录
     * @return 渲染后的事件日志记录字符串
     */
    protected String render(@NotNull String topic, @NotNull EventRecord eventRecord) {
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

        Map<String, Object> extra = eventRecord.extra();
        if (!extra.isEmpty()) {
            sb.append("\nExtra as following:\n")
              .append(renderContext(extra));
        }

        return sb.toString();
    }

    /**
     * 将渲染后的事件日志记录写入目标（本类实现中是标准输出）。
     *
     * @param renderedEntity 渲染后的事件日志记录字符串
     */
    protected void write(@NotNull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
