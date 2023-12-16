package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day14 {
    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<Character>> dish = new ArrayList<>();
        while (line != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (var c : line.toCharArray()) {
                row.add(c);
            }
            dish.add(row);
            line = br.readLine();
        }
        char[][] input = new char[dish.size()][dish.get(0).size()];
        for (int i = 0; i < input.length;  i++){
            for (int j = 0; j < input[0].length; j ++) {
                input[i][j] = dish.get(i).get(j);
            }
        }
        System.out.println();
        return part1q(input);
    }

    public static Long solvePart2(BufferedReader br) throws IOException, NoSuchAlgorithmException {
        String line = br.readLine();
        List<List<Character>> dish = new ArrayList<>();
        while (line != null) {
            ArrayList<Character> row = new ArrayList<>();
            for (var c : line.toCharArray()) {
                row.add(c);
            }
            dish.add(row);
            line = br.readLine();
        }
        char[][] input = new char[dish.size()][dish.get(0).size()];
        for (int i = 0; i < input.length;  i++){
            for (int j = 0; j < input[0].length; j ++) {
                input[i][j] = dish.get(i).get(j);
            }
        }
        System.out.println();
        return part2(input);
    }

    static long part1q(char[][] input) {
        int[][] rocksLeft = new int[input.length][input[0].length];
        int[][] rocksRight = new int[input.length][input[0].length];
        int[][] rocksUp = new int[input.length][input[0].length];
        int[][] rocksDown = new int[input.length][input[0].length];
        cacheRocks(rocksLeft, rocksRight, rocksDown, rocksUp, input);
        Queue<int[]> q = new LinkedList<>();
        for (int r = 0; r < input.length; r ++) {
            for (int c = 0; c < input[0].length; c ++) {
                if (input[r][c] == 'O') q.add(new int[]{r, c});
            }
        }
        while(!q.isEmpty()) {
            int[] poll = q.poll();
            fastRollUpPoint(poll[0], poll[1], input, rocksUp);
        }
        print(input);
        long result = 0l;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] == 'O') result += (input.length - row);
            }
        }
        return result;
    }



    static long part2q(char[][] input) {
        int[][] rocksLeft = new int[input.length][input[0].length];
        int[][] rocksRight = new int[input.length][input[0].length];
        int[][] rocksUp = new int[input.length][input[0].length];
        int[][] rocksDown = new int[input.length][input[0].length];
        cacheRocks(rocksLeft, rocksRight, rocksDown, rocksUp, input);
        Queue<int[]> q = new LinkedList<>();
        Queue<int[]> next = new LinkedList<>();
        for (int r = 0; r < input.length; r ++) {
            for (int c = 0; c < input[0].length; c ++) {
                if (input[r][c] == 'O') q.add(new int[]{r, c});
            }
        }
        Set<String> visited = new HashSet<>();
        visited.add(q.toString());
        for (int i = 0; i < 1000000000; i ++) {
            while (!q.isEmpty()) {
                int[] poll = q.poll();
                int[] newPoint = fastRollUpPoint(poll[0], poll[1], input, rocksUp);
                next.add(newPoint);
            }
            while (!next.isEmpty()) {
                int[] poll = next.poll();
                int[] newPoint = fastRollLeftPoint(poll[0], poll[1], input, rocksLeft);
                q.add(newPoint);
            }

            while (!q.isEmpty()) {
                int[] poll = q.poll();
                int[] newPoint = fastRollDownPoint(poll[0], poll[1], input, rocksDown);
                next.add(newPoint);
            }

            while (!next.isEmpty()) {
                int[] poll = next.poll();
                int[] newPoint = fastRollRightPoint(poll[0], poll[1], input, rocksRight);
                q.add(newPoint);
            }
            if (!visited.add(q.toString())) break;
        }
        print(input);
        long result = 0l;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] == 'O') result += (input.length - row);
            }
        }
        return result;
    }


    static Long part1(char[][] input) {
        int[][] rocksLeft = new int[input.length][input[0].length];
        int[][] rocksRight = new int[input.length][input[0].length];
        int[][] rocksUp = new int[input.length][input[0].length];
        int[][] rocksDown = new int[input.length][input[0].length];
        cacheRocks(rocksLeft, rocksRight, rocksDown, rocksUp, input);
        for (int c = 0; c < input[0].length; c ++) {
            fastRollUp(input, c, rocksUp);
        }
        print(input);
        long result = 0l;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] == 'O') result += (input.length - row);
            }
        }
        return result;
    }

    static void cacheRocks(int[][] left, int[][] right, int[][] down, int[][] up, char[][] input) {
        for (int i = 0; i < input.length; i ++) {
            for (int j = 0; j < input[0].length; j ++) {
                right[i][j] = input[0].length - 1;
                for (int k = j; k < input[0].length; k ++) {
                    if (input[i][k] == '#') {
                        right[i][j] = k - 1;
                        break;
                    }
                }
            }

            for (int j = input[0].length - 1; j >= 0; j --) {
                left[i][j] = 0;
                for (int k = j; k >= 0; k --) {
                    if (input[i][k] == '#') {
                        left[i][j] = k + 1;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < input[0].length; i ++) {
            for (int j = 0; j < input.length; j ++) {
                down[j][i] = input.length - 1;
                for (int k = j; k < input.length; k ++) {
                    if (input[k][i] == '#') {
                        down[j][i] = k - 1;
                        break;
                    }
                }
            }

            for (int j = input.length - 1; j >= 0; j --) {
                up[j][i] = 0;
                for (int k = j; k >= 0; k --) {
                    if (input[k][i] == '#') {
                        up[j][i] = k + 1;
                        break;
                    }
                }
            }
        }
    }

    static String cacheMatrix(char[][] input) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        StringBuilder b = new StringBuilder();
        for (var row : input){
            b.append(Arrays.toString(row));
        }
        instance.update(b.toString().getBytes());
        int value = 0;
        for (byte by : instance.digest()) {
            value = (value << 8) + (by & 0xFF);
        }
        return String.valueOf(value);
    }

    static void cycle(char[][] input, int[][] rocksUp, int[][] rocksLeft, int[][] rocksDown, int[][] rocksRight) {
        for (int c = 0; c < input[0].length; c ++) {
            fastRollUp(input, c, rocksUp);
        }
        for (int r = 0; r < input.length; r ++) {
            fastRollLeft(input, r, rocksLeft);
        }
        for (int c = 0; c < input[0].length; c ++) {
            fastRollDown(input, c, rocksDown);
        }

        for (int r = 0; r < input.length; r ++) {
            fastRollRight(input, r, rocksRight);
        }
    }


    static void slowCycle(char[][] input, int[][] rocksUp, int[][] rocksLeft, int[][] rocksDown, int[][] rocksRight) {
        for (int c = 0; c < input[0].length; c ++) {
            rollUp(input, c);
        }
        for (int r = 0; r < input.length; r ++) {
            rollLeft(input, r);
        }
        for (int c = 0; c < input[0].length; c ++) {
            rollDown(input, c);
        }

        for (int r = 0; r < input.length; r ++) {
            rollRight(input, r);
        }
    }

    static long getResult(char[][] input) {
        long result = 0;
        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[0].length; col++) {
                if (input[row][col] == 'O') result += (input.length - row);
            }
        }
        return result;
    }

    static Long part2(char[][] input) throws NoSuchAlgorithmException {
        int[][] rocksLeft = new int[input.length][input[0].length];
        int[][] rocksRight = new int[input.length][input[0].length];
        int[][] rocksUp = new int[input.length][input[0].length];
        int[][] rocksDown = new int[input.length][input[0].length];
        cacheRocks(rocksLeft, rocksRight, rocksDown, rocksUp, input);
        Set<String> visited = new HashSet<>();
        Map<String, Integer> stateToIt = new HashMap<>();
        System.out.println(getResult(input) + " " + cacheMatrix(input));
        for (int i = 0; i < 1000000000; i ++) {
            String key = cacheMatrix(input);
            if (!visited.add(key)) {
                for (int j = 0; j < (1000000000 - i) % (i - stateToIt.get(key)); j++) {
                    cycle(input, rocksUp, rocksLeft, rocksDown, rocksRight);
                }
                break;
            };
            stateToIt.put(key, i);
            cycle(input, rocksUp, rocksLeft, rocksDown, rocksRight);
            System.out.println(getResult(input) + " " + cacheMatrix(input) + " " + i);
        }
        return getResult(input);
    }
