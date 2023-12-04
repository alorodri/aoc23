import utils.ProblemPrinter;
import utils.Utils;

public class Main {
    public static void main(String[] args) {
        Utils.measureGlobalTime(() -> {
            Utils.executeAllDays(4);
        });
        ProblemPrinter.endPrinting();
    }
}