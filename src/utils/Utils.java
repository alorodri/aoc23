package utils;

import java.util.Arrays;

public class Utils {
    private static final int[] daysWithTestB = new int[] {
            1
    };
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

    public static void executeDay(final int day, boolean hasTestB) {
        try {
            Class<Problem> problem = (Class<Problem>) Class.forName(String.format("day%d.Day%d", day, day));
            var problemInstance = problem.getDeclaredConstructor().newInstance();
            problem.getMethod("test").invoke(problemInstance);
            problem.getMethod("solve").invoke(problemInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeAllDays(final int total) {
        for (int i = 1; i <= total; i++) {
            int finalI = i;
            boolean hasTestB = false;
            if (Arrays.stream(daysWithTestB).anyMatch(day -> day == finalI)) {
                hasTestB = true;
            }
            executeDay(i, hasTestB);
        }
    }
}
