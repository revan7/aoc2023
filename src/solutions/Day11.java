package solutions;


import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Day11 {

    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        LinkedList<List<Character>> map = new LinkedList<>();
        while (line != null) {
            LinkedList<Character> mapFragment = new LinkedList<>();
            boolean addRow = true;
            for (Character c : line.toCharArray()) {
                if (c == '#') addRow = false;
                mapFragment.add(c);
            }
            map.add(mapFragment);
            if (addRow) {
                Character[] empty = new Character[mapFragment.size()];
                Arrays.fill(empty, '.');
                map.add(new LinkedList<>(Arrays.asList(empty)));
            }
            line = br.readLine();
        }
        int[] colCount = new int[map.get(0).size()];
        for (int col = 0; col < map.get(0).size(); col ++) {
            for (int row = 0; row < map.size(); row ++) {
                if (map.get(row).get(col) == '#') {
                    colCount[col]++;
                }
            }
        }
        int offset = 0;
        for (int i = 0; i < colCount.length; i ++) {
            if (colCount[i] == 0) {
                for (List<Character> fragment : map) {
                    fragment.add(i + offset, '.');
                }
                offset++;
            }
        }
        int nodeCount = 0;
        List<Node> nodes = new LinkedList<>();
        for (int r = 0; r < map.size(); r ++) {
            for (int c = 0; c < map.get(0).size(); c ++ ) {
                if (map.get(r).get(c) == '#') {
                    nodes.add(new Node(nodeCount++, r, c));
                }
            }
        }
        long expansion = 100L;
        long[][] distances = new long[nodeCount][nodeCount];
        for (int i = 0; i < nodes.size(); i ++ ){
            Node a = nodes.get(i);
            for (int j = 0; j < nodes.size(); j ++){
                Node b = nodes.get(j);
                if (distances[a.n][b.n] == 0) {
                    long distance = Math.abs(b.r - a.r) + Math.abs(b.c - a.c);
                    distance += (distance * expansion);
                    distances[a.n][b.n] = distance;
                }
            }
        }
        int sum = 0;
        for (var row : distances) {
            for (var n : row) sum += n;
        }
        return (long) sum / 2;
    }

    public static Long solvePart2_bf(BufferedReader br) throws IOException {
        String line = br.readLine();
        LinkedList<List<Character>> map = new LinkedList<>();
        while (line != null) {
            LinkedList<Character> mapFragment = new LinkedList<>();
            boolean addRow = true;
            for (Character c : line.toCharArray()) {
                if (c == '#') addRow = false;
                for (int i =0 ;i < 1000000; i ++) mapFragment.add(c);
            }
            map.add(mapFragment);
            if (addRow) {
                Character[] empty = new Character[mapFragment.size()];
                Arrays.fill(empty, '.');
                map.add(new LinkedList<>(Arrays.asList(empty)));
            }
            line = br.readLine();
        }
        int[] colCount = new int[map.get(0).size()];
        for (int col = 0; col < map.get(0).size(); col ++) {
            for (int row = 0; row < map.size(); row ++) {
                if (map.get(row).get(col) == '#') {
                    colCount[col]++;
                }
            }
        }
        int offset = 0;
        for (int i = 0; i < colCount.length; i ++) {
            if (colCount[i] == 0) {
                for (List<Character> fragment : map) {
                    for (int j = 0; j < 1000000; j ++) fragment.add(i + offset, '.');
                }
                offset++;
            }
        }
        int nodeCount = 0;
        List<Node> nodes = new LinkedList<>();
        for (int r = 0; r < map.size(); r ++) {
            for (int c = 0; c < map.get(0).size(); c ++ ) {
                if (map.get(r).get(c) == '#') {
                    nodes.add(new Node(nodeCount++, r, c));
                }
            }
        }
        long expansion = 100L;
        long[][] distances = new long[nodeCount][nodeCount];
        for (int i = 0; i < nodes.size(); i ++ ){
            Node a = nodes.get(i);
            for (int j = 0; j < nodes.size(); j ++){
                Node b = nodes.get(j);
                if (distances[a.n][b.n] == 0) {
                    long distance = Math.abs(b.r - a.r) + Math.abs(b.c - a.c);
                    distance += (distance * expansion);
                    distances[a.n][b.n] = distance;
                }
            }
        }
        int sum = 0;
        for (var row : distances) {
            for (var n : row) sum += n;
        }
        return (long) sum / 2;
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.SingleShotTime)
    public static Long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        LinkedList<List<Character>> map = new LinkedList<>();
        long expanse = 1000000;
        while (line != null) {
            LinkedList<Character> mapFragment = new LinkedList<>();
            for (Character c : line.toCharArray()) {
                mapFragment.add(c);
            }
            map.add(mapFragment);
            line = br.readLine();
        }
        //print(map);
        int nodeCount = 0;
        int[] galaxiesOnColumn = new int[map.get(0).size()];
        int[] galaxiesOnRow = new int[map.size()];
        Node[][] universe = new Node[map.size()][map.get(0).size()];
        List<Node> nodes = new LinkedList<>();
        for (int row = 0; row < map.size(); row++) {
            for (int col = 0; col < map.get(0).size(); col++) {
                Node toAdd = new Node(-1, row, col, 1);
                if (map.get(row).get(col) == '#') {
                    toAdd.n = nodeCount++;
                    nodes.add(toAdd);
                    galaxiesOnColumn[col]++;
                    galaxiesOnRow[row]++;
                }
                universe[row][col] = toAdd;
            }
        }
        for (int row = 0; row < map.size(); row++) {
            if (galaxiesOnRow[row] == 0) {
                for (int col = 0; col < map.get(0).size(); col ++) {
                    universe[row][col].step = expanse;
                }
            }
        }
        for (int col = 0; col < map.get(0).size(); col++) {
            if (galaxiesOnColumn[col] == 0) {
                for (int row = 0; row < map.size(); row++) {
                    universe[row][col].step = expanse;
                }
            }
        }
        return solveBfs(universe, nodes);
    }

    // SUCCESSFUL Attempt at solution with math
    public static Long solvePart2_butCool(BufferedReader br) throws IOException {
        String line = br.readLine();
        LinkedList<List<Character>> map = new LinkedList<>();
        while (line != null) {
            LinkedList<Character> mapFragment = new LinkedList<>();
            for (Character c : line.toCharArray()) {
                mapFragment.add(c);
            }
            map.add(mapFragment);
            line = br.readLine();
        }
        int[] galaxiesOnColumn = new int[map.get(0).size()];
        int[] galaxiesOnRow = new int[map.size()];
        for (int row = 0; row < map.size(); row ++) {
            for (int col = 0; col < map.get(0).size(); col++) {
                if (map.get(row).get(col) == '#') {
                    galaxiesOnColumn[col]++;
                    galaxiesOnRow[row]++;
                }
            }
        }
        int nodeCount = 0;
        List<Node2> nodes = new LinkedList<>();
        long expanse = 1000000;
        //Update distances
        for (int row = 0; row < map.size(); row ++) {
            long rowOffset = 0;
            long emptyRows = 0;
            for (int rc = row; rc >= 0; rc --) {
                if (galaxiesOnRow[rc] == 0) {
                    rowOffset += expanse;
                    emptyRows++;
                }
            }
            for (int col = 0; col < map.get(0).size(); col++) {
                if (map.get(row).get(col) == '#') {
                    long columnOffset = 0;
                    int emptyColumns = 0;
                    for (int cc = col; cc >= 0; cc--) {
                        if (galaxiesOnColumn[cc] == 0) {
                            columnOffset += expanse;
                            emptyColumns++;
                        }
                    }
                    Node2 node = new Node2(nodeCount++, row + rowOffset - emptyRows, col + columnOffset - emptyColumns);
                    nodes.add(node);
                }
            }
        }
        long[][] distances = new long[nodeCount][nodeCount];
        for (int i = 0; i < nodes.size(); i ++ ) {
            Node2 a = nodes.get(i);
            for (int j = 0; j < nodes.size(); j ++) {
                Node2 b = nodes.get(j);
                if (distances[a.n][b.n] == 0) {
                    long distance = Math.abs(b.r - a.r) + Math.abs(b.c - a.c);
                    distances[a.n][b.n] = distance;
                }
            }
        }
        long sum = 0;
        for (var row : distances) {
            for (var n : row) sum += n;
        }
        return sum / 2;
    }
    static int[][] dx = new int[][]{
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1},
    };

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static void bfs(Node[][] universe, Node n, long[][] distanceMap) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.step));
        long previousStep = n.step;
        n.step = 0;
        pq.add(n);
        Set<Integer> galaxiesVisited = new HashSet<>();
        Set<Node> nodesVisited = new HashSet<>();
        galaxiesVisited.add(n.n);
        nodesVisited.add(universe[n.r][n.c]);
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (curr.n >= 0 && curr.n != n.n) {
                distanceMap[n.n][curr.n] = Math.min(distanceMap[n.n][curr.n], curr.step);
                galaxiesVisited.add(n.n);
                if (galaxiesVisited.size() == distanceMap.length) break;
            }
            for (var dv : dx) {
                int nextR = curr.r + dv[0];
                int nextC = curr.c + dv[1];
                if (isInBounds(nextR, nextC, universe)) {
                    Node galaxyPoint = universe[nextR][nextC];
                    Node next = new Node(galaxyPoint.n, galaxyPoint.r, galaxyPoint.c, galaxyPoint.step + curr.step);
                    if (nodesVisited.add(galaxyPoint)) {
                        pq.add(next);
                    }
                }
            }
        }
        n.step = previousStep;
    }

    static boolean isInBounds(int r, int c, Node[][] universe) {
        if (r < 0 || c < 0) return false;
        return r < universe.length && c < universe[0].length;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.SingleShotTime)
    public static Long solveBfs(Node[][] universe, List<Node> nodes) {
        long[][] distances = new long[nodes.size()][nodes.size()];
        for (var row : distances) {
            Arrays.fill(row, Long.MAX_VALUE);
        }
        for (Node n : nodes) {
            bfs(universe, n, distances);
        }
        long sum = 0;
        for (var row : distances) {
            for (var n : row) {
                if (n == Long.MAX_VALUE) continue;
                sum += n;
            }
        }
        return sum / 2;
    }

    static void print(List<List<Character>> map) {
        for (List<Character> fragment : map) {
            System.out.println(fragment);
        }
    }

    public static Long part1(List<List<Character>> map) {
        List<int[]> galaxyCoords = new LinkedList<>();
        for (int r = 0; r < map.size(); r ++) {
            for (int c = 0; c < map.get(0).size(); c ++) {
                if (map.get(r).get(c) == '#') {
                    galaxyCoords.add(new int[] {r, c});
                }
            }
        }
        return 0L;
    }

    public static class Node {
        int n;
        int r,c;
        long step;

        public Node(int n, int r, int c) {
            this.n = n;
            this.r = r;
            this.c = c;
        }

        public Node(int n, int r, int c, long step) {
            this.n = n;
            this.r = r;
            this.c = c;
            this.step = step;
        }
    }


    public static class Node2 {
        int n;
        long r,c;
        long step;

        public Node2(int n, long r, long c, long step) {
            this.n = n;
            this.r = r;
            this.c = c;
            this.step = step;
        }

        public Node2(int n, long r, long c) {
            this.n = n;
            this.r = r;
            this.c = c;
        }
    }

}
