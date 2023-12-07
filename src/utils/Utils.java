package utils;

import java.util.Arrays;
import java.util.HashSet;

public class Utils {
    private static final int[] daysWithTestB = new int[] {
            1, 7
    };
    public static <T> T convert(Object value, Class<T> type) {
        if (value instanceof Integer) {
            if (String.class.equals(type)) return type.cast(value.toString());
        } else if (value instanceof String) {
            if (Integer.class.equals(type)) return type.cast(Integer.parseInt((String) value));
        }
        return type.cast(value);
    }
    
    public static void measureGlobalTime(Runnable method) {
        long start = System.nanoTime();
        
        method.run();
        
        final long end = System.nanoTime();
        final long durationNanos = end - start;
        String valueToPrint = getTimeString(durationNanos);
        ProblemPrinter.addRow("All problems executed in", valueToPrint);
    }

    private static String getTimeString(long durationNanos) {
        final long durationMicro = durationNanos / 1_000;
        final double durationMilli = durationNanos / 1_000_000.0;
        final double durationSeconds = durationMilli / 1_000.0;
        String valueToPrint = "undefined";
        if (durationSeconds >= 1.0d) {
            valueToPrint = String.format("%.2fs", durationSeconds);
        } else if (durationMilli >= 1.0d) {
            valueToPrint = String.format("%.2fms", durationMilli);
        } else if (durationMicro > 0) {
            valueToPrint = String.format("%dµs", durationMicro);
        } else if (durationNanos > 0) {
            valueToPrint = String.format("%dns", durationNanos);
        }
        return valueToPrint;
    }

    public static String measureTime(UnsafeCallable<String> method, ProblemType type) {
        long start = System.nanoTime();
        final String result;

        result = method.call();

        final long end = System.nanoTime();
        final long durationNanos = end - start;
        String valueToPrint = getTimeString(durationNanos);
        char typeToChar = type == ProblemType.A ? 'A' : 'B';
        ProblemPrinter.addRow("Problem "+typeToChar+" executed in", valueToPrint);
        return result;
    }

    public static void executeDay(final int day) {
        try {
            Class<Problem> problem = (Class<Problem>) Class.forName(String.format("day%d.Day%d", day, day));
            var problemInstance = problem.getDeclaredConstructor().newInstance();
            problem.getMethod("test").invoke(problemInstance);
            problem.getMethod("solve").invoke(problemInstance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeAllDays(final int total, final HashSet<Integer> skip) {
        ProblemPrinter.startPrinting();
        ProblemPrinter.addSeparation();
        ProblemPrinter.addSeparation();
        for (int i = 1; i <= total; i++) {
            if (skip.contains(i)) {
                ProblemPrinter.addRow("DAY " + i + " SKIPPED", "");
                ProblemPrinter.addSeparation();
                ProblemPrinter.addSeparation();
                continue;
            }
            ProblemPrinter.addRow("Solving DAY " + i, "");
            ProblemPrinter.addSeparation();
            executeDay(i);
            ProblemPrinter.addSeparation();
            ProblemPrinter.addSeparation();
        }
    }
}
