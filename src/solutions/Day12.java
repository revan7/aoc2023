package solutions;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {

    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, List<Integer>> rows = new HashMap<>();
        while (line != null) {
            String[] split = line.split(" ");
            String row = split[0].trim();
            List<Integer> list = Arrays.stream(split[1].trim().split(",")).map(s -> Integer.parseInt(s.trim())).toList();
            rows.put(row, list);
            line = br.readLine();
        }
        return part1(rows);
    }

    private static Long part1(Map<String, List<Integer>> rows) {
        return 0L;
    }

    private static Long recurse(String s, List<Integer> groups) {
        for (Integer g : groups) {

        }
    }

    private static boolean areGroupsArranged(String s, List<Integer> groups) {
        if (s.contains("?")) return false;
        int groupIndex = 0;
        char[] charArray = s.toCharArray();
        int f = 0, l = 0;
        for (int i = 0; i < charArray.length; i ++) {

        }
    }

}