package io.github.sinri.keel.logger.api;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 延迟初始化容器。容器内可包含一个非空值，其在初始化后不可变更。
 * <p>
 * 用于表达“先声明，后赋值”的语义：在值尚未设置前读取会抛出异常；在值已设置后再次设置会抛出异常。
 * <p>
 * 并发语义：`value` 为 `volatile`，保证跨线程可见性；`set` 为 `synchronized`，保证“仅赋值一次”；
 * `ensure` 采用双重检查锁定（DCL），正常情况下 supplier 仅调用一次（supplier 抛异常未赋值时会重试）。
 * <p>
 * @see <a href="https://dart.dev/language/variables#late-variables">Dart Late variables</a>
 * @since 5.0.0
 */
@NullMarked
public final class LateObject<T> {
    private volatile @Nullable T value;

    /**
     * 构造一个未初始化的延迟对象。
     */
    public LateObject() {
        this.value = null;
    }

    /**
     * 为本对象设置值。
     * <p>
     * 该操作只允许执行一次；若已设置过值，将抛出异常。
     *
     * @param value 要设置的值
     * @throws IllegalStateException 当本对象已设置过值时抛出
     */
    public synchronized void set(T value) {
        if (this.value != null) {
            throw new IllegalStateException("LateObject variable was already assigned");
        } else {
            this.value = value;
        }
    }

    /**
     * 获取已设置的值。
     *
     * @return 已设置的值
     * @throws IllegalStateException 当本对象尚未设置值时抛出
     */
    public T get() {
        if (this.value == null) {
            throw new IllegalStateException("LateObject variable was read before being assigned");
        }
        return Objects.requireNonNull(value);
    }

    /**
     * 确保本对象已初始化：若尚未初始化，则使用 supplier 计算并设置值。
     * <p>
     * 采用双重检查锁定（DCL）：`value` 为 `volatile`，正常情况下 supplier 仅被调用一次。
     * 若 supplier 抛出异常导致未赋值，后续调用会重试（即 supplier 可能被多次调用）。
     *
     * @param supplier 用于生成值的逻辑
     * @return 已初始化的值
     * @throws RuntimeException 当 supplier 抛出异常时，将被包装后抛出
     */
    public T ensure(Supplier<T> supplier) {
        if (this.value == null) {
            synchronized (this) {
                if (this.value == null) {
                    T t;
                    try {
                        t = supplier.get();
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                    this.value = t;
                }
            }
        }
        return this.value;
    }

    /**
     * 判断本对象是否已初始化。
     *
     * @return 已初始化返回 {@code true}，否则返回 {@code false}
     */
    public boolean isInitialized() {
        return value != null;
    }
}
