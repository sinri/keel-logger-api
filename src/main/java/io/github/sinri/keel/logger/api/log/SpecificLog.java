package io.github.sinri.keel.logger.api.log;

import io.github.sinri.keel.logger.api.LogLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 特定日志，通过泛型指定特定格式。
 * <p>
 * 必须确保可以通过相应的{@link SpecificLog#toLog()}方法转换为等价的{@link Log}记录。
 *
 * @param <T> 特定问题记录类的一个实现类型
 * @since 5.0.0
 */
@Deprecated(forRemoval = true)
public abstract class SpecificLog<T extends SpecificLog<T>> extends Log {
    /**
     * 返回自身以实现链式调用。
     *
     * @return 当前的特定问题日志记录实例，即自身
     */
    @SuppressWarnings("unchecked")
    public final T getImplementation() {
        return (T) this;
    }

    /**
     * 设置日志严重性等级。
     *
     * @param level 日志严重性等级
     * @return 当前的特定问题日志记录实例
     */
    @Override
    public T level(@NotNull LogLevel level) {
        super.level(level);
        return getImplementation();
    }

    /**
     * 设置消息内容。
     *
     * @param message 消息内容
     * @return 当前的特定问题日志记录实例
     */
    @Override
    public T message(@NotNull String message) {
        super.message(message);
        return getImplementation();
    }

    /**
     * 设置上下文键值对。
     *
     * @param contextKey   上下文键
     * @param contextValue 上下文值
     * @return 当前的特定问题日志记录实例
     */
    @Override
    public T context(@NotNull String contextKey, @Nullable Object contextValue) {
        super.context(contextKey, contextValue);
        return getImplementation();
    }

    /**
     * 设置异常信息。
     *
     * @param throwable 异常信息
     * @return 当前的特定问题日志记录实例
     */
    @Override
    public T exception(@NotNull Throwable throwable) {
        super.exception(throwable);
        return getImplementation();
    }

    /**
     * 设置分类。
     *
     * @param classification 分类
     * @return 当前的特定问题日志记录实例
     */
    @NotNull
    @Override
    public T classification(@Nullable List<String> classification) {
        super.classification(classification);
        return getImplementation();
    }

    /**
     * 转换当前的特定问题日志记录实例为一个标准的事件日志记录类实例，确保不丢失任何日志内容。
     * <p>
     * 提供了默认实现，即当特定问题日志记录类里不存在{@link Log}中未定义的额外字段时，其本身根据继承直接返回即可。
     *
     * @return 事件日志记录类实例
     */
    @NotNull
    public Log toLog() {
        return this;
    }
}
