package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.util.HashMap;
import java.util.Objects;

public class Day4Solver {

    HashMap<Coord, String> rollMap = new HashMap<>();
    int rows;
    int cols;

    Day4Solver(String[][] mapArray) {
        this.cols = mapArray[0].length;
        this.rows = mapArray.length;
        this.rollMap = convertGridToMap(mapArray);
    }

    static void main(String[] args) {

        FileHelper fileHelper = new FileHelper();
        String[][] rollsMap = fileHelper.readLinesAs2DArray("day4.txt");

        Day4Solver solver = new Day4Solver(rollsMap);

        System.out.printf("part one: %d \n", solver.solvePartOne());
        System.out.printf("part two: %d \n", solver.solvePartTwo());

    }

    HashMap<Coord, String> convertGridToMap(String[][] grid) {
        HashMap<Coord, String> map = new HashMap<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                map.put(new Coord(i, j), grid[i][j]);
            }
        }
        return map;
    }

    boolean checkBounds(int x, int y) {
        return x >= 0 && x < this.cols && y >= 0 && y < this.rows;
    }

    boolean isRoll(Coord coord) {
        return Objects.equals(this.rollMap.get(coord), "@");
    }

    boolean checkRollAccessibility(Coord coord) {

        // left up, right down, right up, left down, left, right, up, down
        int[][] moves = {{-1, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int maxRolls = 3;
        int adjacentRolls = 0;

        for (int[] move : moves) {

            int nextX = coord.x + move[0];
            int nextY = coord.y + move[1];
            Coord newCoord = new Coord(nextX, nextY);

            if (!checkBounds(nextX, nextY)) {
                continue;
            }

            if (this.isRoll(newCoord)) {
                adjacentRolls++;
            }

            if (adjacentRolls > maxRolls) {
                return false;
            }

        }
        return true;

    }

    public int solvePartOne() {
        int accessibleRolls = 0;
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.cols; x++) {
                Coord coord = new Coord(x, y);
                if (isRoll(coord) && checkRollAccessibility(coord)) {
                    accessibleRolls++;
                }
            }
        }

        return accessibleRolls;
    }

    public int solvePartTwo() {
        int removedRolls = 0;
        boolean removing = true;
        while (removing) {
            int removedRollsThisRound = 0;
            for (int y = 0; y < this.rows; y++) {
                for (int x = 0; x < this.cols; x++) {
                    Coord coord = new Coord(x, y);
                    if (isRoll(coord) && checkRollAccessibility(coord)) {
                        removedRolls++;
                        removedRollsThisRound++;
                        this.rollMap.put(coord, ".");
                    }
                }
            }
            if (removedRollsThisRound == 0) {
                removing = false;
            }
        }
        return removedRolls;
    }

    public record Coord(int x, int y) {

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Coord(int x1, int y1))) return false;
            return x == x1 && y == y1;
        }

    }
}

