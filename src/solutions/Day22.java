package solutions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day22 {

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<Brick> bricks = new LinkedList<>();
        Brick ground = new Brick("ground", new Point(0,0, 0), new Point(0,0,0));
        bricks.add(ground);
        int i = 0;
        while (line != null) {
            String[] split = line.split("~");
            split[0] = split[0].replace(",", "").trim();
            split[1] = split[1].replace(",", "").trim();
            Point a = new Point(Integer.parseInt(split[0].charAt(0) + ""), Integer.parseInt(split[0].charAt(1) + ""), Integer.parseInt(split[0].charAt(2) + ""));
            Point b = new Point(Integer.parseInt(split[1].charAt(0) + ""), Integer.parseInt(split[1].charAt(1) + ""), Integer.parseInt(split[1].charAt(2) + ""));
            bricks.add(new Brick("" + (char)('A' + i++), a, b));
            line = br.readLine();
        }
        bricks.sort(Comparator.comparingInt(a -> a.a.z));
        System.out.println(bricks);
        return part1(bricks);
    }

    public static Long part1(List<Brick> bricks) {
        System.out.println(bricks);
        for (int i = 1; i < bricks.size(); i ++) {
            Brick curr = bricks.get(i);
            Brick prev = null;
            int zDelta = curr.b.z - curr.a.z;
            for (int j = i - 1; j >= 0; j --) {
                prev = bricks.get(j);
                curr.a.z = prev.a.z;
                curr.b.z = curr.a.z + zDelta;
                if (prev.intersects(curr)) {
                    System.out.println(curr.name + " intersecting " + prev.name);
                    break;
                }
            }
            curr.a.z = prev.b.z + 1;
            curr.b.z = curr.a.z + zDelta;
        }

        System.out.println(bricks);
        bricks.remove(0);
        long count = 0;
        Map<Integer, List<Brick>> bricksByLevel = new HashMap<>();
        for (var brick : bricks) {
            if (!bricksByLevel.containsKey(brick.a.z)) {
                bricksByLevel.put(brick.a.z, new LinkedList<>());
            }
            bricksByLevel.get(brick.a.z).add(brick);
        }
      /*  for (int i = 1; i < bricksByLevel.size(); i ++) {
            List<Brick> previousLevel = bricksByLevel.get(i - 1);
            if (previousLevel.size() == 1) continue;
            List<Brick> currentLevel = bricksByLevel.get(i);
            for (var brick : currentLevel) {
                boolean intersectsAll = true;
                for (int j = 0; j < previousLevel.size(); j ++) {
                    for (int k = 0; k < previousLevel.size(); k ++) {
                        if (k == j) continue;
                        Brick intersectionCopy = new Brick(brick.name +" copy", new Point(brick.a.x, brick.a.y, brick.a.z - 1), new Point(brick.b.x, brick.b.y, brick.b.z - 1));
                        if (!previousLevel.get(k).intersects(intersectionCopy)) {
                            System.out.println(previousLevel.get(k).name + " not intersecting with " + intersectionCopy.name);
                            intersectsAll = false;
                            break;
                        }
                    }
                    if (intersectsAll) count++;
                }
            }
        }*/
        for (var level : bricksByLevel.entrySet()) {
            int l = level.getKey();
            if (bricksByLevel.containsKey(l + 1)) {
                List<Brick> currLevel = bricksByLevel.get(l);
                List<Brick> nextLevel = bricksByLevel.get(l + 1);
                for (int ignoreIndex = 0; ignoreIndex < currLevel.size(); ignoreIndex++) {
                    boolean intersectsAll = true;
                    for (int i = 0; i < currLevel.size() && intersectsAll; i++) {
                        if (i == ignoreIndex) continue;
                        for (var brick : nextLevel) {
                            if (!brick.intersects(currLevel.get(i))) {
                                intersectsAll = false;
                                break;
                            }
                        }
                        count++;
                    }
                }
            } else count += level.getValue().size();
        }
        return count;
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        return 0L;
    }

    public static class Point {
        int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    public record Brick(String name, Point a, Point b) {

        public int zDistance(Brick other) {
            return this.a.z - other.b.z;
        }

        private List<Point> allPoints() {
            List<Point> toReturn = new LinkedList<>();
            for (int i = a.x; i <= b.x; i ++) {
                for (int j = a.y; j <= b.y; j ++) {
                    for (int k = a.z; k <= b.z; k ++) {
                        toReturn.add(new Point(i, j, k));
                    }
                }
            }
            return toReturn;
        }

        boolean intersects(Brick other) {
            List<Point> aPoints = allPoints();
            List<Point> bPoints = other.allPoints();
            for (var n : aPoints) {
                if (bPoints.contains(n)) return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Brick brick = (Brick) o;
            return Objects.equals(a, brick.a) && Objects.equals(b, brick.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public String toString() {
            return "Name: " + name + " (a:" + a + " b:" + b + ")";
        }
    }

}
