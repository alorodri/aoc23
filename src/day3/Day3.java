package day3;

import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TestResults(resultA = "4361", resultB = "undefined")
public class Day3 extends Problem {
    public Day3() {
        super(3);
    }

    int cols;
    int rows;
    int wholeTextLength;
    String wholeText;

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        wholeText = String.join("", lines);
        wholeTextLength = wholeText.length();
        cols = lines.get(0).length();
        rows = wholeTextLength / cols;
        List<ProblemNumber> numbersWithAdjacentSymbol = new ArrayList<>();
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
            if (Character.isDigit(c) && hasAdjacentSymbol(i)) {
                ProblemNumber wholeNumber = getWholeNumber(checkingNumberFirstPosition);
                if (!numbersWithAdjacentSymbol.contains(wholeNumber))
                    numbersWithAdjacentSymbol.add(wholeNumber);
            }
        }

        return numbersWithAdjacentSymbol.stream().map(pn -> pn.number).reduce(Integer::sum).get().toString();
    }

    private boolean hasAdjacentSymbol(int numberPosition) {
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
                if (!Character.isDigit(checkingChar) && checkingChar != '.') {
                    hasAdjacentSymbol = true;
                    break;
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

    private boolean isFirstRow(int arrayPos) {
        return toPoint2D(arrayPos).y == 0;
    }

    private boolean isLastRow(int arrayPos) {
        return toPoint2D(arrayPos).y == rows - 1;
    }

    private boolean isFirstColumn(int arrayPos) {
        return toPoint2D(arrayPos).x == 0;
    }

    private boolean isLastColumn(int arrayPos) {
        return toPoint2D(arrayPos).y == cols - 1;
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

        public boolean isSame(ProblemNumber n) { return this.arrayPosition == n.arrayPosition; }
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
