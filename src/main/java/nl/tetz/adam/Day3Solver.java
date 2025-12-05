package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day3Solver {


    public int[] findMaxValueAndIndex(int[] digitArray, int skipLast, int startPos) {
        int max = 0;
        int index = 0;

        for (int i = startPos; i < digitArray.length - skipLast; i++) {
            if (digitArray[i] > max) {
                max = digitArray[i];
                index = i;
            }
        }
        return new int[]{max, index};
    }

    public int findRemainingMax(int[] digitArray, int startIndex) {
        return Arrays.stream(digitArray, startIndex, digitArray.length).max().orElseThrow();
    }

    public int mergeNumbers(int leftMax, int rightMax) {
        String sb = String.valueOf(leftMax) +
                rightMax;
        return Integer.parseInt(sb);

    }

    public int[] bankToDigitArray(String bank) {
        return Arrays.stream(bank.split("")).mapToInt(Integer::parseInt).toArray();
    }

    public int solvePartOne(List<String> banks) {
        int total = 0;
        for (String bank : banks) {
            int[] digitArray = bankToDigitArray(bank);
            int[] maxValueAndIndex = findMaxValueAndIndex(digitArray, 1, 0);
            int remainingMax = findRemainingMax(digitArray, maxValueAndIndex[1] + 1);
            int maxJoltage = mergeNumbers(maxValueAndIndex[0], remainingMax);
            total += maxJoltage;
        }
        return total;
    }

    public BigInteger solvePartTwo(List<String> banks) {
        BigInteger total = BigInteger.ZERO;
        int batteriesWanted = 12;

        for (String bank : banks) {
            int[] digitArray = bankToDigitArray(bank);
            int startPos = 0;
            StringBuilder result = new StringBuilder();

            // for an increasing sliding window, get highest value until batteriesWanted

            for (int windowLimit = batteriesWanted - 1; windowLimit > -1; windowLimit--) {
                int[] maxValueAndIndex = findMaxValueAndIndex(digitArray, windowLimit, startPos);
                startPos = maxValueAndIndex[1] + 1;
                result.append(maxValueAndIndex[0]);
            }
            total = total.add(new BigInteger(result.toString()));
        }
        return total;
    }

    void main() {

        FileHelper fileHelper = new FileHelper();
        List<String> banks = fileHelper.readLines("day3.txt");

        System.out.printf("Part one: %d \n", solvePartOne(banks));
        System.out.printf("Part two: %d \n", solvePartTwo(banks));

    }
}

