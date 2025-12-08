package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;
import nl.tetz.adam.utils.grid.Coord;
import nl.tetz.adam.utils.grid.GridHelper;

import java.util.*;


public class Day7Solver {

    LinkedHashMap<Coord, String> rollMap;
    int rows;
    int cols;
    List<Coord> checkedCoords = new ArrayList<>();
    int splitBeams = 0;

    Day7Solver(String[][] grid) {
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

        Coord start = solver.rollMap.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), "S"))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();


        solver.passBeam(start);
        System.out.println(solver.splitBeams);
    }

    boolean inBounds(Coord coord) {
        int x = coord.x();
        int y = coord.y();
        return x >= 0 && x < this.cols && y >= 0 && y < this.rows;
    }

    boolean isSplitter(Coord coord) {
        return this.rollMap.get(coord).equals("^");
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
