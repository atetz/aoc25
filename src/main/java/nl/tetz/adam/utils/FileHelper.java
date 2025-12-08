package nl.tetz.adam.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class FileHelper {

    private final Path inputPath;

    public FileHelper() {
        this.inputPath = Path.of("src/main/resources/puzzleInput");
    }

    public FileHelper(String inputPath) {
        this.inputPath = Path.of(inputPath);
    }

    public List<String> readLines(String fileName) {
        try {
            return Files.readAllLines(this.inputPath.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }


    public List<int[]> readLinesAsListOfIntArray(String fileName, String delimiter) {
        return readLines(fileName).stream()
                .map(line -> Arrays.stream(line.split(delimiter))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toList();

    }

    public String[][] readLinesAsGrid(String fileName) {
        return readLines(fileName).stream().map(s -> s.split(""))
                .toArray(String[][]::new);
    }
}


