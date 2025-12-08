package nl.tetz.adam.utils.grid;


import java.util.LinkedHashMap;

public class GridHelper {

    // LinkedHashMap preserves order of insertion.
    public static LinkedHashMap<Coord, String> convertGridToMap(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        LinkedHashMap<Coord, String> map = new LinkedHashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map.put(new Coord(j, i), grid[i][j]);
            }
        }
        return map;
    }

}
