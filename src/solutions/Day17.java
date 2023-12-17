package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day17 {

    static Map<Long, LinkedList<String>> map = new HashMap<>();

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<char[]> preMap = new LinkedList<>();
        while (line != null) {
            preMap.add(line.toCharArray());
            line = br.readLine();
        }
        int[][] map = new int[preMap.size()][preMap.get(0).length];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < preMap.get(0).length; c++) {
                map[r][c] = Integer.parseInt("" + preMap.get(r)[c]);
            }
        }
        print(map);
        return part1(map);
    }


    public static long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<char[]> preMap = new LinkedList<>();
        while (line != null) {
            preMap.add(line.toCharArray());
            line = br.readLine();
        }
        int[][] map = new int[preMap.size()][preMap.get(0).length];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < preMap.get(0).length; c++) {
                map[r][c] = Integer.parseInt("" + preMap.get(r)[c]);
            }
        }
        print(map);
        return part2(map);
    }

    static long part1(int[][] map) {
        long result = 0;
        result += bfs(map);
        return result;
    }

    static long bfs(int[][] map) {
        PriorityQueue<Block> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.step));
        Block goingRight = new Block(0,1, 0, 1, 0, 0, 0);
        //Block goingDown = new Block(1,0 ,1,0 , 0, 0, 0);
        q.add(goingRight);
        //q.add(goingDown);
        Set<String> visited = new HashSet<>();
        //visited.add(goingDown.hash());
        visited.add(goingRight.hash());
        long minStep = Long.MAX_VALUE;
        while (!q.isEmpty()) {
            Block curr = q.poll();
            curr.step = curr.step + map[curr.r][curr.c];
            //System.out.println("Doing curr: " + curr);
            if (curr.r == map.length - 1 && curr.c == map[0].length - 1) {
                minStep = Math.min(minStep, curr.step);
            }
            Block left = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
            left.turnLeft();
            left.moveForward();
            if (left.canPlace(map) && visited.add(left.hash())) q.add(left);
            Block right = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
            right.turnRight();
            right.moveForward();
            if (right.canPlace(map) && visited.add(right.hash())) q.add(right);
            if (curr.distanceToStart() < 3) {
                curr.moveForward();
                if (curr.canPlace(map) && visited.add(curr.hash())) q.add(curr);
            }
        }
        return minStep;
    }

    static long bfs2(int[][] map) {
        PriorityQueue<Block> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.step));
        Block goingRight = new Block(0,1, 0, 1, 0, 0, 0);
        Block goingDown = new Block(1,0 ,1,0 , 0, 0, 0);
        q.add(goingRight);
        q.add(goingDown);
        Set<String> visited = new HashSet<>();
        visited.add(goingDown.hash());
        visited.add(goingRight.hash());
        long minStep = Long.MAX_VALUE;
        while (!q.isEmpty()) {
            Block curr = q.poll();
            curr.step = curr.step + map[curr.r][curr.c];
            //System.out.println("Doing curr: " + curr);
            if (curr.r == map.length - 1 && curr.c == map[0].length - 1 && curr.distanceToStart() >= 4) {
                minStep = Math.min(minStep, curr.step);
            }
            if (curr.distanceToStart() < 4) {
                curr.moveForward();
                if (curr.canPlace(map) && visited.add(curr.hash())) q.add(curr);
            } else if (curr.distanceToStart() >= 4 && curr.distanceToStart() < 10) {
                Block left = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
                left.turnLeft();
                left.moveForward();
                if (left.canPlace(map) && visited.add(left.hash())) q.add(left);
                Block right = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
                right.turnRight();
                right.moveForward();
                if (right.canPlace(map) && visited.add(right.hash())) q.add(right);
                curr.moveForward();
                if (curr.canPlace(map) && visited.add(curr.hash())) q.add(curr);
            } else if (curr.distanceToStart() >= 10) {
                Block left = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
                left.turnLeft();
                left.moveForward();
                if (left.canPlace(map) && visited.add(left.hash())) q.add(left);
                Block right = new Block(curr.r, curr.c, curr.speedR, curr.speedC, curr.r, curr.c, curr.step);
                right.turnRight();
                right.moveForward();
                if (right.canPlace(map) && visited.add(right.hash())) q.add(right);
            }
        }
        return minStep;
    }

    static long part2(int[][] map) {
        long result = 0;
        result += bfs2(map);
        return result;
    }

    static long getResult(boolean[][] visited) {
        long result = 0;
        for (var row : visited) {
            for (var n : row) {
                if (n) result++;
            }
        }
        return result;
    }

    static void print(int[][] arr) {
        for (var row : arr) {
            for (var c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static class Block {

        int r, c, speedR, speedC, startRow, startCol, step;
        boolean[][] visited;

        public Block(int speedR, int speedC, int startRow, int startCol, int step) {
            this.speedR = speedR;
            this.speedC = speedC;
            this.startRow = startRow;
            this.startCol = startCol;
            this.step = step;
        }

        public Block(int r, int c, int speedR, int speedC, int startRow, int startCol, int step) {
            this.r = r;
            this.c = c;
            this.speedR = speedR;
            this.speedC = speedC;
            this.startRow = startRow;
            this.startCol = startCol;
            this.step = step;
        }

        public int distanceToStart() {
            return Math.abs(r - startRow) + Math.abs(c - startCol);
        }

        public void moveForward() {
            r += speedR; c += speedC;
        }

        public void turnLeft() {
            if (speedR == 0 && speedC == 1) {
                speedR = -1;
                speedC = 0;
            }
            else if (speedR == -1 && speedC == 0) {
                speedR = 0;
                speedC = -1;
            }
            else if (speedR == 0 && speedC == -1) {
                speedR = 1;
                speedC = 0;
            }
            else {
                speedR = 0;
                speedC = 1;
            }
        }

        public void turnRight() {
            if (speedR == 0 && speedC == 1) {
                speedR = 1;
                speedC = 0;
            } else if (speedR == 1 && speedC == 0) {
                speedR = 0;
                speedC = -1;
            } else if (speedR == 0 && speedC == -1) {
                speedR = 1;
                speedC = 0;
            } else {
                speedR = 0;
                speedC = 1;
            }
        }

        public boolean canPlace(int[][] map) {
            if (r < 0 || c < 0) return false;
            return r < map.length && c < map[0].length;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "r=" + r +
                    ", c=" + c +
                    ", speedR=" + speedR +
                    ", speedC=" + speedC +
                    ", startRow=" + startRow +
                    ", startCol=" + startCol +
                    ", step=" + step +
                    '}';
        }

        public String hash() {
            return "Block{" +
                    "r=" + r +
                    ", c=" + c +
                    ", speedR=" + speedR +
                    ", speedC=" + speedC +
                    ", startRow=" + startRow +
                    ", startCol=" + startCol +
                    '}';
        }

    }

}
