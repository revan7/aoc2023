package solutions;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day12 {

    static Map<String, Long> cache = new HashMap<>();
    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        long result = 0L;
        while (line != null) {
            Map<String, List<Integer>> rows = new HashMap<>();
            String[] split = line.split(" ");
            String row = split[0].trim();
            List<Integer> list = Arrays.stream(split[1].trim().split(",")).map(s -> Integer.parseInt(s.trim())).toList();
            rows.put(row, list);
            line = br.readLine();
            result += part1(rows);
        }
        return result;
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        long result = 0L;
        int repeatCount = 1;
        while (line != null) {
            Map<String, List<Integer>> rows = new HashMap<>();
            String[] split = line.split(" ");
            String row = split[0].trim().repeat(repeatCount);
            List<Integer> list = Arrays.stream(split[1].trim().repeat(repeatCount).split(",")).map(s -> Integer.parseInt(s.trim())).toList();
            rows.put(row, list);
            line = br.readLine();
            result += part1(rows);
        }
        return result;
    }

    public static Long part1(Map<String, List<Integer>> rows) {
        long result = 0;
        int count = 0;
        for (var row : rows.entrySet()) {
            cache = new HashMap<>();
            var r = recurseWithMemo(0, row.getKey(), new LinkedList<>(row.getValue()), row.getValue());
            //System.out.println(r);
            result += r;
            count++;
        }
        //sSystem.out.println("Lines done : " + count);
        return result;
    }

    public static Long part2(Map<String, List<Integer>> rows) {
        long result = 0;
        int count = 0;
        for (var row : rows.entrySet()) {
            cache = new HashMap<>();
            System.out.println("Doing row : " + row);
            var r = recurseWithMemo(0, row.getKey(), new LinkedList<>(row.getValue()), row.getValue());
            //System.out.println(r);
            result += r;
            count++;
            System.out.println(count + " out of " + rows.entrySet().size());
        }
        //sSystem.out.println("Lines done : " + count);
        return result;
    }

    private static Long recurse(int i, String s, List<Integer> groups, List<Integer> originalGroups) {
        //System.out.println(key);
        //System.out.println("Recursing for: " + s + " " + groups.toString());
        if (i >= s.length()) {
            return areGroupsArranged(s, originalGroups) ? 1L : 0L;
        }
        char c = s.charAt(i);
        String key = s + groups;
        //if (cache.containsKey(key)) return cache.get(key);
        String firstHalf = s.substring(0, i);
        String secondHalf = i == s.length() - 1 ? "" : s.substring(i + 1);
        if (c == '?') {
            long recurseDot = recurse(i, firstHalf + "." + secondHalf, new LinkedList<>(groups), originalGroups);
            long recurseMachine = recurse(i, firstHalf + "#" + secondHalf, new LinkedList<>(groups), originalGroups);
            long result = recurseDot + recurseMachine;
            //cache.put(key, result);
            return result;
        }
        if (c == '#' && !groups.isEmpty()) {
            List<Integer> groupsCopy = new LinkedList<>(groups);
            int groupCount = groupsCopy.remove(0) - 1;
            if (groupCount == -1) return 0L;
            groupsCopy.add(0, groupCount);
            long result =  recurse(i + 1, s, groupsCopy, originalGroups);
            //cache.put(key, result);
            return result;
        }
        if (!groups.isEmpty() && groups.get(0) == 0) groups.remove(0);
        long result = recurse(i + 1, s, new LinkedList<>(groups), originalGroups);
        //cache.put(key, result);
        return result;
    }

    static String generateCachekey(int i, String s, List<Integer> groups) {
        StringBuilder builder = new StringBuilder();
        builder.append(i);
        builder.append(s.substring(i));
        builder.append(groups);
        return builder.toString();
    }

    private static Long recurseWithMemo(int i, String s, List<Integer> groups, List<Integer> originalGroups) {
        System.out.println(s + " " + groups);
        if (i >= s.length()) {
            return areGroupsArranged(s, originalGroups) ? 1L : 0L;
        }
        String key = generateCachekey(i, s, groups);
        if (cache.containsKey(key)) {
            System.out.println("Cache hit with key: " + key);
            return cache.get(key);
        }
        char c = s.charAt(i);
        long result = 0L;
        if (c == '#') {
            List<Integer> nextGroups = new ArrayList<>(groups);
            if (!nextGroups.isEmpty() && nextGroups.get(0) == 0) {
                return 0L;
            }
            if (!nextGroups.isEmpty()) {
                Integer machineCount = nextGroups.remove(0);
                nextGroups.add(0, machineCount - 1);
            }
            result = recurseWithMemo(i + 1, s, nextGroups, originalGroups);
        }
        if (c == '.') {
            if (!groups.isEmpty() && groups.get(0) == 0) {
                groups.remove(0);
            }
            result = recurseWithMemo(i + 1, s, new ArrayList<>(groups), originalGroups);
        }
        if (c == '?') {
            String firstHalf = s.substring(0, i);
            String secondHalf = i == s.length() - 1 ? "" : s.substring(i + 1);
            result = recurseWithMemo(i, firstHalf + "." + secondHalf, new ArrayList<>(groups), originalGroups) +
                    recurseWithMemo(i, firstHalf + "#" + secondHalf, new ArrayList<>(groups), originalGroups);
        }
        cache.put(key, result);
        return result;
    }

    static String substring(int i, char newChar, String s) {
        String firstHalf = s.substring(0, i);
        String secondHalf = i == s.length() - 1 ? "" : s.substring(i + 1);
        return firstHalf + newChar + secondHalf;
    }

    private static boolean areGroupsArranged(String s, List<Integer> groups) {
        //System.out.println("Checking if " + s + " are arranged with " + groups);
        List<String> split = Arrays.stream(s.split("\\.")).filter(it -> !it.isBlank()).toList();
        if (split.size() != groups.size()) return false;
        for (int i = 0; i < split.size(); i ++) {
            if (split.get(i).length() != groups.get(i)) return false;
        }
        //System.out.println("Arranged !");
        return true;
    }

}