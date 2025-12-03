package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day2Solver {

    public boolean validateP1ProductId(long productId) {
        String productIdString = String.valueOf(productId);
        int productIdLength = productIdString.length();
        if (productIdLength % 2 == 0) {
            String firstPart = productIdString.substring(0, productIdLength / 2);
            String secondPart = productIdString.substring((productIdLength / 2), productIdLength);
            return firstPart.equals(secondPart);
        }
        return false;
    }

    public boolean validateP2ProductId(long productId) {
        String productIdString = String.valueOf(productId);
        int productIdLength = productIdString.length();

        // if pos has passed length /2 then repetition of string wil be too big for length of product id.

        for (int pos = 1; pos <= productIdLength / 2; pos++) {
            String pattern = productIdString.substring(0, pos);
            String repeated = pattern.repeat((productIdLength / pos));

            if (repeated.startsWith(productIdString)) {
                return true;
            }
        }
        return false;
    }

    public BigInteger solver(List<String> ranges, boolean part2) {

        BigInteger total = BigInteger.ZERO;

        for (String range : ranges) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);

            for (long i = start; i <= end; i++) {
                if (!part2) {
                    if (validateP1ProductId(i)) {
                        total = total.add(BigInteger.valueOf(i));
                    }
                } else {
                    if (validateP2ProductId(i)) {
                        total = total.add(BigInteger.valueOf(i));
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

        System.out.printf("Part one: %d \n", solver(ranges, false));
        System.out.printf("Part two: %d \n", solver(ranges, true));
        
    }

}

