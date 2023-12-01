package utils;

@FunctionalInterface
public interface UnsafeCallable<V> {
    V call();
}
