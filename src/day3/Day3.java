package day3;

import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@TestResults(resultA = "4361", resultB = "467835")
public class Day3 extends Problem {
    public Day3() {
        super(3);
    }

    int cols;
    int rows;
    int wholeTextLength;
    String wholeText;
    ProblemType type;

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        this.type = type;
        wholeText = String.join("", lines);
        wholeTextLength = wholeText.length();
        cols = lines.get(0).length();
        rows = wholeTextLength / cols;
        List<ProblemNumber> numbersWithAdjacentSymbol = new ArrayList<>();
        Map<Integer, List<ProblemNumber>> gearRatios = new HashMap<>();
        boolean checkingIncompleteNumber = false;
        int checkingNumberFirstPosition = 0;
        for (int i = 0; i < wholeTextLength; i++) {
            char c = wholeText.charAt(i);
            if (Character.isDigit(c) && !checkingIncompleteNumber) {
                checkingNumberFirstPosition = i;
                checkingIncompleteNumber = true;
            } else if (!Character.isDigit(c)) {
                checkingIncompleteNumber = false;
            }
            AtomicInteger symbolPosRef = new AtomicInteger();
            if (Character.isDigit(c) && hasAdjacentSymbol(i, symbolPosRef)) {
                ProblemNumber wholeNumber = getWholeNumber(checkingNumberFirstPosition);
                if (type == ProblemType.A) {
                    if (!numbersWithAdjacentSymbol.contains(wholeNumber))
                        numbersWithAdjacentSymbol.add(wholeNumber);
                } else {
                    if (!gearRatios.containsKey(symbolPosRef.get())) {
                        var ratios = new ArrayList<ProblemNumber>();
                        ratios.add(wholeNumber);
                        gearRatios.put(symbolPosRef.get(), ratios);
                    } else {
                        if (!gearRatios.get(symbolPosRef.get()).contains(wholeNumber))
                            gearRatios.get(symbolPosRef.get()).add(wholeNumber);
                    }
                }
            }
        }

        if (type == ProblemType.A) {
            return numbersWithAdjacentSymbol.stream().map(pn -> pn.number).reduce(Integer::sum).get().toString();
        } else {
            return gearRatios.values()
                    .stream()
                    .filter(list -> list.size() == 2)
                    .map(list -> list.stream().map(pn -> pn.number).reduce((a, b) -> a * b).get())
                    .reduce(0, Integer::sum)
                    .toString();
        }
    }

    private boolean hasAdjacentSymbol(int numberPosition, AtomicInteger symbolPosRef) {
        Point2D point = toPoint2D(numberPosition);
        boolean hasAdjacentSymbol = false;
        for (int y = point.y - 1; y <= point.y + 1; y++) {
            for (int x = point.x - 1; x <= point.x + 1; x++) {
                int arrayPosition = toArrayPosition(new Point2D(x, y));
                if (arrayPosition < 0
                        || arrayPosition == numberPosition
                        || arrayPosition > wholeTextLength - 1)
                    continue;
                char checkingChar = wholeText.charAt(arrayPosition);
                if (type == ProblemType.A) {
                    if (!Character.isDigit(checkingChar) && checkingChar != '.') {
                        hasAdjacentSymbol = true;
                        break;
                    }
                } else {
                    if (checkingChar == '*') {
                        symbolPosRef.set(arrayPosition);
                        hasAdjacentSymbol = true;
                        break;
                    }
                }
            }
        }
        return hasAdjacentSymbol;
    }

    private ProblemNumber getWholeNumber(int numberPosition) {
        StringBuilder wholeNumber = new StringBuilder();
        for (int i = numberPosition; i < wholeTextLength; i++) {
            char c = wholeText.charAt(i);
            if (isDifferentRow(numberPosition, i) || !Character.isDigit(c)) break;
            wholeNumber.append(c);
        }
        return new ProblemNumber(Integer.parseInt(wholeNumber.toString()), numberPosition, wholeNumber.length());
    }

    private Point2D toPoint2D(int arrayPos) {
        int x = arrayPos % cols;
        int y = arrayPos / rows;
        return new Point2D(x, y);
    }

    private int toArrayPosition(Point2D point) {
        return point.y * cols + point.x;
    }

    private boolean isDifferentRow(int currentArrayPos, int newArrayPos) {
        return toPoint2D(currentArrayPos).y != toPoint2D(newArrayPos).y;
    }

    class ProblemNumber {
        int number;
        int arrayPosition;
        int digits;

        public ProblemNumber(int number, int arrayPosition, int digits) {
            this.number = number;
            this.arrayPosition = arrayPosition;
            this.digits = digits;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProblemNumber that = (ProblemNumber) o;
            return number == that.number && arrayPosition == that.arrayPosition && digits == that.digits;
        }

        @Override
        public int hashCode() {
            return Objects.hash(number, arrayPosition, digits);
        }

        public boolean isSame(ProblemNumber n) {
            return this.arrayPosition == n.arrayPosition;
        }
    }

    class Point2D {
        int x;
        int y;

        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
