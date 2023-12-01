package day1;

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

@State(Scope.Benchmark)
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
        for (String line : lines) {
            int firstDigit = 0;
            int lastDigit = 0;
            boolean hasFirstDigit = false;
            boolean hasLastDigit = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c > 47 && c < 58) {
                    int digit = c - 48;
                    if (!hasFirstDigit) {
                        firstDigit = digit;
                        hasFirstDigit = true;
                    }
                    else{
                        lastDigit = digit;
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
        return Integer.toString(result);
    }

    @Benchmark
    public void benchmarkSolveA() {
        final String filename = "day1/input.txt";
        String currentLine;
        final ArrayList<String> lines = new ArrayList<>();
        var inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((currentLine = br.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        solveProblem(lines, ProblemType.A);
    }

    private String getCharsAsString(int from, int to, String line) {
        if (to > line.length() - from) {
            return "";
        }
        return line.substring(from, from + to);
    }
}
