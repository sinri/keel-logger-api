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
 * 并发语义：{@code value} 为 {@code volatile}，保证跨线程可见性；{@link #set(Object)} 与初始化过程互斥，
 * 保证“仅赋值一次”；{@link #ensure(Supplier)} 采用双重检查锁定（DCL），正常情况下 supplier 仅调用一次。
 * supplier 抛异常时不会完成初始化，后续调用可以重试。
 * <p>
 * @see <a href="https://dart.dev/language/variables#late-variables">Dart Late variables</a>
 * @since 5.0.0
 */
@NullMarked
public final class LateObject<T> {
    private volatile @Nullable T value;
    private boolean syncInitializing;

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
     * @throws NullPointerException  当 value 为 {@code null} 时抛出
     * @throws IllegalStateException 当本对象已设置过值时抛出
     */
    public synchronized void set(T value) {
        if (this.value != null) {
            throw new IllegalStateException("LateObject variable was already assigned");
        }
        if (syncInitializing) {
            throw new IllegalStateException("LateObject variable is being initialized");
        }
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * 获取已设置的值。
     *
     * @return 已设置的值
     * @throws IllegalStateException 当本对象尚未设置值时抛出
     */
    public T get() {
        T current = this.value;
        if (current == null) {
            throw new IllegalStateException("LateObject variable was read before being assigned");
        }
        return current;
    }

    /**
     * 确保本对象已初始化：若尚未初始化，则使用 supplier 计算并设置值。
     * <p>
     * 采用双重检查锁定（DCL）：{@code value} 为 {@code volatile}，正常情况下 supplier 仅被调用一次。
     * 若 supplier 抛出异常导致未赋值，后续调用会重试（即 supplier 可能被多次调用）。
     * supplier 返回 {@code null}，或者 supplier 重入初始化当前对象，都会抛出异常且不完成初始化。
     *
     * @param supplier 用于生成值的逻辑
     * @return 已初始化的值
     * @throws NullPointerException  当 supplier 或其返回值为 {@code null} 时抛出
     * @throws IllegalStateException 当 supplier 重入初始化当前对象时抛出
     */
    public T ensure(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        T current = this.value;
        if (current == null) {
            synchronized (this) {
                current = this.value;
                if (current == null) {
                    if (syncInitializing) {
                        throw new IllegalStateException("LateObject variable is already being initialized");
                    }
                    syncInitializing = true;
                    try {
                        current = Objects.requireNonNull(supplier.get(), "supplier returned null");
                        this.value = current;
                    } finally {
                        syncInitializing = false;
                    }
                }
            }
        }
        return current;
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
