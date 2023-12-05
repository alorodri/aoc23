package day5;

import org.w3c.dom.ranges.Range;
import utils.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestResults(resultA = "35", resultB = "46")
public class Day5 extends Problem {
    public Day5() {
        super(5);
    }

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        List<Seed> seeds = new ArrayList<>();
        List<RangedSeed> rangedSeeds = new ArrayList<>();
        final ConversionGuide cg = new ConversionGuide();
        Guide actualGuide = null;

        for (var line : lines) {
            if (line.startsWith("seeds")) {
                String seedsString = line.substring(line.indexOf(":") + 1).trim();
                if (type == ProblemType.A) {
                    seeds.addAll(Parser.split(" ", seedsString)
                            .map(Long::parseLong)
                            .map(Seed::new)
                            .toList());
                } else {
                    var splittedSeeds = Parser.split(" ", seedsString).map(Long::parseLong).toList();
                    RangedSeed rangedSeed = null;
                    for (int i = 0; i < splittedSeeds.size(); i++) {
                        if (i % 2 == 0) {
                            rangedSeed = new RangedSeed(splittedSeeds.get(i));
                        } else {
                            rangedSeed.end = rangedSeed.start + splittedSeeds.get(i) - 1;
                            rangedSeeds.add(rangedSeed);
                        }
                    }
                }
            } else if (line.isEmpty()) continue;
            else if (line.matches("[\\a-z- ]+: ?")) {
                String[] sourceDestination = line.substring(0, line.indexOf(" ")).split("-");
                Guide parsingGuide = new Guide(sourceDestination[0], sourceDestination[2]);
                cg.guides.add(parsingGuide);
                actualGuide = parsingGuide;
            } else {
                List<Long> values = Parser.split(" ", line).map(Long::parseLong).toList();
                Long source = values.get(1);
                Long destination = values.get(0);
                Long range = values.get(2);
                var value = new SourceToDestinationValue(source, destination, range);
                actualGuide.values.add(value);
            }
        }

        long lowerValue = -1;

        if (type == ProblemType.A) {
            for (var seed : seeds) {
                lowerValue = retrieveLowerValue(seed.id, cg, lowerValue);
            }
        } else {
            for (int i = 0; i < rangedSeeds.size(); i++) {
                var rs1 = rangedSeeds.get(i);
                for (int j = 0; j < rangedSeeds.size(); j++) {
                    if (i == j) continue;
                    var rs2 = rangedSeeds.get(j);
                    // eliminamos solapes
                    if (rs2.start < rs1.end && rs1.end < rs2.end) {
                        rs2.start = rs1.end + 1;
                    }
                }
            }
            for (var rangedSeed : rangedSeeds) {
                for (long i = rangedSeed.start; i <= rangedSeed.end; i++) {
                    lowerValue = retrieveLowerValue(i, cg, lowerValue);
                }
            }
        }

        return String.valueOf(lowerValue);
    }

    private static long retrieveLowerValue(Long seedId, ConversionGuide cg, long lowerValue) {
        long workingSeed;
        workingSeed = seedId;
        for (var guide : cg.guides) {
            Long jumpsToMake = 0L;
            for (var value : guide.values) {
                if (workingSeed >= value.source && workingSeed < value.source + value.range) {
                    jumpsToMake = value.source - value.destination;
                }
            }
            workingSeed -= jumpsToMake;
        }
        if (lowerValue == -1 || lowerValue >= workingSeed) lowerValue = workingSeed;
        return lowerValue;
    }

    record Seed(long id) {
    }

    class RangedSeed {
        Long start;
        Long end;
        public RangedSeed(Long start) {
            this.start = start;
        }
    }

    class ConversionGuide {
        List<Guide> guides = new ArrayList<>();

        @Override
        public String toString() {
            return "ConversionGuide{" +
                    "guides=" + guides +
                    '}';
        }
    }

    class Guide {
        String source;
        String destination;
        List<SourceToDestinationValue> values = new ArrayList<>();

        public Guide(String source, String destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "Guide{" +
                    "source='" + source + '\'' +
                    ", destination='" + destination + '\'' +
                    ", values=" + values +
                    '}';
        }
    }

    class SourceToDestinationValue {
        Long source;
        Long destination;
        Long range;

        public SourceToDestinationValue(Long source, Long destination, Long range) {
            this.source = source;
            this.destination = destination;
            this.range = range;
        }

        @Override
        public String toString() {
            return "SourceToDestinationValue{" +
                    "source=" + source +
                    ", destination=" + destination +
                    ", range=" + range +
                    '}';
        }
    }
}
