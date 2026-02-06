package io.github.sinri.keel.logger.api;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 延迟初始化容器。
 * <p>
 * 用于表达“先声明，后赋值”的语义：在值尚未设置前读取会抛出异常；在值已设置后再次设置会抛出异常。
 * <p>
 * 注意：本类不保证并发场景下的可见性与“仅初始化一次”的语义；若在多线程场景使用，请在外部保证同步或仅在单线程内访问。
 * <p>
 * @see <a href="https://dart.dev/language/variables#late-variables">Dart Late variables</a>
 * @since 5.0.0
 */
@NullMarked
public final class LateObject<T> {
    private @Nullable T value;

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
    public void set(T value) {
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
     * 注意：在并发场景下，supplier 可能被调用多次；请保证 supplier 具备幂等性或可接受重复调用。
     *
     * @param supplier 用于生成值的逻辑
     * @return 已初始化的值
     * @throws RuntimeException 当 supplier 抛出异常时，将被包装后抛出
     */
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

    /**
     * 判断本对象是否已初始化。
     *
     * @return 已初始化返回 {@code true}，否则返回 {@code false}
     */
    public boolean isInitialized() {
        return value != null;
    }
}
