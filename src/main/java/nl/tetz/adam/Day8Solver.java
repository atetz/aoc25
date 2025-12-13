package nl.tetz.adam;

import nl.tetz.adam.utils.FileHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


public class Day8Solver {

    List<Coord> coordsList = new ArrayList<>();

    List<Coord[]> coordPairsList = new ArrayList<>();

    List<Set<Coord>> circuits = new ArrayList<>();

    Day8Solver(List<String> lines) {
        setCoords(lines);
    }

    static void main() {

        FileHelper fileHelper = new FileHelper();
        List<String> lines = fileHelper.readLines("day8.txt");

        Day8Solver solver = new Day8Solver(lines);
        System.out.printf("Part one: %d\n", solver.partOne());

        Day8Solver p2solver = new Day8Solver(lines);
        System.out.printf("Part two: %s", p2solver.partTwo());

    }


    int partOne() {
        pairCoordsList();
        int targetPairs = 1000;
        //        int targetPairs = 10;
        for (int i = 0; i < targetPairs; i++) {
            this.addCircuits(coordPairsList.get(i));
        }
        return multiplyTopCircuits();
    }

    BigInteger partTwo() {
        pairCoordsList();
        Set<Coord> allCoordSet = new HashSet<>(this.coordsList);
        Coord[] lastPair = new Coord[0];

        int i = 0;
        while (true) {
            int firstCircuitSize = Optional.ofNullable(this.circuits)
                    .filter(list -> !list.isEmpty())
                    .map(list -> list.getFirst().size())
                    .orElse(0);
            if (firstCircuitSize == allCoordSet.size() && this.circuits.size() == 1) {
                break;
            }
            lastPair = coordPairsList.get(i);
//            System.out.printf("Index: %d\n", i);
//            System.out.printf("First: %s\n", lastPair[0]);
//            System.out.printf("Second: %s\n", lastPair[1]);
            this.addCircuits(coordPairsList.get(i));
//            System.out.printf("First circuit size: %d\n", firstCircuitSize);
//            System.out.printf("All coords size: %d\n", allCoordSet.size());
            i++;
        }

        Coord lastLeftCoord = lastPair[0];
        Coord lastRightCoord = lastPair[1];
        BigDecimal leftDecimal = BigDecimal.valueOf(lastLeftCoord.x);
        BigDecimal rightDecimal = BigDecimal.valueOf(lastRightCoord.x);
        return leftDecimal.multiply(rightDecimal).toBigInteger();

    }

    int multiplyTopCircuits() {
        this.circuits.sort((a, b) -> Integer.compare(b.size(), a.size()));

        int highestGroupSize = this.circuits.getFirst().size();
        int secondGroupSize = this.circuits.get(1).size();
        int thirdGroupSize = this.circuits.get(2).size();


        return highestGroupSize * secondGroupSize * thirdGroupSize;
    }


    void addCircuits(Coord[] coords) {
        Coord firstCoord = coords[0];
        Coord secondCoord = coords[1];
        Map<Integer, Coord> connectedCircuits = new HashMap<>();

        for (int i = 0; i < this.circuits.size(); i++) {
            Set<Coord> coordSet = this.circuits.get(i);
            if (coordSet.contains(firstCoord) && coordSet.contains(secondCoord)) {
                return;
            }
            if (coordSet.contains(firstCoord) && !coordSet.contains(secondCoord)) {
                coordSet.add(secondCoord);
                connectedCircuits.put(i, secondCoord);
            }
            if (coordSet.contains(secondCoord) && !coordSet.contains(firstCoord)) {
                coordSet.add(firstCoord);
                connectedCircuits.put(i, firstCoord);
            }

        }

        int connectedCircuitsSize = connectedCircuits.size();

        if (connectedCircuitsSize > 1) {
            Iterator<Integer> iterator = connectedCircuits.keySet().iterator();
            int firstKey = iterator.next();
            int secondKey = iterator.next();
            Set<Coord> firstSet = this.circuits.get(firstKey);
            Set<Coord> secondSet = this.circuits.get(secondKey);
//            System.out.printf("Merge: firtcoord: %s, secondcoord: %s\n", firstCoord, secondCoord);
            firstSet.addAll(secondSet);
            this.circuits.remove(secondSet);
        }
        if (connectedCircuitsSize == 0) {
            Set<Coord> junctionBoxes = new HashSet<>();
            junctionBoxes.add(firstCoord);
            junctionBoxes.add(secondCoord);
            this.circuits.add(junctionBoxes);
        }
    }

    // also handles the sorting by distance in ascending order
    void pairCoordsList() {
        for (int i = 0; i < this.coordsList.size(); i++) {
            for (int j = i + 1; j < this.coordsList.size(); j++) {
                Coord coord1 = this.coordsList.get(i);
                Coord coord2 = this.coordsList.get(j);
                this.coordPairsList.add(new Coord[]{coord1, coord2});
            }
        }
        this.coordPairsList.sort(Comparator.comparingDouble(a -> a[0].calcDistance(a[1])));
    }

    void setCoords(List<String> lines) {
        for (String line : lines) {
            String[] coordString = line.split(",");
            Coord coord = new Coord(Double.parseDouble(coordString[0]), Double.parseDouble(coordString[1]), Double.parseDouble(coordString[2]));
            this.coordsList.add(coord);
        }
    }

    // distance calc was int first and therefore off by 50k....
    // had a look at how the import javafx.geometry.Point3D handled the points.
    static class Coord {
        double x;
        double y;
        double z;

        Coord(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // calc 3D Euclidean distance 
        double calcDistance(Coord other) {
            double deltaY = y - other.y;
            double deltaX = x - other.x;
            double deltaZ = z - other.z;
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coord coord)) return false;
            return x == coord.x && y == coord.y && z == coord.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
