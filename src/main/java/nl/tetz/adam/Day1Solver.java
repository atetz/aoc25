package nl.tetz.adam;


import nl.tetz.adam.utils.FileHelper;

import java.util.List;

public class Day1Solver {
    // including 0, there are 100 positions on the dial
    private final int MAX_POSITIONS = 100;
    private final int START_POSITION = 50;

    public boolean partTwo = false;
    private int dialPosition = 0;
    private int rotationsToZero = 0;
    private int rotationsOverZero = 0;

    public void setPartTwo(Boolean partTwo) {
        this.partTwo = partTwo;
    }

    private void setDialPosition(int position) {
        if (position == 0) {
            rotationsToZero++;
        }
        dialPosition = position;
    }

    private void rotateRight(int count) {

        int newPosition = (dialPosition + count) % MAX_POSITIONS;

        if (partTwo) {
            int div = count / MAX_POSITIONS;
            int mod = count % MAX_POSITIONS;

            rotationsOverZero += div;

            if (dialPosition + mod >= MAX_POSITIONS) {
                rotationsOverZero++;
            }
        }
        this.setDialPosition(newPosition);
    }

    // Java's % operator with negative numbers returns a negative remainder,
    // so I add 100 to make it positive.
    private void rotateLeft(int count) {


        int newPosition = ((dialPosition - count % MAX_POSITIONS) + MAX_POSITIONS) % MAX_POSITIONS;


        if (partTwo) {
            int div = -count / -MAX_POSITIONS;
            int mod = -count % -MAX_POSITIONS;
            rotationsOverZero += div;

            if (dialPosition != 0 && dialPosition + mod <= 0) {
                rotationsOverZero++;
            }


        }
        this.setDialPosition(newPosition);
    }

    private void rotate(List<String> lines) {
        this.dialPosition = START_POSITION;
        for (String line : lines) {
            int count = Integer.parseInt(line.replaceAll("\\D", ""));
            if (line.charAt(0) == 'L') {
                this.rotateLeft(count);
            } else {
                this.rotateRight(count);
            }
        }
    }

    public int solvePart1(List<String> lines) {
        rotate(lines);
        return this.rotationsToZero;

    }

    public int solvePart2(List<String> lines) {
        rotate(lines);
        return this.rotationsOverZero;
    }

    void main() {
        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines("day1.txt");
        int partOne = solvePart1(lines);
        IO.println(String.format("Part one: %d", partOne));
        this.partTwo = true;
        int partTwo = solvePart2(lines);
        IO.println(String.format("Part two: %d", partTwo));
    }
}
