package utils;

import java.io.BufferedReader;
import java.io.FileReader;
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
        var lines = this.readLines();
        System.out.println("[TEST A] Result: " + solveProblem(lines, ProblemType.A));
        if (hasTestB) lines = this.readLines(true);
        System.out.println("[TEST B] Result: " + solveProblem(lines, ProblemType.B));
        this.testing = false;
    }

    public void solve() {
        final var lines = this.readLines();
        String resultA = Utils.measureTime(() -> solveProblem(lines, ProblemType.A));
        System.out.println("[PROBLEM A] Result: " + resultA);
        String resultB = Utils.measureTime(() -> solveProblem(lines, ProblemType.B));
        System.out.println("[PROBLEM B] Result: " + resultB);

    }
}
