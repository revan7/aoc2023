package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15 {

    static Map<Long, LinkedList<String>> map = new HashMap<>();

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] split = line.split(",");
        return part1(split);
    }

    static long part1(String[] codesToHash) {
        long result = 0;
        for (var s : codesToHash) {
            result += hash(s);
        }
        return result;
    }

    static long hash(String s) {
        long result = 0;
        for (var c : s .toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }
        return result;
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] split = line.split(",");
        List<String[]> input = new LinkedList<>();
        for (var s : split) {
            String[] operation;
            if (s.contains("=")) {
                operation = s.split("=");
                input.add(new String[]{operation[0], "=", operation[1]});
            } else {
                input.add(new String[]{s.substring(0, s.length() - 1), "-"});
            }
        }
        return part2(input);
    }

    public static long part2(List<String[]> input) {
        for (var operation : input) {
            long hash = hash(operation[0]);
            LinkedList<String> list = map.getOrDefault(hash, new LinkedList<>());
            if (operation[1] == "=") {
                String element = operation[0] + " " + operation[2];
                int index = -1;
                for (var l : list) {
                    if (l.startsWith(operation[0] + " ")) {
                        index = list.indexOf(l);
                        break;
                    }
                }
                if (index >= 0) {
                    list.remove(index);
                    list.add(index, element);
                } else {
                    if (list.isEmpty()) list.add(element);
                    else list.addLast(element);
                }
            } else {
                int index = -1;
                for (var l : list) {
                    if (l.startsWith(operation[0] + " ")) {
                        index = list.indexOf(l);
                        break;
                    }
                }
                if (index >= 0) list.remove(index);
            }
            map.put(hash, list);
        }
        long result = 0;
        for (int i = 0; i <= 255; i++) {
            if (map.containsKey((long) i)) {
                LinkedList<String> lenses = map.get((long) i);
                if (lenses.isEmpty()) continue;
                for (int j = 0; j < lenses.size(); j ++) {
                    long focusingPower = (long) (i + 1) * ((long) (j + 1) * Integer.parseInt(lenses.get(j).split(" ")[1]));
                    result += focusingPower;
                }
            }
        }
        return result;
    }
}
