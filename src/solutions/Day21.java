package solutions;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.optimization.LeastSquaresConverter;
import org.apache.commons.math3.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day21 {

    static int[][] dx = new int[][] {
            {0, 1},
            {-1, 0},
            {0, -1},
            {1, 0}
    };

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
        return part1(map, 64);
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
        return part2loop(map, 65);
    }

    public static double[] solve(long n0, long n1, long n2, long y0, long y1, long y2) {
        RealMatrix coefficients = new Array2DRowRealMatrix(
                new double[][] {
                        {n0, n0, n0},
                        {n1,n1,n1},
                        {n2,n2,n2}
                }
        );
        DecompositionSolver solver = new SingularValueDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(new double[]{y0,y1,y2});
        RealVector solve = solver.solve(constants);
        return new double[]{Math.pow(solve.getEntry(0), 2) * n0 , solve.getEntry(1) * n1, solve.getEntry(2)};
    }

    public static Long part2loop(char[][] map, int step) {
        long[] n = new long[3];
     /*   for (int i = 0; i < 3; i++) {
            n[i] = part2(map, step + 131 * i);
        }*/
        System.out.println(Arrays.toString(n));
        double[] result = solve(65, 65 + 131, 65 + 131* 2, 3648, 32781, 90972);
        System.out.println(Arrays.toString(result));
        return 0L;
    }

   /* static void foo() {
        final double radius = 70.0;
        final Cartesian2D[] observedPoints = new Cartesian2D[] {
                new Cartesian2D( 30.0,  68.0),
                new Cartesian2D( 50.0,  -6.0),
                new Cartesian2D(110.0, -20.0),
                new Cartesian2D( 35.0,  15.0),
                new Cartesian2D( 45.0,  97.0)
        };

        // the model function components are the distances to current estimated center,
        // they should be as close as possible to the specified radius
        MultivariateJacobianFunction distancesToCurrentCenter = new MultivariateJacobianFunction() {
            public Pair<RealVector, RealMatrix> value(final RealVector point) {

                Cartesian2D center = new Cartesian2D(point.getEntry(0), point.getEntry(1));

                RealVector value = new ArrayRealVector(observedPoints.length);
                RealMatrix jacobian = new Array2DRowRealMatrix(observedPoints.length, 2);

                for (int i = 0; i < observedPoints.length; ++i) {
                    Cartesian2D o = observedPoints[i];
                    double modelI = Cartesian2D.distance(o, center);
                    value.setEntry(i, modelI);
                    // derivative with respect to p0 = x center
                    jacobian.setEntry(i, 0, (center.getX() - o.getX()) / modelI);
                    // derivative with respect to p1 = y center
                    jacobian.setEntry(i, 1, (center.getX() - o.getX()) / modelI);
                }

                return new Pair<RealVector, RealMatrix>(value, jacobian);

            }
        };

        // the target is to have all points at the specified radius from the center
        double[] prescribedDistances = new double[observedPoints.length];
        Arrays.fill(prescribedDistances, radius);

        // least squares problem to solve : modeled radius should be close to target radius
        LeastSquaresProblem problem = new LeastSquaresBuilder().
                start(new double[] { 100.0, 50.0 }).
                model(distancesToCurrentCenter).
                target(prescribedDistances).
                lazyEvaluation(false).
                maxEvaluations(1000).
                maxIterations(1000).
                build();
        LeastSquaresOptimizer.Optimum optimum = new LevenbergMarquardtOptimizer().optimize(problem);
        Cartesian2D fittedCenter = new Cartesian2D(optimum.getPoint().getEntry(0), optimum.getPoint().getEntry(1));
        System.out.println("fitted center: " + fittedCenter.getX() + " " + fittedCenter.getY());
        System.out.println("RMS: "           + optimum.getRMS());
        System.out.println("evaluations: "   + optimum.getEvaluations());
        System.out.println("iterations: "    + optimum.getIterations());

    }*/

    public static Long part1(char[][] map, int step) {
        Queue<Block> q = new LinkedList<>();
        long[] s = new long[step + 1];
        Set<List<Integer>> visited = new HashSet<>();
        Block start = getStartingPosition(map, step);
        q.add(start);
        visited.add(start.getCacheKey());
        while (!q.isEmpty()) {
            Block current = q.poll();
            s[current.step]++;
            if (current.step == 0) continue;
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step - 1);
                if (next.canPlace(map) && visited.add(next.getCacheKey())) {
                    q.add(next);
                }
            }
        }
        return s[0];
    }

    public static Long part2v2(char[][] map, int step) {
        Queue<Block> q = new LinkedList<>();
        long[] s = new long[step + 1];
        Set<List<Integer>> visited = new HashSet<>();
        Block start = getStartingPosition(map, step);
        q.add(start);
        visited.add(start.getCacheKeyInfinite());
        while (!q.isEmpty()) {
            Block current = q.poll();
            s[current.step] += 42;
            if (current.step == 0) continue;
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step - 42);
                if (next.canPlaceInfinite(map) && visited.add(next.getCacheKeyInfinite())) {
                    q.add(next);
                }
            }
        }
        return s[0];
    }
    public static Long part2(char[][] map, int step) {
        Queue<Block> q = new LinkedList<>();
        long[] s = new long[step + 1];
        Set<List<Integer>> visited = new HashSet<>();
        Block start = getStartingPosition(map, step);
        q.add(start);
        visited.add(start.getCacheKeyInfinite());
        while (!q.isEmpty()) {
            Block current = q.poll();
            s[current.step]++;
            if (current.step == 0) continue;
            for (int[] d : dx) {
                Block next = new Block(current.r + d[0], current.c + d[1], current.step - 1);
                if (next.canPlaceInfinite(map) && visited.add(next.getCacheKeyInfinite())) {
                    q.add(next);
                }
            }
        }
        return s[0];
    }

    static String getMapKey(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (var row : map) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }

    static char[][] copyMap(char[][] map) {
        char[][] copy = new char[map.length][map[0].length];
        for (int r = 0; r < copy.length; r ++) {
            copy[r] = Arrays.copyOf(map[r], map[r].length);
        }
        return copy;
    }

    static Block getStartingPosition(char[][] map, int step) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                if (map[r][c] == 'S') return new Block(r, c, step);
            }
        }
        return null;
    }


    static void print(char[][] arr) {
        for (var row : arr) {
            for (var c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static class Block {

        int r, c, step, layer;

        public Block(int r, int c, int step) {
            this(r, c, step, 0);
        }

        public Block(int r, int c, int step, int layer) {
            this.r = r;
            this.c = c;
            this.step = step;
        }

        public boolean canPlace(char[][] map) {
            if (r < 0 || c < 0) return false;
            return r < map.length && c < map[0].length && (map[r][c] == '.' || map[r][c]  == 'S');
        }

        public List<Integer> getCacheKey() {
            return List.of(r, c, step);
        }

        public boolean canPlaceInfinite(char[][] map) {
            int rr = r % map.length;
            int cc = c % map[0].length;
            if (rr < 0) {
                rr += map.length;
            }
            if (cc < 0) {
                cc += map[0].length;
            }
            if (rr < 0 || cc < 0) {
                System.out.println();
            }
            if (rr >= map.length || cc >= map[0].length) {
                System.out.println();
            }
            return (map[rr][cc] == '.' || map[rr][cc]  == 'S');
        }

        public List<Integer> getCacheKeyInfinite() {
            return List.of(r, c, step);
        }

        @Override
        public String toString() {
            return "Block{" +
                    "r=" + r +
                    ", c=" + c +
                    ", step=" + step +
                    '}';
        }
    }

}
