package io.github.sinri.keel.logger.api;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @see <a href="https://dart.dev/language/variables#late-variables">Dart Late variables</a>
 * @since 5.0.0
 */
@NullMarked
public final class LateObject<T> {
    private @Nullable T value;

    public LateObject() {
        this.value = null;
    }

    public void set(T value) {
        if (this.value != null) {
            throw new IllegalStateException("LateObject variable was already assigned");
        } else {
            this.value = value;
        }
    }

    public T get() {
        if (this.value == null) {
            throw new IllegalStateException("LateObject variable was read before being assigned");
        }
        return Objects.requireNonNull(value);
    }

    public T ensure(Supplier<T> supplier) {
        if (this.value == null) {
            synchronized (this) {
                T t;
                try {
                    t = supplier.get();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
                this.value = t;
            }
        }
        return this.value;
    }

    public boolean isInitialized() {
        return value != null;
    }
}
