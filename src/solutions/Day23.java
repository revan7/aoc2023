package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.ObjLongConsumer;

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
        return part1DFSMemoJump(map);
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
        var startTime = System.currentTimeMillis();
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
        var endtime = System.currentTimeMillis();
        System.out.println((endtime - startTime) / 1000);
        return maxStep;
    }

    public static Long part1DFS(char[][] map) {
        var startTime = System.currentTimeMillis();
        Stack<Block> stack = new Stack<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        long maxStep = 0;
        stack.add(start);
        Set<Block> visiting = new HashSet<>();
        while (!stack.isEmpty()) {
            Block current = stack.pop();
            visiting.remove(current);
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                //System.out.println(current);
                maxStep = Math.max(maxStep, current.step);
                //System.out.println("Step : " + maxStep);
                continue;
            }
            String slopeKey = "" + map[current.r][current.c];
            if (slopeDxIndices.containsKey(slopeKey)) {
                int[] d = dx[slopeDxIndices.get(slopeKey)];
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                if (next.canPlace(map)) {
                    stack.push(next);
                    visiting.add(next);
                }
            } else {
                for (int[] d : dx) {
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    if (next.canPlace(map)) {
                        stack.push(next);
                        visiting.add(next);
                    }
                }
            }
        }
        var endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return maxStep;
    }

    public static Long part1DFSMemo(char[][] map) {
        var startTime = System.currentTimeMillis();
        Stack<Block> stack = new Stack<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        long maxStep = 0;
        stack.add(start);
        Set<Block> visiting = new HashSet<>();
        Map<List<Integer>, Integer> memo = new HashMap<>();
        while (!stack.isEmpty()) {
            Block current = stack.pop();
            if (memo.containsKey(current.getKey())) {
                if (current.step < memo.get(current.getKey())) continue;
            }
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                //System.out.println(current);
                maxStep = Math.max(maxStep, current.step);
                System.out.println("Step : " + maxStep);
                continue;
            }
            String slopeKey = "" + map[current.r][current.c];
            if (slopeDxIndices.containsKey(slopeKey)) {
                int[] d = dx[slopeDxIndices.get(slopeKey)];
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                if (next.canPlace(map)) {
                    stack.push(next);
                    visiting.add(next);
                }
            } else {
                for (int[] d : dx) {
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    if (next.canPlace(map)) {
                        stack.push(next);
                        visiting.add(next);
                    }
                }
            }
            memo.put(current.getKey(), current.step);
        }
        var endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return maxStep;
    }

    public static Long part1DFSMemoJump(char[][] map) {
        var startTime = System.currentTimeMillis();
        Stack<Block> stack = new Stack<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        long maxStep = 0;
        stack.add(start);
        Set<Block> visiting = new HashSet<>();
        Map<List<Integer>, Integer> memo = new HashMap<>();
        while (!stack.isEmpty()) {
            Block current = stack.pop();
//            System.out.println(current);
/*            if (memo.containsKey(current.getKey())) {
                if (current.step < memo.get(current.getKey())) continue;
            }*/
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                //System.out.println(current);
                maxStep = Math.max(maxStep, current.step);
                System.out.println("Step : " + maxStep);
                continue;
            }
            String slopeKey = "" + map[current.r][current.c];
            if (slopeDxIndices.containsKey(slopeKey)) {
                int[] d = dx[slopeDxIndices.get(slopeKey)];
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                Block toAdd = next;
                while (next.canPlaceOnMap(map) && !next.isIntersection(map)) {
                    toAdd = next;
                    next = new Block(next.r + d[0], next.c + d[1], next.step + 1, next.visited);
                }
                if (toAdd.canPlace(map)) {
                    stack.push(toAdd);
                }
            } else {
                for (int[] d : dx) {
                    Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                    Block toAdd = next;
                    while (next.canPlaceOnMap(map) && !next.isIntersection(map)) {
                        toAdd = next;
                        next = new Block(next.r + d[0], next.c + d[1], next.step + 1, next.visited);
                    }
                    if (next.isIntersection(map)) toAdd = next;
                    if (toAdd.canPlace(map)) {
                        stack.push(toAdd);
                    }
                }
            }
            memo.put(current.getKey(), current.step);
        }
        var endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return maxStep;
    }
    public static Long part2DFSMemoJump(char[][] map) {
        var startTime = System.currentTimeMillis();
        Stack<Block> stack = new Stack<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        long maxStep = 0;
        stack.add(start);
        Set<Block> visiting = new HashSet<>();
        Map<List<Integer>, Integer> memo = new HashMap<>();
        while (!stack.isEmpty()) {
            Block current = stack.pop();
//            System.out.println(current);
/*            if (memo.containsKey(current.getKey())) {
                if (current.step < memo.get(current.getKey())) continue;
            }*/
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                //System.out.println(current);
                maxStep = Math.max(maxStep, current.step);
                System.out.println("Step : " + maxStep);
                continue;
            }
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1);
                Block toAdd = next;
                while (next.canPlaceOnMap(map) && !next.isIntersection(map)) {
                    toAdd = next;
                    next = new Block(next.r + d[0], next.c + d[1], next.step + 1);
                }
                if (next.isIntersection(map)) toAdd = next;
                if (toAdd.canPlaceOnMap(map) && current.visited.add(toAdd.getKey())) {
                    toAdd.visited = new HashSet<>(current.visited);
                    stack.push(toAdd);
                }
            }
            memo.put(current.getKey(), current.step);
        }
        var endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return maxStep;
    }

    public static Long part2DFSMemo(char[][] map) {
        var startTime = System.currentTimeMillis();
        Stack<Block> stack = new Stack<>();
        Block start = new Block(0, 1, 0, new HashSet<>());
        long maxStep = 0;
        stack.add(start);
        Set<Block> visiting = new HashSet<>();
        Map<List<Integer>, Integer> memo = new HashMap<>();
        while (!stack.isEmpty()) {
            Block current = stack.pop();
            if (memo.containsKey(current.getKey())) {
                if (current.step < memo.get(current.getKey())) continue;
            }
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                //System.out.println(current);
                maxStep = Math.max(maxStep, current.step);
//                System.out.println("Step : " + maxStep);
                continue;
            }
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1, current.visited);
                if (next.canPlace(map)) {
                    stack.push(next);
                    visiting.add(next);
                }
            }
            memo.put(current.getKey(), current.step);
        }
        var endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        return maxStep;
    }

    public static Long part2(char[][] map) {
        Queue<Block> q = new LinkedList<>();
        Block start = new Block(0, 1, 0,  new HashSet<>());
        q.add(start);
        long maxStep = 0;
        Map<List<Integer>, Long> memo = new HashMap<>();
        while (!q.isEmpty()) {
            Block current = q.poll();
            //System.out.println(current);
            if (current.r == map.length - 1 && current.c == map[0].length - 2) {
                maxStep = Math.max(maxStep, current.step);
                System.out.println("Found: " + maxStep);
                continue;
            }
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step + 1);
                Block toAdd = next;
                while (next.canPlaceOnMap(map) && !next.isIntersection(map)) {
                    toAdd = next;
                    next = new Block(next.r + d[0], next.c + d[1], next.step + 1);
                }
                if (next.isIntersection(map)) toAdd = next;
                toAdd.visited = new HashSet<>(current.visited);
                if (toAdd.canPlace(map)) {
                    q.add(toAdd);
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
        return part2DFSMemoJump(map);
    }

    public static class Block {

        int r, c, step, it;

        public Block(int r, int c, int step) {
            this.r = r;
            this.c = c;
            this.step = step;
        }

        public Block(int r, int c, int step, int it, Set<List<Integer>> visited) {
            this.r = r;
            this.c = c;
            this.step = step;
            this.it = it;
            this.visited = visited;
        }

        Set<List<Integer>> visited = new HashSet<>();

        public Block(int r, int c, int step, Set<List<Integer>> visited) {
            this.r = r;
            this.c = c;
            this.step = step;
            this.visited = new HashSet<>(visited);
        }

        public int distanceTo(int r, int c) {
            return Math.abs(r - this.r) + Math.abs(c - this.c);
        }

        public boolean canPlace(char[][] map) {
            if (r < 0 || c < 0) return false;
            return r < map.length && c < map[0].length && ((map[r][c] == '.') || slopeDxIndices.containsKey(String.valueOf(map[r][c]))) && visited.add(getKey());
        }

        public boolean canPlaceOnMap(char[][] map) {
            if (r < 0 || c < 0) return false;
            return r < map.length && c < map[0].length && ((map[r][c] == '.') || slopeDxIndices.containsKey(String.valueOf(map[r][c])));
        }

        public boolean isIntersection(char[][] map) {
            if (canPlaceOnMap(map)) {
                for (int i = 0; i < dx.length; i ++) {
                    int[] d = dx[i];
                    int[] e = dx[(i + 1) % 4];
                    Block b = new Block(this.r + d[0], this.c + d[1], step, visited);
                    Block c = new Block(this.r + e[0], this.c + e[1], step, visited);
                    if (b.canPlaceOnMap(map) && c.canPlaceOnMap(map)) return true;
                }
            }
            return false;
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
