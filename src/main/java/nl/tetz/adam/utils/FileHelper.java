package nl.tetz.adam.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class FileHelper {
    private static final Path INPUT_PATH = Path.of("src/main/resources/puzzleInput");

    public static List<String> readLines(String fileName) {
        try {
            return Files.readAllLines(INPUT_PATH.resolve(fileName));
        } catch (IOException e) {
                throw new RuntimeException("Failed to read file: " + fileName, e);
            }
        }


    public static List<int[]> readLinesAsListOfIntArray(String fileName, String delimiter) {
        return readLines(fileName).stream()
                .map(line -> Arrays.stream(line.split(delimiter))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toList();

    }

    public static String[][] readLinesAs2DArray(String fileName) {
        return readLines(fileName).stream().map(s -> s.split(""))
                .toArray(String[][]::new);
    }
}


