package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day10 {

    static Map<String, List<int[]>> directions = new HashMap<>();

    static Map<int[], List<Character>> coordsMap = new HashMap<>();

    static Map<Integer, Map<Integer, List<Character>>> dx = new HashMap<>();
    public static Long solvePart1(BufferedReader br) throws IOException {
        initMapPart1();
        String line = br.readLine();
        List<char[]> premap = new ArrayList<>();
        while (line != null) {
            premap.add(line.toCharArray());
            line = br.readLine();
        }
        char[][] map = new char[premap.size()][premap.get(0).length];
        for (int i = 0; i < premap.size(); i++) {
            map[i] = premap.get(i);
        }
        int sr = 0, sc = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                if (c == 'S') {
                    sr = i;
                    sc = j;
                    break;
                }
            }
        }
        dx.put(-1, Map.of(0, List.of('|', 'J', 'L')));
        dx.put(1, Map.of(0, List.of('|', '7', 'F')));
        dx.put(0, Map.of(-1, List.of('-', 'L', 'F'), 1,List.of('-', 'J', '7') ));
        return part1(map, sr, sc);
    }

    static void initMapPart1() {
        directions.put("J", List.of(new int[]{0, -1}));
        directions.put("L", List.of(new int[]{0, 1}));
        directions.put("7", List.of(new int[]{0, -1}));
        directions.put("F", List.of(new int[]{0, 1}));
        directions.put("|", List.of(new int[]{0, 1}, new int[]{ }));
    }

    static int[] getNextDirection(Character s) {
        if (s == 'J' || s == '7') return new int[]{0, -1};
        return new int[]{0, 1};
    }

    static boolean canAdd(Node n, char[][] map, boolean[][] visited) {
        return !outOfBounds(n.r, n.c, map) && !visited[n.r][n.c];
    }

    public static Long part1(char[][] map, int r, int c) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        visited[r][c] = true;
        long longestDistance = 0L;
        for (int rx = -1; rx <= 1; rx ++) {
            for (int cx = -1; cx <= 1; cx ++) {
                if (rx == cx) continue;
                if (rx + cx == 0) continue;
                int newR = r + rx;
                int newC = c + cx;
                if (!outOfBounds(newR, newC, map)) {
                    if (dx.get(rx).get(cx).contains(map[newR][newC])) {
                        queue.add(new Node(newR, newC, 1));
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            longestDistance = Math.max(longestDistance, n.step);
            Character s = map[n.r][n.c];
            System.out.println("Doing node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
            if (s == '.') continue;
            for (int rx = -1; rx <= 1; rx ++) {
                for (int cx = -1; cx <= 1; cx ++) {
                    if (rx == cx) continue;
                    if (rx + cx == 0) continue;
                    int newR = n.r + rx;
                    int newC = n.c + cx;
                    if (!outOfBounds(newR, newC, map)) {
                        if (dx.get(rx).get(cx).contains(map[newR][newC])) {
                            queue.add(new Node(newR, newC, 1));
                        }
                    }
                }
            }
        }
        return longestDistance;
    }

    static boolean outOfBounds(int r, int c, char[][] map) {
        if (r < 0 || c < 0) return true;
        return r >= map.length || c >= map[0].length;
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<Long>> history = new LinkedList<>();
        while (line != null) {
            List<Long> historyLine = Arrays.stream(line.split(" ")).map(s -> Long.parseLong(s.trim())).toList();
            history.add(historyLine);
            line = br.readLine();
        }
        return part2(history);
    }

    public static Long part2(List<List<Long>> history) {
        long result = 0L;
        return result;
    }

    static class Node {
        int r, c;
        int step;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Node(int r, int c, int step) {
            this.r = r;
            this.c = c;
            this.step = step;
        }
    }
}
