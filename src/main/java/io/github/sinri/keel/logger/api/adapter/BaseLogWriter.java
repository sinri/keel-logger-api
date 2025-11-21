package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基本日志接入器。
 * <p>
 * 兼容日志写入适配器要求，处理日志为文本并写入标准输出。
 * <p>
 * 本类可直接使用，也可以继承已实现自定义格式的处理器。
 *
 * @since 5.0.0
 */
public class BaseLogWriter implements InstantLogWriterAdapter {
    private static final BaseLogWriter instance = new BaseLogWriter();
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    /**
     * 构造函数。
     * <p>
     * 设定为 protected 级别，确保单例但可继承。
     */
    protected BaseLogWriter() {

    }

    /**
     * 获取单例实例。
     *
     * @return 单例实例
     */
    public static BaseLogWriter getInstance() {
        return instance;
    }

    @Override
    public void accept(@NotNull String topic, @NotNull SpecificLog<?> log) {
        write(render(topic, log));
    }

    /**
     * 渲染日志的分类信息为字符串。
     *
     * @param classification 日志的分类信息
     * @return 渲染后的分类信息字符串
     */
    protected String renderClassification(@NotNull List<String> classification) {
        return String.join(",", classification);
    }

    /**
     * 渲染日志的异常信息为字符串。
     *
     * @param throwable 日志对应的异常对象
     * @return 渲染后的异常信息字符串
     */
    protected String renderThrowable(@NotNull Throwable throwable) {
        return ThrowableRender.renderThrowableChain(throwable);
    }

    /**
     * 渲染日志的上下文信息为字符串。
     *
     * @param context 日志的上下文信息
     * @return 渲染后的上下文信息字符串
     */
    protected String renderContext(@NotNull Map<String, Object> context) {
        return context.entrySet().stream()
                      .map(entry -> "\t" + entry.getKey() + ":\t" + entry.getValue())
                      .collect(Collectors.joining("\n"));
    }

    /**
     * 渲染日志为字符串。
     *
     * @param topic 主题
     * @param log   日志
     * @return 日志经渲染后的字符串
     */
    protected String render(@NotNull String topic, @NotNull SpecificLog<?> log) {
        StringBuilder sb = new StringBuilder();
        var zonedDateTime = Instant.ofEpochMilli(log.timestamp()).atZone(ZoneId.systemDefault());
        sb.append("㏒ ")
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
        Map<String, Object> map = log.context().toMap();
        if (!map.isEmpty()) {
            sb.append("\n")
              .append(renderContext(map));
        }

        Map<String, Object> extra = log.extra();
        if (!extra.isEmpty()) {
            sb.append("\nExtra as following:\n")
              .append(renderContext(extra));
        }

        return sb.toString();
    }

    /**
     * 将日志渲染结果写入目标（本类实现中是标准输出）。
     *
     * @param renderedEntity 日志渲染结果
     */
    protected void write(@NotNull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
