package day1;

import utils.Problem;
import utils.ProblemType;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends Problem {
    public Day1() {
        super(1, true);
    }

    final List<String> numbersToStrings = List.of(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
    );

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        int result = 0;
        for (var line : lines) {
            int firstDigit = 0;
            int lastDigit = 0;
            boolean hasFirstDigit = false;
            boolean hasLastDigit = false;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) > 47 && line.charAt(i) < 58) {
                    if (!hasFirstDigit) {
                        firstDigit = line.charAt(i) - 48;
                        hasFirstDigit = true;
                    }
                    else{
                        lastDigit = line.charAt(i) - 48;
                        hasLastDigit = true;
                    }
                } else {
                    if (type == ProblemType.B) {
                        int stringNumber = numbersToStrings.indexOf(getCharsAsString(i, 3, line).toLowerCase());
                        if (stringNumber == -1) stringNumber = numbersToStrings.indexOf(getCharsAsString(i, 4, line).toLowerCase());
                        if (stringNumber == -1) stringNumber = numbersToStrings.indexOf(getCharsAsString(i, 5, line).toLowerCase());

                        if (!hasFirstDigit && stringNumber != -1) {
                            firstDigit = stringNumber;
                            hasFirstDigit = true;
                        } else if (stringNumber != -1) {
                            lastDigit = stringNumber;
                            hasLastDigit = true;
                        }
                    }
                }
            }
            if (!hasLastDigit) lastDigit = firstDigit;
            result += firstDigit * 10 + lastDigit;
        }
        return Utils.cast(result, String.class);
    }

    private String getCharsAsString(int from, int to, String line) {
        if (to > line.length() - from) {
            return "";
        }
        return new String(line.toCharArray(), from, to);
    }
}
