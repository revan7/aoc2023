package solutions;


import java.io.BufferedReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
        /*
        long expansion = 100L;
        long[][] distances = new long[nodeCount][nodeCount];
        for (int i = 0; i < nodes.size(); i ++ ){
            Node a = nodes.get(i);
            for (int j = 0; j < nodes.size(); j ++){
                Node b = nodes.get(j);
                if (distances[a.n][b.n] == 0) {
                    long bR = expansion * b.r + b.r;
                    long bC = expansion * b.c + b.c;
                    long aR = expansion * a.r + a.r;
                    long aC = expansion * a.c + a.c;
                    System.out.println("Coord of " + a.n + " AR:" + aR + " AC:" + aC + " BR:" + bR + " BC:" + bC);
                    long distance = Math.abs(bR - aR) + Math.abs(bC - aC);
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
         */
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
    public static Long solvePart2(BufferedReader br) throws IOException {
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
        int nodeCount = 0;
        long expansion = 100L;
        List<Node> nodes = new LinkedList<>();
        long[] spaceAbove = new long[map.get(0).size()];
        for (int r = 0; r < map.size(); r ++) {
            long spaceLeft = 0;
            for (int c = 0; c < map.get(0).size(); c ++ ) {
                if (map.get(r).get(c) == '#') {
                    spaceLeft += c;
                    spaceAbove[c] += r;
                    nodes.add(new Node(nodeCount++, spaceAbove[c], spaceLeft));
                } else {
                    spaceLeft += expansion;
                    spaceAbove[c] += expansion;
                }
            }
        }
        long[][] distances = new long[nodeCount][nodeCount];
        for (int i = 0; i < nodes.size(); i ++ ) {
            Node a = nodes.get(i);
            for (int j = 0; j < nodes.size(); j ++) {
                Node b = nodes.get(j);
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

    static class Node {
        int n;
        long r,c;

        long relativeR, relativeC;

        public Node(int n, long r, long c) {
            this.n = n;
            this.r = r;
            this.c = c;
        }

        public Node(int n, long r, long c, long relativeR, long relativeC) {
            this.n = n;
            this.r = r;
            this.c = c;
            this.relativeR = relativeR;
            this.relativeC = relativeC;
        }
    }

}
