package utils;

import java.util.concurrent.Callable;

public class Utils {
    public static <T> T cast(Object value, Class<T> type) {
        if (value instanceof Integer) {
            if (String.class.equals(type)) return type.cast(value.toString());
        } else if (value instanceof String) {
            if (Integer.class.equals(type)) return type.cast(Integer.parseInt((String) value));
        }
        return type.cast(value);
    }

    public static String measureTime(UnsafeCallable<String> method) {
        long start = System.nanoTime();
        final String result;

        result = method.call();

        final long end = System.nanoTime();
        final long durationNanos = end - start;
        final long durationMicro = durationNanos / 1_000;
        final long durationMilli = durationNanos / 1_000_000;
        final double durationSeconds = durationMilli / 1_000.0;
        System.out.printf("Execution time: %dns, %dµs, %dms, %.2fs%n", durationNanos, durationMicro, durationMilli, durationSeconds);
        return result;
    }
}
