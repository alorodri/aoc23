package day2;

import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@TestResults(resultA = "8", resultB = "undefined")
public class Day2 extends Problem {

    private final static int MAX_RED = 12;
    private final static int MAX_GREEN = 13;
    private final static int MAX_BLUE = 14;

    public Day2() {
        super(2);
    }

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        for (String line : lines) {

            final Info ref = new Info();
            Parser.sep(":", line).forEach(s -> {
                if (s.startsWith("Game")) {
                    ref.game = Utils.cast(Parser.sep(" ", s).skip(1).findFirst().get(), Integer.class);
                } else {
                    // parseando la parte derecha, a partir de los :
                    Parser.sep(";", s).forEach(group -> {
                        Info.Group g = new Info.Group();

                        Parser.sep(",", group).forEach(color -> {
                            String[] numAndColor = color.trim().split(" ");
                            int num = Utils.cast(numAndColor[0], Integer.class);
                            String colorName = numAndColor[1];
                            switch (colorName) {
                                case "red" -> g.reds = num;
                                case "blue" -> g.blues = num;
                                case "green" -> g.greens = num;
                            }
                        });

                        ref.groups.add(g);
                    });
                }
            });

            Logger.getInstance().log("Parsed " + ref);
        }
        return null;
    }


    class Info {
        int game;
        List<Group> groups = new ArrayList<>();

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
