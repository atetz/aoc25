package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class Day5Solver {

    ArrayList<Long> ingredientStock = new ArrayList<>();
    ArrayList<long[]> ingredientRanges = new ArrayList<>();

    List<String> lines;

    Day5Solver(List<String> lines) {
        this.lines = lines;
        this.parseLines();
    }

    static void main() {

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines("day5.txt");
        Day5Solver solver = new Day5Solver(lines);
        solver.mergeRanges();
        System.out.printf("Part one: %d%n", solver.partOne());
        System.out.printf("Part two: %d%n", solver.partTwo());

    }

    void parseLines() {
        this.lines.forEach(line -> {
            if (line.contains("-")) {
                String[] range = line.split("-");
                this.ingredientRanges.add(new long[]{Long.parseLong(range[0]), Long.parseLong(range[1])});
            }
            if (!line.contains("-") && !line.isEmpty()) {
                this.ingredientStock.add(
                        Long.parseLong(line));

            }
        });
    }

    void mergeRanges() {

        ArrayList<long[]> ranges = this.ingredientRanges;
        // Sort intervals based on start values
        ranges.sort((a, b) -> Long.compare(a[0], b[0]));

        ArrayList<long[]> res = new ArrayList<>();
        // adding first one so I can compare.
        res.add(new long[]{ranges.getFirst()[0], ranges.getFirst()[1]});

        for (int i = 1; i < ranges.size(); i++) {
            long[] last = res.getLast();
            long[] curr = ranges.get(i);

            // If current interval overlaps with the last merged interval,
            // merge them
            if (curr[0] <= last[1])
                last[1] = Math.max(last[1], curr[1]);
            else
                res.add(new long[]{curr[0], curr[1]});
        }

        this.ingredientRanges = res;
    }

    long partOne() {
        long res = 0;
        for (long ingredient : this.ingredientStock) {
            for (long[] ingredientRange : this.ingredientRanges) {
                long lowerBand = ingredientRange[0];
                long upperBand = ingredientRange[1];
                if (ingredient >= lowerBand &&
                        ingredient <= upperBand) {
                    res++;
                }
            }
        }
        return res;
    }

    long partTwo() {
        long res = 0;

        for (long[] ingredientRange : this.ingredientRanges) {
            long lowerBand = ingredientRange[0];
            long upperBand = ingredientRange[1];

            res += upperBand - lowerBand;
        }
        res += ingredientRanges.size();

        return res;
    }


}

// 3 are fresh