package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.LogLevel;
import io.github.sinri.keel.logger.api.event.EventRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @param <T> the type of implementation
 * @since 5.0.0
 */
public abstract class IssueRecord<T extends IssueRecord<T>> extends EventRecord {
    @SuppressWarnings("unchecked")
    public T getImplementation() {
        return (T) this;
    }

    @Override
    public T level(@Nonnull LogLevel level) {
        super.level(level);
        return getImplementation();
    }

    @Override
    public T message(@Nonnull String message) {
        super.message(message);
        return getImplementation();
    }

    @Override
    public T context(@Nonnull String contextKey, @Nullable Object contextValue) {
        super.context(contextKey, contextValue);
        return getImplementation();
    }

    public T exception(@Nonnull Throwable throwable) {
        super.exception(throwable);
        return getImplementation();
    }
}
