package day4;

import utils.Parser;
import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@TestResults(resultA = "13", resultB = "30")
public class Day4 extends Problem {
    public Day4() {
        super(4);
    }

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        AtomicInteger result = new AtomicInteger();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            int cardNumber = Integer.parseInt((String)
                    Parser.split(" ", line.split(":")[0].trim())
                            .filter(s -> !s.isEmpty())
                            .toArray()[1]
            );
            Parser.split(":", line).skip(1).forEach(groups -> {
                final String[] groupsSplit = groups.trim().split("\\|");
                final String wnSplit = groupsSplit[0].trim();
                final String nSplit = groupsSplit[1].trim();
                List<Integer> winningNumbers = Parser.split(" ", wnSplit).filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();
                List<Integer> numbersOwned = Parser.split(" ", nSplit).filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();
                AtomicInteger pointsToSum = new AtomicInteger(0);
                numbersOwned.forEach(n -> {
                    if (winningNumbers.contains(n)) {
                        if (type == ProblemType.A) {
                            if (pointsToSum.get() == 0) pointsToSum.addAndGet(1);
                            else pointsToSum.set(pointsToSum.get() * 2);
                        } else {
                            pointsToSum.addAndGet(1);
                        }
                    }
                });
                if (type == ProblemType.A) result.addAndGet(pointsToSum.get());
                else {
                    for (int j = 1; j <= pointsToSum.get(); j++) {
                        lines.add(lines.get(cardNumber + j - 1));
                    }
                }
            });
        }
        if (type == ProblemType.A) return String.valueOf(result.get());
        else return String.valueOf(lines.size());
    }
}
