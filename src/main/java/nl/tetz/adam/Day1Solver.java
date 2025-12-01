package nl.tetz.adam;


import nl.tetz.adam.utils.FileHelper;

import java.util.List;

public class Day1Solver {

    // including 0, there are 100 positions to be set.
    private final int MAX_POSITIONS = 100;
    private int dialPosition = 50;
    private int rotationsToZero = 0;
    

    private void setDialPosition(int position) {
        if (position == 0) {
            rotationsToZero++;
        }
        dialPosition = position;
    }

    private void rotateRight(int count) {
        int newPosition = (dialPosition + count) % MAX_POSITIONS;
        this.setDialPosition(newPosition);
    }

    // Java's % operator with negative numbers returns a negative remainder,
    // so I add 100 to make it positive.
    private void rotateLeft(int count) {
        int newPosition = ((dialPosition - count % MAX_POSITIONS) + MAX_POSITIONS) % MAX_POSITIONS;
        this.setDialPosition(newPosition);
    }

    public int solvePart1(String fileName) {
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines(fileName);
        for (String line : lines) {
            int count = Integer.parseInt(line.replaceAll("\\D", ""));
            if (line.charAt(0) == 'L') {
                this.rotateLeft(count);
            } else {
                this.rotateRight(count);
            }
        }
        return this.rotationsToZero;

    }

    public int solvePart2() {
        return 2;
    }

    void main() {
        int partOne = solvePart1("day1.txt");
        IO.println(String.format("Part one: %d", partOne));
    }
}
