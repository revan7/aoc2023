package solutions;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Day13 {

    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<List<Character>>> input = new ArrayList<>();
        List<List<Character>> map = new ArrayList<>();
        while (line != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (var c : line.toCharArray()) {
                row.add(c);
            }
            map.add(row);
            line = br.readLine();
            if (line == null || line.isBlank()) {
                input.add(map);
                map = new ArrayList<>();
                line = br.readLine();
            }
        }
        return part1(input);
    }


    public static Long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<List<Character>>> input = new ArrayList<>();
        List<List<Character>> map = new ArrayList<>();
        while (line != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (var c : line.toCharArray()) {
                row.add(c);
            }
            map.add(row);
            line = br.readLine();
            if (line == null || line.isBlank()) {
                input.add(map);
                map = new ArrayList<>();
                line = br.readLine();
            }
        }

        return part2(input);
    }

    public static long part1(List<List<List<Character>>> input) {
        long result = 0;
        for (List<List<Character>> map : input) {
            var res = summariseNotes(map);
            System.out.println(res);
            result += res;
        }
        return result;
    }


    public static long part2(List<List<List<Character>>> input) {
        long result = 0;
        long count = 0;
        for (List<List<Character>> map : input) {
            var res = summariseNotesWithSmudge(map);
            System.out.println("Result for " + (count++) + " is " + res);
            result += res;
        }
        return result;
    }

    public static long horizontalResult(long[] rowEncodings) {
        System.out.println(Arrays.toString(rowEncodings));
        long horizontalResult = 0;
        for (int i = 1; i < rowEncodings.length - 2; i ++) {
            if (rowEncodings[i] == rowEncodings[i + 1]) {
                if (isReflecting(i, i + 1, rowEncodings)) {
                    horizontalResult = (i + 1) * 100L;
                    System.out.println("Reflecting horizontally : " + " " + i + " " + (i + 1) + " " + " " + rowEncodings[i] + " " + rowEncodings[i + 1]);
                }
            }
        }
        if (horizontalResult == 0) {
            if (isReflecting(0, 1, rowEncodings)) {
                horizontalResult = 100L;
            } else if (isReflecting(rowEncodings.length - 2, rowEncodings.length - 1, rowEncodings)) {
                horizontalResult = (rowEncodings.length - 1) * 100L;
            }
        }
        return horizontalResult;
    }

    public static long verticalResult(long[] columnEncodings) {
        System.out.println(Arrays.toString(columnEncodings));
        long verticalResult = 0;
        for (int i = 1; i < columnEncodings.length - 2; i ++) {
            if (columnEncodings[i] == columnEncodings[i + 1]) {
                if (isReflecting(i, i + 1, columnEncodings)) {
                    verticalResult = i + 1;
                    System.out.println("Reflecting vertically : " + " " + i + " " + (i + 1) + " " + " " + columnEncodings[i] + " " + columnEncodings[i + 1]);
                }
            }
        }
        if (verticalResult == 0) {
            if (isReflecting(0, 1, columnEncodings)) {
                verticalResult = 1;
            }
            if (isReflecting(columnEncodings.length - 2, columnEncodings.length - 1, columnEncodings)) {
                verticalResult = (columnEncodings.length - 1);
            }
        }
        return verticalResult;
    }

    public static void getHeaderEncodings(List<List<Character>> map, long[] rowEncodings, long[] columnEncodings) {
        for (int r = 0; r < map.size(); r ++) {
            for (int c = 0; c < map.get(0).size(); c ++) {
                Character terrainType = map.get(r).get(c);
                long factor = 0;
                if (terrainType == '#') {
                    factor = 1;
                }
                rowEncodings[r] += (long) Math.pow(2, c) * factor;
                columnEncodings[c] += (long) Math.pow(2, r) * factor;
            }
        }
    }

    public static List<Integer> getRefractionIndices(long[] arr) {
        List<Integer> toReturn = new LinkedList<>();
        for (int i = 0; i < arr.length - 1; i ++) {
            if (arr[i] == arr[i + 1]) {
                if (isReflecting(i, i + 1, arr)) {
                    toReturn.add(i);
                    toReturn.add(i + 1);
                }
            }
        }
        return toReturn;
    }

    public static long summariseNotes(List<List<Character>> map) {
        //printMap(map);
        long result = 0;
        long[] rowEncodings = new long[map.size()];
        long[] columnEncodings = new long[map.get(0).size()];
        getHeaderEncodings(map, rowEncodings, columnEncodings);
        result += horizontalResult(rowEncodings);
        System.out.println(Arrays.toString(columnEncodings));
        result += verticalResult(columnEncodings);
        System.out.println(result);
        System.out.println();
        return result;
    }

    public static long summariseNotesWithSmudge(List<List<Character>> map) {
        System.out.println("MAP");
        printMap(map);
        long result = 0;
        long[] rowEncodings = new long[map.size()];
        long[] columnEncodings = new long[map.get(0).size()];
        getHeaderEncodings(map, rowEncodings, columnEncodings);
        List<Integer> oldHorizontalRefractionIndices = getRefractionIndices(rowEncodings);
        List<Integer> oldVerticalRefractionIndices = getRefractionIndices(columnEncodings);
        System.out.println("Horizontal Refraction Indices: " + oldHorizontalRefractionIndices);
        System.out.println("Vertical Refraction Indices: " + oldVerticalRefractionIndices);
        boolean foundNewConfig = false;
        for (int r = 0; r < map.size() && !foundNewConfig; r ++) {
            for (int c = 0; c < map.get(0).size(); c++) {
                List<List<Character>> newMap = new ArrayList<>();
                for (var row : map) {
                    newMap.add(new ArrayList<>(row));
                }
                Character newCharacter = newMap.get(r).get(c) == '.' ? '#' : '.';
                newMap.get(r).set(c, newCharacter);
                System.out.println("Changing in row " + r + " " + c);
                System.out.println("NEW MAP");
                printMap(newMap);
                long[] newRowEncodings = new long[map.size()];
                long[] newColumnEncodings = new long[map.get(0).size()];
                getHeaderEncodings(newMap, newRowEncodings, newColumnEncodings);
                List<Integer> newHorizontalResult = getRefractionIndices(newRowEncodings);
                List<Integer> newVerticalResult = getRefractionIndices(newColumnEncodings);
                System.out.println(Arrays.toString(newRowEncodings));
                System.out.println(Arrays.toString(newColumnEncodings));
                System.out.println("New Horizontal Refraction Indices: " + newHorizontalResult);
                System.out.println("New Vertical Refraction Indices: " + newVerticalResult);
                for (int i = 0; i < newHorizontalResult.size(); i += 2) {
                    int ri = newHorizontalResult.get(i);
                    int rj = newHorizontalResult.get(i + 1);
                    System.out.println("Checking if " + ri + " " + rj + " " + " are present in: " + oldHorizontalRefractionIndices);
                    if (!indicesIn(ri, rj, oldHorizontalRefractionIndices)) {
                        System.out.println(ri + " " + rj + " not found in : " + oldHorizontalRefractionIndices);
                        return 100L * rj;
                    }
                }
                for (int i = 0; i < newVerticalResult.size(); i += 2) {
                    int ri = newVerticalResult.get(i);
                    int rj = newVerticalResult.get(i + 1);
                    System.out.println("Checking if " + ri + " " + rj + " " + " are present in: " + oldVerticalRefractionIndices);
                    if (!indicesIn(ri, rj, oldVerticalRefractionIndices)) {
                        return rj;
                    }
                }
            }
        }
        return result;
    }

    public static boolean indicesIn(int i, int j, List<Integer> target) {
        for (int k = 0; k < target.size(); k += 2) {
            if (target.get(k) == i && target.get(k + 1) == j) return true;
        }
        return false;
    }

    public static long summariseBf(List<List<Character>> map) {
        //System.out.println("MAP");
        //printMap(map);
        long result = summariseNotes(map);
        boolean foundNewConfig = false;
        for (int r = 0; r < map.size() && !foundNewConfig; r ++) {
            for (int c = 0; c < map.get(0).size(); c++) {
                List<List<Character>> newMap = new ArrayList<>();
                for (var row : map) {
                    newMap.add(new ArrayList<>(row));
                }
                Character newCharacter = newMap.get(r).get(c) == '.' ? '#' : '.';
                newMap.get(r).set(c, newCharacter);
                /*System.out.println("NEW MAP");
                System.out.println("Changing in row " + r + " " + c);*/
                //printMap(newMap);
                long newResult = summariseNotes(newMap);
                if (newResult != 0 && newResult != result) return newResult;
            }
        }
        return result;
    }

    static boolean isReflecting(int l, int h, long[] arr) {
        int count = 0;
        while (l >= 0 && h < arr.length && arr[l] == arr[h]) {
            l--;h++;
            count++;
        }
        return l < 0 || h >= arr.length;
    }

    static void printMap(List<List<Character>> map) {
        System.out.println();
        for (var row : map) {
            for (var c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }


}
