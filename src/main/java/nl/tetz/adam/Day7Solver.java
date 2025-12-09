package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;
import nl.tetz.adam.utils.grid.Coord;
import nl.tetz.adam.utils.grid.GridHelper;

import java.util.*;


public class Day7Solver {

    final String OBSTACLE = "^";
    String[][] grid;
    int rows;
    int cols;
    LinkedHashMap<Coord, String> rollMap;
    List<Coord> checkedCoords = new ArrayList<>();
    int splitBeams = 0;


    Day7Solver(String[][] grid) {
        this.grid = grid;
        this.cols = grid[0].length;
        this.rows = grid.length;
        this.rollMap = GridHelper.convertGridToMap(grid);
    }

    static void main() {

        String file = "day7.txt";

        FileHelper fileHelper = new FileHelper();
        String[][] grid = fileHelper.readLinesAsGrid(file);

        Day7Solver solver = new Day7Solver(grid);

        int split = 0;

        Coord startCoord = solver.rollMap.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), "S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();


        // run part one
        solver.passBeam(startCoord);

        System.out.printf("Part one: %d\n", solver.splitBeams);
        System.out.printf("Part two: %d", solver.uniqueTimelines(startCoord));

    }


    // found a Using Top-Down DP(Memoization) algirithm on https://www.geeksforgeeks.org/dsa/unique-paths-in-a-grid-with-obstacles/#using-topdown-dpmemoization-omn-time-and-omn-space
    // edited to comply with the logic of branching of to left down and right down once an obstacle is hit.
    long findUniqueTimelines(int i, int j, String[][] grid, long[][] memo) {
        int r = grid.length, c = grid[0].length;

        // If out of bounds, return 0
        if (i == r || j == c || j < 0) {
            return 0L;
        }

        // If cell is an obstacle, return 0
        if (grid[i][j].equals(this.OBSTACLE)) {
            System.out.println(grid[i][j]);
            return 0L;
        }

        // If reached the bottom return 1
        if (i == r - 1) {
            return 1L;
        }

        // If already computed, return the stored result
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // if next down is obstacle, split to left and right.
        if (grid[i + 1][j].equals(this.OBSTACLE)) {
            memo[i][j] = findUniqueTimelines(i + 1, j - 1, grid, memo) +
                    findUniqueTimelines(i + 1, j + 1, grid, memo);

            // otherwise go down
        } else {
            memo[i][j] = findUniqueTimelines(i + 1, j, grid, memo);
        }

        return memo[i][j];
    }

    // Function to find unique paths with obstacles
    long uniqueTimelines(Coord start) {
        int n = this.grid.length, m = this.grid[0].length;

        // Initialize memoization table with -1
        long[][] memo = new long[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1L);
        }

        return findUniqueTimelines(start.y(), start.x(), this.grid, memo);
    }

    boolean inBounds(Coord coord) {
        int x = coord.x();
        int y = coord.y();
        return x >= 0 && x < this.cols && y >= 0 && y < this.rows;
    }

    boolean isSplitter(Coord coord) {
        return this.rollMap.get(coord).equals(this.OBSTACLE);
    }

    void passBeam(Coord coord) {

        if (this.checkedCoords.contains(coord)) {
            return;
        }
        checkedCoords.add(coord);
        Coord nextCoord = new Coord(coord.x(), coord.y() + 1);

        if (!inBounds(nextCoord)) {
            return;
        }
        if (!isSplitter(nextCoord)) {
            passBeam(nextCoord);
        } else {
            if (!this.checkedCoords.contains(nextCoord)) {
                splitBeams++;
                checkedCoords.add(nextCoord);
            }
            Coord nextLeftCoord = new Coord(nextCoord.x() - 1, nextCoord.y());
            passBeam(nextLeftCoord);
            Coord nextRightCoord = new Coord(nextCoord.x() + 1, nextCoord.y());
            passBeam(nextRightCoord);
        }
    }


}
