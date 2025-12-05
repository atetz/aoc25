package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.util.Objects;

public class Day4Solver {

    final static String PUZZLE_INPUT = "day4.txt";
    final static String PUZZLE_INPUT_SAMPLE = "day4.sample.txt";

    boolean checkBounds(int x, int y, int numCols, int numRows) {
        return x >= 0 && x < numCols && y >= 0 && y < numRows;
    }

    boolean checkRollAccessibility(int x, int y, String[][] rollsMap) {

        int numRows = rollsMap.length;
        int numCols = rollsMap[0].length;

        // left up, right down, right up, left down, left, right, up, down
        int[][] moves = {{-1, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int maxRolls = 3;
        int adjacentRolls = 0;

        for (int[] move : moves) {
            int nextX = x + move[0];
            int nextY = y + move[1];

            if (!checkBounds(nextX, nextY, numCols, numRows)) {
                continue;
            }

            if (Objects.equals(rollsMap[nextY][nextX], "@")) {
                adjacentRolls++;
            }

            if (adjacentRolls > maxRolls) {
                return false;
            }

        }
        return true;

    }

    public int solvePartOne(String[][] rollsMap) {
        int numRows = rollsMap.length;
        int numCols = rollsMap[0].length;
        int accessibleRolls = 0;
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (Objects.equals(rollsMap[y][x], "@") && checkRollAccessibility(x, y, rollsMap)) {
                    accessibleRolls++;
                }
            }
        }

        return accessibleRolls;
    }

    void main() {

        FileHelper fileHelper = new FileHelper();
        String[][] rollsMap = fileHelper.readLinesAs2DArray(PUZZLE_INPUT);

        System.out.printf("part one: %d", solvePartOne(rollsMap));

//        assert checkBounds(0, 0, numCols, numRows);
//        assert !checkBounds(-1, 0, numCols, numRows);
//        assert !checkBounds(12, 0, numCols, numRows);
//        assert !checkBounds(0, -1, numCols, numRows);
//        assert checkBounds(1, 0, numCols, numRows);
//        assert !checkBounds(10, 11, numCols, numRows);

    }
}

