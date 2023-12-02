package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class Problem {
    private int day;
    private boolean testing;
    private boolean hasTestB;

    public Problem(int day) {
        this.day = day;
    }
    public Problem(int day, boolean hasTestB) {
        this.day = day;
        this.hasTestB = hasTestB;
    }

    private ArrayList<String> readLines(boolean testB) {
        String filename = testing ? String.format("day%d/test.txt", this.day) : String.format("day%d/input.txt", this.day);
        if (testB && testing) filename = String.format("day%d/testb.txt", this.day);
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
        return lines;
    }

    private ArrayList<String> readLines() {
        return readLines(false);
    }

    protected abstract String solveProblem(final ArrayList<String> lines, final ProblemType type);

    public void test() {
        this.testing = true;
        if (this.getClass().isAnnotationPresent(TestResults.class)) {
            TestResults testResults = this.getClass().getAnnotation(TestResults.class);
            ArrayList<String> lines = this.readLines();
            String resultFromA = solveProblem(lines, ProblemType.A);
            Problem.assertEquals(resultFromA, testResults.resultA(), 'A');

            if (hasTestB) lines = this.readLines(true);
            String resultFromB = solveProblem(lines, ProblemType.B);
            Problem.assertEquals(resultFromB, testResults.resultB(), 'B');
        }
        ProblemPrinter.addSeparation();
        this.testing = false;
    }

    private static void assertEquals(final String result, final String expected, char part) {
        if (result == null) {
            ProblemPrinter.addRow("Test result is undefined", "");
            return;
        }
        if (result.equals(expected)) ProblemPrinter.addRow("TEST "+part+": Correct!", result);
        else ProblemPrinter.addRow("TEST "+part+": Failed! [ Result | Expected]", result + " | " + expected);
    }

    public void solve() {
        final var lines = this.readLines();
        String resultA = Utils.measureTime(() -> solveProblem(lines, ProblemType.A), ProblemType.A);
        ProblemPrinter.addRow("Result of problem A", resultA);
        String resultB = Utils.measureTime(() -> solveProblem(lines, ProblemType.B), ProblemType.B);
        ProblemPrinter.addRow("Result of problem B", resultB);

    }
}
