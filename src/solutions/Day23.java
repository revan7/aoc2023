package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Day23 {


    static int[][] dx = new int[][] {
            {0, 1},
            {-1, 0},
            {0, -1},
            {1, 0}
    };

    static Map<String, Integer> slopeDxIndices = Map.of(
            ">", 0,
            "<", 2,
            "v", 3,
            "^", 1
    );


    static Map<String, List<Integer>> slopeDxIndicesPart2 = Map.of(
            ">", List.of(0, 2),
            "<", List.of(0, 2),
            "v", List.of(3,1),
            "^", List.of(3,1)
    );

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<char[]> preMap = new LinkedList<>();
        while (line != null) {
            preMap.add(line.toCharArray());
            line = br.readLine();
        }
        char[][] map = new char[preMap.size()][preMap.get(0).length];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < preMap.get(0).length; c++) {
                map[r][c] = preMap.get(r)[c];
            }
        }
        print(map);
        return part1(map);
    }

    static void print(char[][] arr) {
        for (var row : arr) {
            for (var c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static Long part1(char[][] map) {
        Queue<Block> q = new LinkedList<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        q.add(start);
        long maxStep = 0;
        while (!q.isEmpty()) {
            Block current = q.poll();
            //System.out.println(current);
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                maxStep = Math.max(maxStep, current.step);
                continue;
            }
            String slopeKey = "" + map[current.r][current.c];
            if (slopeDxIndices.containsKey(slopeKey)) {
                int[] d = dx[slopeDxIndices.get(slopeKey)];
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                if (next.canPlace(map)) {
                    q.add(next);
                }
            } else {
                for (int[] d : dx) {
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    if (next.canPlace(map)) {
                        q.add(next);
                    }
                }
            }
        }
        return maxStep;
    }

    public static Long part2(char[][] map) {
        Queue<Block> q = new LinkedList<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        q.add(start);
        long maxStep = 0;
        while (!q.isEmpty()) {
            Block current = q.poll();
            //System.out.println(current);
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                maxStep = Math.max(maxStep, current.step);
                continue;
            }
            String slopeKey = "" + map[current.r][current.c];
            if (slopeDxIndices.containsKey(slopeKey)) {
                List<Integer> indices = slopeDxIndicesPart2.get(slopeKey);
                for (int i : indices) {
                    int[] d = dx[i];
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    if (next.canPlace(map)) {
                        q.add(next);
                    }
                }
            } else {
                for (int[] d : dx) {
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    if (next.canPlace(map)) {
                        q.add(next);
                    }
                }
            }
        }
        return maxStep;
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<char[]> preMap = new LinkedList<>();
        while (line != null) {
            preMap.add(line.toCharArray());
            line = br.readLine();
        }
        char[][] map = new char[preMap.size()][preMap.get(0).length];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < preMap.get(0).length; c++) {
                map[r][c] = preMap.get(r)[c];
            }
        }
        print(map);
        return part2(map);
    }

    public static class Block {

        int r, c, step;
        Set<List<Integer>> visited = new HashSet<>();

        public Block(int r, int c, int step, Set<List<Integer>> visited) {
            this.r = r;
            this.c = c;
            this.step = step;
            this.visited = new HashSet<>(visited);
        }

        public boolean canPlace(char[][] map) {
            if (r < 0 || c < 0) return false;
            return r < map.length && c < map[0].length && ((map[r][c] == '.') || slopeDxIndices.containsKey(String.valueOf(map[r][c]))) && visited.add(getKey());
        }

        public List<Integer> getKey() {
            return List.of(r, c);
        }

        @Override
        public String toString() {
            return "Block{" +
                    "r=" + r +
                    ", c=" + c +
                    ", step=" + step +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return r == block.r && c == block.c && step == block.step;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
}
