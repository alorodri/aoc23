package utils;

public class ProblemPrinter {

    final static String leftAlignFormat = "| %-45s | %-15s |%n";
    public static void startPrinting() {
        System.out.format("+-----------------------------------------------+-----------------+%n");
        System.out.format(leftAlignFormat, "ADVENT OF CODE 2023", "RESULT");
    }

    public static void addSeparation() {
        endPrinting();
    }

    public static void addRow(final String text, final String value) {
        System.out.format(leftAlignFormat, text, value);
    }

    public static void endPrinting() {
        System.out.format("+-----------------------------------------------+-----------------+%n");
    }
}
