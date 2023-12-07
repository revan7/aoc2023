package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 {

    static Map<Integer, Map<Integer, List<Integer>>> gearMap = new HashMap<>();

    static int part1(char[][] m) {
        int sum = 0;
        for (int r = 0; r < m.length; r++) {
            for (int c = 0; c < m[0].length; c++) {
                String number = "";
                boolean shouldAdd = false;
                while (c < m[0].length && Character.isDigit(m[r][c])) {
                    number += m[r][c];
                    if (!shouldAdd) shouldAdd = canSum(r, c, m);
                    c++;
                }
                if (shouldAdd) {
                    sum += Integer.parseInt(number);
                }
            }
        }
        return sum;
    }

    static int part2(char[][] m) {
        int sum = 0;
        for (int r = 0; r < m.length; r++) {
            for (int c = 0; c < m[0].length; c++) {
                String number = "";
                int[] hasGear = null;
                while (c < m[0].length && Character.isDigit(m[r][c])) {
                    number += m[r][c];
                    if (hasGear == null) hasGear = hasGear(r, c, m);
                    c++;
                }
                if (hasGear != null) {
                    int gearR = hasGear[0];
                    int gearC = hasGear[1];
                    if (!gearMap.containsKey(gearR)) {
                        gearMap.put(gearR, new HashMap<>());
                    }
                    if (!gearMap.get(gearR).containsKey(gearC)) {
                        gearMap.get(gearR).put(gearC, new ArrayList<>());
                    }
                    gearMap.get(gearR).get(gearC).add(Integer.parseInt(number));
                }
            }
        }
        for (var entry0 : gearMap.entrySet()) {
            for (var entry1 : entry0.getValue().entrySet()) {
                if (entry1.getValue().size() == 2) {
                    sum += (entry1.getValue().get(0) * entry1.getValue().get(1));
                }
            }
        }
        return sum;
    }

    public static int solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        final List<String> m = new ArrayList<>();
        while (line != null) {
            m.add(line);
            line = br.readLine();
        }
        final char[][] engine = new char[m.size()][m.get(0).length()];
        for (int i = 0; i < m.size(); i++) {
            engine[i] = m.get(i).toCharArray();
        }
        return part1(engine);
    }

    public static int solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> m = new ArrayList<>();
        while (line != null) {
            m.add(line);
            line = br.readLine();
        }
        char[][] engine = new char[m.size()][m.get(0).length()];
        for (int i = 0; i < m.size(); i++) {
            engine[i] = m.get(i).toCharArray();
        }
        return part2(engine);
    }

    static int[] hasGear(int r, int c, char[][] m) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int rx = r + i;
                int cx = c + j;
                if (rx < 0 || rx >= m.length) continue;
                if (cx < 0 || cx >= m[0].length) continue;
                char character = m[rx][cx];
                if (Character.isAlphabetic(character) || Character.isDigit(character) || character == '.') continue;
                if (character == '*') return new int[]{rx, cx};
            }
        }
        return null;
    }

    static boolean canSum(int r, int c, char[][] m) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int rx = r + i;
                int cx = c + j;
                if (rx < 0 || rx >= m.length) continue;
                if (cx < 0 || cx >= m[0].length) continue;
                char character = m[rx][cx];
                if (Character.isAlphabetic(character) || Character.isDigit(character) || character == '.') continue;
                return true;
            }
        }
        return false;
    }

}
