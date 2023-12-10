package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Day10 {

    static Map<Character, List<int[]>> directions = new HashMap<>();

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
        dx.put(-1, Map.of(0, List.of('|', '7', 'F')));
        dx.put(1, Map.of(0, List.of('|', 'L', 'J')));
        dx.put(0, Map.of(-1, List.of('-', 'L', 'F'), 1, List.of('-', 'J', '7')));
        return part1(map, sr, sc);
    }

    static void initMapPart1() {
        directions.put('J', List.of(new int[]{0, -1}, new int[]{-1, 0}));
        directions.put('L', List.of(new int[]{0, 1}, new int[]{-1, 0}));
        directions.put('7', List.of(new int[]{0, -1}, new int[]{1, 0}));
        directions.put('F', List.of(new int[]{0, 1}, new int[]{1, 0}));
        directions.put('|', List.of(new int[]{1, 0}, new int[]{-1, 0}));
        directions.put('-', List.of(new int[]{0, 1}, new int[]{0, -1}));
        directions.put('S', List.of());
    }

    static int[] getNextDirection(Character s) {
        if (s == 'J' || s == '7') return new int[]{0, -1};
        return new int[]{0, 1};
    }

    static boolean canAdd(Node n, char[][] map, int[][] visited) {
        return !outOfBounds(n.r, n.c, map) && visited[n.r][n.c] == 0;
    }

    public static Long part1dfs(char[][] map, int r, int c) {
        Queue<Node> queue = new LinkedList<>();
        Node start = new Node(r, c, 0);
        for (int rx = -1; rx <= 1; rx++) {
            for (int cx = -1; cx <= 1; cx++) {
                if (rx == cx) continue;
                if (rx + cx == 0) continue;
                int newR = r + rx;
                int newC = c + cx;
                if (!outOfBounds(newR, newC, map)) {
                    if (directions.containsKey(map[newR][newC])) {
                        queue.add(new Node(newR, newC, 1));
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            System.out.println("DFS node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
            Long result = dfs(start, n, map, new int[map.length][map[0].length]);
            if (result > 0) return result / 2;
        }
        return -1L;
    }

    public static Long dfs(Node prev, Node n, char[][] map, int[][] visiting) {
        System.out.println("Doing node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
        System.out.println("Prev node: " + prev.r + " " + prev.c + " (" + map[prev.r][prev.c] + ") Step: " + prev.step);
        if (map[n.r][n.c] == 'S') {
            System.out.println("Loop found !");
            return n.step;
        }
        visiting[n.r][n.c] = 1;
        for (int[] dx : directions.get(map[n.r][n.c])) {
            int newR = dx[0] + n.r;
            int newC = dx[1] + n.c;
            if (newR == prev.r && newC == prev.c) continue;
            Node next = new Node(newR, newC, n.step + 1);
            if (canAdd(next, map, visiting) && directions.containsKey(map[next.r][next.c])) {
                Long result = dfs(n, next, map, visiting);
                if (result > 0) {
                    return result;
                }
            }
        }
        visiting[n.r][n.c] = 2;
        return -1L;
    }

    static boolean canGo(int rx, int cx, int r, int c, char[][] map) {
        List<Character> characters = dx.get(rx).get(cx);
        return characters.contains(map[r + rx][c + cx]);
    }

    public static Long part1(char[][] map, int r, int c) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        Node start = new Node(r, c, 0);
        visited[r][c] = true;
        long longestDistance = 0;
        for (int rx = -1; rx <= 1; rx++) {
            for (int cx = -1; cx <= 1; cx++) {
                if (rx == cx) continue;
                if (rx + cx == 0) continue;
                int newR = r + rx;
                int newC = c + cx;
                if (!outOfBounds(newR, newC, map) && canGo(rx, cx, r, c, map)) {
                    queue.add(new Node(newR, newC, 1));
                }
            }
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            //System.out.println("Doing node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
            longestDistance = Math.max(longestDistance, n.step);
            visited[n.r][n.c] = true;
            for (int[] dx : directions.get(map[n.r][n.c])) {
                int newR = dx[0] + n.r;
                int newC = dx[1] + n.c;
                Node next = new Node(newR, newC, n.step + 1);
                if (!outOfBounds(newR, newC, map) && !visited[newR][newC]) {
                    queue.add(next);
                }
            }
        }
        return longestDistance;
    }


    //TODO from visited to visited fill, skip next visited, repeat
    public static Long part2(char[][] map, int r, int c) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        visited[r][c] = true;
        long longestDistance = 0;
        for (int rx = -1; rx <= 1; rx++) {
            for (int cx = -1; cx <= 1; cx++) {
                if (rx == cx) continue;
                if (rx + cx == 0) continue;
                int newR = r + rx;
                int newC = c + cx;
                if (!outOfBounds(newR, newC, map) && canGo(rx, cx, r, c, map)) {
                    queue.add(new Node(newR, newC, 1));
                }
            }
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            //System.out.println("Doing node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
            longestDistance = Math.max(longestDistance, n.step);
            visited[n.r][n.c] = true;
            for (int[] dx : directions.get(map[n.r][n.c])) {
                int newR = dx[0] + n.r;
                int newC = dx[1] + n.c;
                Node next = new Node(newR, newC, n.step + 1);
                if (!outOfBounds(newR, newC, map) && !visited[newR][newC]) {
                    queue.add(next);
                }
            }
        }
        System.out.println("BOOLEAN MAP: ");
        for (int i = 0; i < map.length; i ++) {
            for (int j = 0; j < map[0].length; j ++) {
                System.out.print(visited[i][j] ? "X" : " ");
            }
            System.out.println();
        }
        for (int i = 0; i < map.length; i ++) {
            for (int j = 0; j < map[0].length; j ++) {
                if (!visited[i][j]) {
                    map[i][j] = '.';
                }
            }
        }
        for (int i = 0; i < map.length; i ++) {
            int visitedCount = 0;
            for (int j = 0; j < map[0].length; j ++) {
                if (visited[i][j]) {
                    visitedCount++;
                    continue;
                }
                if (visitedCount % 2 == 0) fill(i, j, map, visited);
            }
        }

        for (int i = 0; i < map[0].length; i ++) {
            int visitedCount = 0;
            for (int j = 0; j < map.length; j ++) {
                if (visited[j][i]) {
                    visitedCount++;
                    continue;
                }
                if (visitedCount % 2 == 0) fill(j, i, map, visited);
            }
        }
        System.out.println();
        System.out.println("POST FILL MAP: ");
        for (int i = 0; i < map.length; i ++) {
            for (int j = 0; j < map[0].length; j ++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        return longestDistance;
    }

    static void fill(int r, int c, char[][] map, boolean[][] bound) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        visited[r][c] = true;
        queue.add(new Node(r, c, 0));
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (bound[n.r][n.c]) continue;
            map[n.r][n.c] = '0';
            for (int rx = -1; rx <= 1; rx ++) {
                for (int cx = -1; cx <= 1; cx ++) {
                    if (rx + cx == 0) continue;
                    int checkR = n.r + rx;
                    int checkC = n.c + cx;
                    if (!outOfBounds(checkR, checkC, map) && !visited[checkR][checkC] && !bound[checkR][checkC]) {
                        queue.add(new Node(checkR, checkC, 0));
                        visited[checkR][checkC] = true;
                    }
                }
            }
        }
    }

    static boolean outOfBounds(int r, int c, char[][] map) {
        if (r < 0 || c < 0) return true;
        return r >= map.length || c >= map[0].length;
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
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
        dx.put(-1, Map.of(0, List.of('|', '7', 'F')));
        dx.put(1, Map.of(0, List.of('|', 'L', 'J')));
        dx.put(0, Map.of(-1, List.of('-', 'L', 'F'), 1, List.of('-', 'J', '7')));
        part2(map, sr, sc);
        return part2FindTiles(map, sr, sc);
    }

    static Long part2FindTiles(char[][] map, int r, int c) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        visited[r][c] = true;
        for (int rx = -1; rx <= 1; rx++) {
            for (int cx = -1; cx <= 1; cx++) {
                if (rx == cx) continue;
                if (rx + cx == 0) continue;
                int newR = r + rx;
                int newC = c + cx;
                if (!outOfBounds(newR, newC, map) && canGo(rx, cx, r, c, map)) {
                    queue.add(new Node(newR, newC, 1));
                }
            }
        }
        long tiles = 0;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            System.out.println("Doing node: " + n.r + " " + n.c + " (" + map[n.r][n.c] + ") Step: " + n.step);
            visited[n.r][n.c] = true;
            if (map[n.r][n.c] == '0') continue;
            for (int[] dx : directions.get(map[n.r][n.c])) {
                int newR = dx[0] + n.r;
                int newC = dx[1] + n.c;
                Node next = new Node(newR, newC, n.step + 1);
                if (!outOfBounds(newR, newC, map) && !visited[newR][newC]) {
                    queue.add(next);
                }
                for (int rx = -1; rx <= 1; rx ++) {
                    for (int cx = -1; cx <= 1; cx ++) {
                        if (rx + cx == 0) continue;
                        int checkR = n.r + rx;
                        int checkC = n.c + cx;
                        if (!outOfBounds(checkR, checkC, map) && !visited[checkR][checkC] && map[checkR][checkC] == '.') {
                            tiles += countTiles(map, checkR, checkC);
                            visited[checkR][checkC] = true;
                        }
                    }
                }
            }
        }
        return tiles;
    }

    static long countTiles(char[][] map, int r, int c) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<Node> queue = new LinkedList<>();
        visited[r][c] = true;
        queue.add(new Node(r, c, 0));
        long tiles = 0;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            map[n.r][n.c] = '0';
            tiles++;
            for (int rx = -1; rx <= 1; rx ++) {
                for (int cx = -1; cx <= 1; cx ++) {
                    if (rx + cx == 0) continue;
                    int checkR = n.r + rx;
                    int checkC = n.c + cx;
                    if (!outOfBounds(checkR, checkC, map) && !visited[checkR][checkC] && map[checkR][checkC] == '.') {
                        queue.add(new Node(checkR, checkC, 0));
                        visited[checkR][checkC] = true;
                    }
                }
            }
        }
        return tiles;
    }

    static class Node {
        int r, c;
        long step;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Node(int r, int c, long step) {
            this.r = r;
            this.c = c;
            this.step = step;
        }
    }
}
