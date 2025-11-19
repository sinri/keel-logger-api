package io.github.sinri.keel.logger.api.factory;

import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;
import io.github.sinri.keel.logger.api.logger.Logger;
import io.github.sinri.keel.logger.api.logger.SpecificLogger;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * 日志记录器工厂。
 * <p>
 * 日志记录器工厂负责创建通用和特定格式的日志记录器。
 *
 * @since 5.0.0
 */
public interface LoggerFactory {
    /**
     * 本日志记录器工厂所创建的日志记录器所共用的日志处理器。
     *
     * @return 日志写入适配器
     */
    LogWriterAdapter sharedAdapter();

    /**
     * 创建某一主题下的日志记录器。
     *
     * @param topic 主题
     * @return 日志记录器。
     */
    Logger createLogger(@NotNull String topic);

    /**
     * 创建某一主题下的特定日志记录器。
     *
     * @param topic               主题
     * @param specificLogSupplier 特定日志记录的构造器。
     * @param <L>                 特定日志记录的类型。
     * @return 特定日志记录器。
     */
    <L extends SpecificLog<L>> SpecificLogger<L> createLogger(@NotNull String topic, @NotNull Supplier<L> specificLogSupplier);

}
