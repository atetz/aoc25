package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class Day1SolverTest {

    String testFilePath = "src/test/resources/puzzleInput/";
    Day1Solver day1Solver = new Day1Solver();


    @Test
    void solvePart1Test() {
        FileHelper fileHelper = new FileHelper(testFilePath);
        List<String> lines = fileHelper.readLines("day1.txt");
        int partOne = day1Solver.solvePart1(lines);
        IO.println(String.format("Part one: %d", partOne));
    }

    @Test
    void solvePart2Test() {
        FileHelper fileHelper = new FileHelper(testFilePath);
        List<String> lines = fileHelper.readLines("day1.txt");
        day1Solver.setPartTwo(true);
        int partOne = day1Solver.solvePart2(lines);
        IO.println(String.format("Part two: %d", partOne));
    }
}