package io.github.sinri.keel.logger.api.adapter;

import io.github.sinri.keel.logger.api.log.SpecificLog;
import org.jspecify.annotations.NullMarked;

/**
 * 基本日志接入器。
 * <p>
 * 兼容日志写入适配器要求，处理日志为文本并写入标准输出。
 * <p>
 * 本类可直接使用，也可以继承已实现自定义格式的处理器。
 *
 * @since 5.0.0
 */
@NullMarked
public class BaseLogWriter implements LogWriterAdapter, LogTextRender {
    private static final BaseLogWriter instance = new BaseLogWriter();

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
    public void accept(String topic, SpecificLog<?> log) {
        write(render(topic, log));
    }

    /**
     * 将日志渲染结果写入目标（本类实现中是标准输出）。
     *
     * @param renderedEntity 日志渲染结果
     */
    protected void write(String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
