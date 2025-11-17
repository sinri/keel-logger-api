package io.github.sinri.keel.logger.api.consumer;

import io.github.sinri.keel.logger.api.log.Log;
import org.jetbrains.annotations.NotNull;


/**
 * 日志写入适配器。
 *
 * @since 5.0.0
 */
public sealed interface LogWriterAdapter permits InstantLogWriterAdapter, PersistentLogWriterAdapter {
    /**
     * 在指定主题下，对给定的一个日志进行处理。
     * <p>
     * 常见的处理方式如格式化后写入标准输出、文件等。
     *
     * @param topic         主题名称
     * @param loggingEntity 事件日志记录
     */
    void accept(@NotNull String topic, @NotNull Log loggingEntity);
}
