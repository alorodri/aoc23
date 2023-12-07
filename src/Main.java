import utils.ProblemPrinter;
import utils.Utils;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        var skip = new HashSet<Integer>();
        skip.add(5);
        Utils.measureGlobalTime(() -> {
            Utils.executeAllDays(7, skip);
        });
        ProblemPrinter.endPrinting();
    }
}