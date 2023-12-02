package day2;

import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@TestResults(resultA = "8", resultB = "2286")
public class Day2 extends Problem {

    private final static int MAX_RED = 12;
    private final static int MAX_GREEN = 13;
    private final static int MAX_BLUE = 14;

    public Day2() {
        super(2);
    }

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        List<Integer> validGroups = new ArrayList<>();
        AtomicInteger maxRedInBag = new AtomicInteger();
        AtomicInteger maxBlueInBag = new AtomicInteger();
        AtomicInteger maxGreenInBag = new AtomicInteger();
        for (String line : lines) {
            final Info ref = new Info();
            Parser.split(":", line).forEach(s -> {
                if (s.startsWith("Game")) {
                    ref.game = Utils.convert(Parser.split(" ", s).skip(1).findFirst().get(), Integer.class);
                } else {
                    // parseando la parte derecha, a partir de los :
                    Parser.split(";", s).forEach(group -> {
                        Info.Group g = new Info.Group();

                        Parser.split(",", group).forEach(color -> {
                            String[] numAndColor = color.trim().split(" ");
                            int num = Utils.convert(numAndColor[0], Integer.class);
                            String colorName = numAndColor[1];
                            switch (colorName) {
                                case "red" -> g.reds = num;
                                case "blue" -> g.blues = num;
                                case "green" -> g.greens = num;
                            }
                        });

                        if (type == ProblemType.A && (g.reds > MAX_RED || g.greens > MAX_GREEN || g.blues > MAX_BLUE)) ref.valid = false;
                        else if (type == ProblemType.B) {
                            if (g.reds > maxRedInBag.get()) maxRedInBag.set(g.reds);
                            if (g.blues > maxBlueInBag.get()) maxBlueInBag.set(g.blues);
                            if (g.greens > maxGreenInBag.get()) maxGreenInBag.set(g.greens);
                        }
                    });

                    if (type == ProblemType.A && ref.valid) validGroups.add(ref.game);
                    else if (type == ProblemType.B) {
                        validGroups.add(maxRedInBag.get() * maxBlueInBag.get() * maxGreenInBag.get());
                        maxRedInBag.set(0);
                        maxBlueInBag.set(0);
                        maxGreenInBag.set(0);
                    }
                }
            });

        }

        return Utils.convert(validGroups.stream().reduce(Integer::sum).get(), String.class);
    }


    class Info {
        int game;
        List<Group> groups = new ArrayList<>();
        boolean valid = true;

        @Override
        public String toString() {
            return "Info{" +
                    "game=" + game +
                    ", groups=" + groups.toString() +
                    '}';
        }

        static class Group {
            int reds;
            int blues;
            int greens;
            Group() {}
            Group(int reds, int blues, int greens) {
                this.reds = reds;
                this.blues = blues;
                this.greens = greens;
            }
            @Override
            public String toString() {
                return "Group{" +
                        "reds=" + reds +
                        ", blues=" + blues +
                        ", greens=" + greens +
                        '}';
            }
        }
    }
}
