package day1;

import com.sun.jdi.StringReference;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import utils.Problem;
import utils.ProblemType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    protected String solveProblem(final ArrayList<String> lines, ProblemType type) {
        int result = 0;
        for (final String line : lines) {
            int firstDigit = -1;
            int lastDigit = -1;
            char[] chars = line.toCharArray();
            int charidx = 0;
            for (final char c : chars) {
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
