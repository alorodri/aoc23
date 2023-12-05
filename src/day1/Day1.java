package day1;

import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;
import java.util.List;

@TestResults(resultA = "142", resultB = "281")
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
    protected String solveProblem(final ArrayList<String> lines, ProblemType type) {
        int result = 0;
        for (final String line : lines) {
            int firstDigit = -1;
            int lastDigit = -1;
            int charidx = 0;

            int len = line.length();

            for (int i = 0; i < len; ++i) {
                final char c = line.charAt(i);
                if (c >= '0' && c <= '9') {
                    int digit = c - '0';
                    if (firstDigit == -1) {
                        firstDigit = digit;
                    }
                    lastDigit = digit;
                } else {
                    if (type == ProblemType.B) {
                        int stringNumber = numbersToStrings.indexOf(getCharsAsString(charidx, 3, line).toLowerCase());
                        if (stringNumber == -1)
                            stringNumber = numbersToStrings.indexOf(getCharsAsString(charidx, 4, line).toLowerCase());
                        if (stringNumber == -1)
                            stringNumber = numbersToStrings.indexOf(getCharsAsString(charidx, 5, line).toLowerCase());

                        if (firstDigit == -1 && stringNumber != -1)
                            firstDigit = stringNumber;

                        if (stringNumber != -1) lastDigit = stringNumber;
                    }
                }
                ++charidx;
            }
            result += firstDigit * 10 + lastDigit;
        }
        return Integer.toString(result);
    }

    private String getCharsAsString(int from, int to, String line) {
        if (to > line.length() - from) {
            return "";
        }
        return line.substring(from, from + to);
    }
}
