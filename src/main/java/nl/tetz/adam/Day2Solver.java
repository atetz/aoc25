package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day2Solver {

    public BigInteger solvePart1(List<String> ranges) {

        BigInteger total = BigInteger.ZERO;

        for (String range : ranges) {
            BigInteger start = new BigInteger(range.split("-")[0]);
            BigInteger end = new BigInteger(range.split("-")[1]);

            for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                String productId = String.valueOf(i);
                int productIdLength = productId.length();
                if (productIdLength % 2 == 0) {
                    String firstPart = productId.substring(0, productIdLength / 2);
                    String secondPart = productId.substring((productIdLength / 2), productIdLength);
                    if (firstPart.equals(secondPart)) {
                        total = total.add(i);
                    }

                }
            }
        }
        return total;
    }

    public BigInteger solvePart2(List<String> ranges) {

        // In hindsight this is a brute-force approach to string periodicity detection


        BigInteger total = BigInteger.ZERO;

        for (String range : ranges) {
            BigInteger start = new BigInteger(range.split("-")[0]);
            BigInteger end = new BigInteger(range.split("-")[1]);

            for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                String productId = String.valueOf(i);
                int productIdLength = productId.length();

                // if pos has passed length /2 then repetition of string wil be too big for length of product id.

                for (int pos = 1; pos <= productIdLength / 2; pos++) {
                    String pattern = productId.substring(0, pos);
                    String repeated = pattern.repeat((productIdLength / pos));

                    if (repeated.startsWith(productId)) {
                        total = total.add(i);
                        break;
                    }
                }
            }
        }
        return total;
    }

    void main() {

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines("day2.txt");
        List<String> ranges = Arrays.stream(lines.getFirst().split(",")).toList();

        System.out.printf("Part one: %d \n", solvePart1(ranges));

        System.out.printf("Part two: %d", solvePart2(ranges));

    }

}

