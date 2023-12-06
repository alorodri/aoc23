package day6;

import utils.Parser;
import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@TestResults(resultA = "288", resultB = "71503")
public class Day6 extends Problem {
    public Day6() {
        super(6);
    }

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        var races = new ArrayList<Race>();
        var raceB = new Race();
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            int finalI = i;
            var raceCounter = new AtomicInteger(0);
            Parser.split(" ", line.split(":")[1].trim())
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .forEach(n -> {
                        if (finalI == 0) {
                            // time
                            if (type == ProblemType.A) {
                                var race = new Race();
                                races.add(race);
                                race.time = n;
                            } else {
                                raceB.time = Long.parseLong(String.valueOf(raceB.time) + String.valueOf(n));
                            }
                        } else {
                            // distance
                            if (type == ProblemType.A) {
                                var race = races.get(raceCounter.get());
                                race.distance = n;
                                raceCounter.addAndGet(1);
                            } else {
                                raceB.distance = Long.parseLong(String.valueOf(raceB.distance) + String.valueOf(n));
                            }
                        }
                    });
        }
        races.add(raceB);
        var results = new ArrayList<Integer>();
        for (var race : races) {
            var singleResult = 0;
            for (long i = race.time / 2; i * (race.time - i) > race.distance; i--) {
                if (i * (race.time - i) > race.distance) {
                    singleResult+=2;
                }
            }
            if (singleResult > 0) results.add(race.time % 2 == 0 ? singleResult - 1 : singleResult);
        }

        return results.stream().reduce((a, b) -> a * b).get().toString();
    }

    class Race {
        long time;
        long distance;
    }
}
