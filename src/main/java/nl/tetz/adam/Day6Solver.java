package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6Solver {


    static void main() {

        String file = "day6.txt";

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines(file);
        System.out.printf("Part one: %d \n", partOne(lines));


        String[][] linesGrid = fileHelper.readLinesAs2DArray(file);
        System.out.printf("Part two: %d \n", partTwo(linesGrid));

    }

    private static BigInteger partOne(List<String> lines) {
        Pattern numberPattern = Pattern.compile("(\\d+)");

        // determine columns based on first line
        String firstLine = lines.getFirst();

        //operators based on last line
        String lastLine = lines.getLast();

        // put numbers in order
        List<List<Long>> pivotedList = createEmptyPivotedList(numberPattern, firstLine);
        for (String line : lines) {
            Matcher matcher = numberPattern.matcher(line);
            int column = 0;
            while (matcher.find()) {
                List<Long> columnList = pivotedList.get(column);
                columnList.add(Long.valueOf(matcher.group()));
                column++;
            }
        }
        // get operators
        String[] operators = getOperatorList(lastLine);

        return calculateTotal(pivotedList, operators);
    }

    private static BigInteger partTwo(String[][] linesGrid) {
        String[] operatorsLine = linesGrid[linesGrid.length - 1];
        List<List<Long>> numbersSections = getNumberSections(linesGrid, operatorsLine);
        String[] operatorsArray = Arrays.stream(operatorsLine).filter(Day6Solver::isOperator).toArray(String[]::new);
        return calculateTotal(numbersSections, operatorsArray);
    }


    private static boolean isOperator(String s) {
        return "*".equals(s) || "+".equals(s);
    }

    // need to pivot the data to do the math
    // populate with rows for columns
    private static List<List<Long>> createEmptyPivotedList(Pattern pattern, String line) {
        long columns = pattern.matcher(line).results().count();
        List<List<Long>> result = new ArrayList<>();

        for (int i = 0; i < columns; i++) {
            result.add(new ArrayList<>());
        }
        return result;
    }

    static String[] getOperatorList(String line) {
        Pattern operatorPattern = Pattern.compile("([+*])");
        return operatorPattern.matcher(line)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
    }

    static List<List<Long>> getNumberSections(String[][] linesGrid, String[] operators) {


        int sectionCount = Math.toIntExact(
                Arrays.stream(operators)
                        .filter(Day6Solver::isOperator)
                        .count()
        );

        int section = -1;
        int column = 0;
        List<List<Long>> sections = new ArrayList<>();
        List<Long> numberSection = new ArrayList<>();
        while (section < sectionCount + 1) {
            StringBuilder sb = new StringBuilder();
            if (!" ".equals(operators[column])) {
                if (section != -1) {
                    sections.add(numberSection);
                }
                section++;
                numberSection = new ArrayList<>();
            }
            //-1 to skip last row which has operators.
            for (int i = 0; i < linesGrid.length - 1; i++) {
                String target = linesGrid[i][column];
                if (!" ".equals(target)) {
                    sb.append(target);
                }
            }

            if (!sb.isEmpty()) {
                numberSection.add(Long.parseLong(sb.toString()));
            }

            column++;
            if (column >= linesGrid[0].length) {
                sections.add(section, numberSection);
                break;
            }
        }
        return sections;
    }

    static BigInteger calculateTotal(List<List<Long>> numbersSections, String[] operatorsArray) {
        BigInteger total = BigInteger.ZERO;
        for (int i = 0; i < operatorsArray.length; i++) {
            if ("*".equals(operatorsArray[i])) {
                // multiplication needs to start with 1 to prevent zero result.
                total = total.add(
                        numbersSections.get(i).stream()
                                .map(BigInteger::valueOf)
                                .reduce(BigInteger.ONE, BigInteger::multiply)
                );

            } else {
                total = total.add(numbersSections.get(i).stream()
                        .map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add));
            }

        }
        return total;
    }

}