//WRONG: 99371 99238

    static char[][] copy(char[][] arr) {
        char[][] copy = new char[arr.length][arr[0].length];
        for (int r = 0; r < arr.length; r ++) {
            for (int c = 0; c < arr[0].length; c ++) {
                copy[r][c] = arr[r][c];
            }
        }
        return copy;
    }

    static boolean isEqual(char[][] a, char[][] b) {
        for (int r = 0; r < a.length; r ++) {
            for (int c = 0; c < a[0].length; c ++) {
                if (a[r][c] != b[r][c]) return false;
            }
        }
        return true;
    }

    static void rollUp(char[][] arr, int c) {
        for (int i = arr.length - 1; i >= 0; i --) {
            if (arr[i][c] == 'O') {
                int j = i - 1;
                while (j >= 0 && arr[j][c] != '#') {
                    if (arr[j][c] == '.') {
                        swap(i, c, j, c, arr);
                        break;
                    }
                    j--;
                }
            }
        }
    }

    static void rollDown(char[][] arr, int c) {
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i][c] == 'O') {
                int j = i + 1;
                while (j < arr.length && arr[j][c] != '#') {
                    if (arr[j][c] == '.') {
                        swap(i, c, j, c, arr);
                        break;
                    }
                    j++;
                }
            }
        }
    }

    static void rollLeft(char[][] arr, int r) {
        for (int i = arr[0].length - 1; i >= 0; i --) {
            if (arr[r][i] == 'O') {
                int j = i - 1;
                while (j >= 0 && arr[r][j] != '#') {
                    if (arr[r][j] == '.') {
                        swap(r, i, r, j, arr);
                    }
                    j--;
                }
            }
        }
    }


    static void rollRight(char[][] arr, int r) {
        for (int i = 0; i < arr[0].length; i ++) {
            if (arr[r][i] == 'O') {
                int j = i + 1;
                while (j < arr[0].length && arr[r][j] != '#') {
                    if (arr[r][j] == '.') {
                        swap(r, i, r, j, arr);
                    }
                    j++;
                }
            }
        }
    }

    static void fastRollUp(char[][] arr, int c, int[][] rocksUp) {
        for (int i = arr.length - 1; i >= 0; i --) {
            if (arr[i][c] == 'O') {
                fastRollUpPoint(i, c, arr, rocksUp);
            }
        }
    }


    static int[] fastRollUpPoint(int r, int c, char[][] arr, int[][] rocksUp) {
        int rockIndex = rocksUp[r][c];
        while (rockIndex != r && arr[rockIndex][c] != '.') rockIndex++;
        swap(r, c, rockIndex, c, arr);
        return new int[]{rockIndex, c};
    }

    static int[] fastRollLeftPoint(int r, int c, char[][] arr, int[][] rocksLeft) {
        int rockIndex = rocksLeft[r][c];
        while (rockIndex != c && arr[r][rockIndex] != '.') rockIndex++;
        swap(r, c, r, rockIndex, arr);
        return new int[]{r, rockIndex};
    }


    static int[] fastRollRightPoint(int r, int c, char[][] arr, int[][] rocksRight) {
        int rockIndex = rocksRight[r][c];
        while (rockIndex != c && arr[r][rockIndex] != '.') rockIndex--;
        swap(r, c, r, rockIndex, arr);
        return new int[]{r, rockIndex};
    }


    static int[] fastRollDownPoint(int r, int c, char[][] arr, int[][] rocksDown) {
        int rockIndex = rocksDown[r][c];
        while (rockIndex != r && arr[rockIndex][c] != '.') rockIndex--;
        swap(r, c, rockIndex, c, arr);
        return new int[]{rockIndex, c};
    }


    static void fastRollDown(char[][] arr, int c, int[][] rocksDown) {
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i][c] == 'O') {
                fastRollDownPoint(i, c, arr, rocksDown);
            }
        }
    }

    static void fastRollLeft(char[][] arr, int r, int[][] rocksLeft) {
        for (int i = arr[0].length - 1; i >= 0; i --) {
            if (arr[r][i] == 'O') {
                fastRollLeftPoint(r, i, arr, rocksLeft);
            }
        }
    }

    static void fastRollRight(char[][] arr, int r, int[][] rocksRight) {
        for (int i = 0; i < arr[0].length; i ++) {
            if (arr[r][i] == 'O') {
                fastRollRightPoint(r, i, arr, rocksRight);
            }
        }
    }


    static void print(char[][] arr) {
        for (var row : arr) {
            for (var c :row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    static void swap(int r1, int c1, int r2, int c2, char[][] arr) {
        //System.out.println("Swapping: " + r1 + " " + c1 + " " + r2 + " " + c2);
        char swap = arr[r1][c1];
        arr[r1][c1] = arr[r2][c2];
        arr[r2][c2] = swap;
    }

}
