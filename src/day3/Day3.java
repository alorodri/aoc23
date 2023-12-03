package day3;

import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;

@TestResults(resultA = "4361", resultB = "undefined")
public class Day3 extends Problem {
    public Day3() {
        super(3);
    }

    int cols;
    int rows;
    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        cols = lines.get(0).length();
        rows = String.join("", lines).length() / cols;
        for (var line : lines) {

        }
    }

    private ArrayList<Integer> getAdjacents(int symbolPosition, ArrayList<String> lines) {
        Point2D point = toPoint2D(symbolPosition);
    }

    private Point2D toPoint2D(int arrayPos) {
        int x = arrayPos % cols;
        int y = arrayPos / rows;
        return new Point2D(x, y);
    }

    private int toArrayPosition(Point2D point) {
        return point.y * cols + point.x;
    }

    private boolean isFirstRow(Point2D point) {
        return point.y == 0;
    }

    private boolean isLastRow(Point2D point) {
        return point.y == rows - 1;
    }

    private boolean isFirstColumn(Point2D point) {
        return point.x == 0;
    }

    private boolean isLastColumn(Point2D point) {
        return point.y == cols - 1;
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
