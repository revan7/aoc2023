package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day16 {

    static Map<Long, LinkedList<String>> map = new HashMap<>();

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
        return part1(map);
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
        return part2(map);
    }

    static long part1(char[][] map) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        int[] start = new int[]{0, 0, 0, 1, 0, 0};
        bfs(visited, map, start);
        printVisited(visited);
        return getResult(visited);
    }


    static long part2(char[][] map) {
        long maxResult = 0;
        for (int r = 0; r < map.length; r++) {
            boolean[][] visitedLeft = new boolean[map.length][map[0].length];
            int[] startLeft = new int[]{r, 0, 0, 1, r, 0};
            bfs(visitedLeft, map, startLeft);

            boolean[][] visitedRight = new boolean[map.length][map[0].length];
            int[] startRight = new int[]{r, map[0].length - 1, 0, -1, r, map[0].length - 1};
            bfs(visitedRight, map, startRight);

            maxResult = Math.max(maxResult, Math.max(getResult(visitedRight), getResult(visitedLeft)));
        }

        for (int c = 0; c < map[0].length; c++) {
            boolean[][] visitedLeft = new boolean[map.length][map[0].length];
            int[] startLeft = new int[]{0, c, 1, 0, 0, c};
            bfs(visitedLeft, map, startLeft);

            boolean[][] visitedRight = new boolean[map.length][map[0].length];
            int[] startRight = new int[]{map.length - 1, c, -1, 0, map.length - 1, c};
            bfs(visitedRight, map, startRight);

            maxResult = Math.max(maxResult, Math.max(getResult(visitedRight), getResult(visitedLeft)));
        }
        return maxResult;
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

    static void printVisited(boolean[][] visited) {
        for (var row : visited) {
            for (var n : row) {
                System.out.print(n ? "#" : ".");
            }
            System.out.println();
        }
    }

    static String cacheMap(int[][] map) {
        StringBuilder sb = new StringBuilder();
        for (var row : map) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }

    static void bfs(boolean[][] visited, char[][] map) {
        Queue<int[]> q = new LinkedList<>();
        int beamId = 0;
        int[] start = new int[]{0, 0, 0, 1, 0, 0};
        int[][] v = new int[map.length][map[0].length];
        q.add(start);
        visited[0][0] = true;
        Set<String> cache = new HashSet<>();
        while (!q.isEmpty()) {
/*            printVisited(visited);
            System.out.println();
            System.out.println();*/
            int[] current = q.poll();
            visited[current[0]][current[1]] = true;
            if (!cache.add(Arrays.toString(current))) continue;
            char c = map[current[0]][current[1]];
            if (isSplitting(c, current)) {
                beamId = current[4] + 1;
                if (current[2] == 0) {
                    int[] up = new int[]{current[0] - 1, current[1], -1, 0, current[0], current[1]};
                    int[] down = new int[]{current[0] + 1, current[1], 1, 0, current[0], current[1]};
                    if (canAdd(up, map)) {
                        visited[up[0]][up[1]] = true;
                        q.add(up);
                    }
                    if (canAdd(down, map)) {
                        visited[down[0]][down[1]] = true;
                        q.add(down);
                    }
                } else {
                    if (current[3] == 0) {
                        int[] left = new int[]{current[0], current[1] - 1, 0, -1, current[0], current[1]};
                        int[] right = new int[]{current[0], current[1] + 1, 0, 1, current[0], current[1]};
                        if (canAdd(left, map)) {
                            visited[left[0]][left[1]] = true;
                            q.add(left);
                        }
                        if (canAdd(right, map)) {
                            visited[right[0]][right[1]] = true;
                            q.add(right);
                        }
                    }
                }
            } else {
                int[] speed = calculateSpeed(c, current);
                int[] next = new int[]{speed[0] + current[0], speed[1] + current[1], speed[0], speed[1], current[0], current[1]};
                if (canAdd(next, map)) {
                    visited[next[0]][next[1]] = true;
                    q.add(next);
                }
            }
        }
    }


    static void bfs(boolean[][] visited, char[][] map, int[] start) {
        Queue<int[]> q = new LinkedList<>();
        int[][] v = new int[map.length][map[0].length];
        q.add(start);
        visited[start[0]][start[1]] = true;
        Set<String> cache = new HashSet<>();
        while (!q.isEmpty()) {
/*            printVisited(visited);
            System.out.println();
            System.out.println();*/
            int[] current = q.poll();
            visited[current[0]][current[1]] = true;
            if (!cache.add(Arrays.toString(current))) continue;
            char c = map[current[0]][current[1]];
            if (isSplitting(c, current)) {
                if (current[2] == 0) {
                    int[] up = new int[]{current[0] - 1, current[1], -1, 0, current[0], current[1]};
                    int[] down = new int[]{current[0] + 1, current[1], 1, 0, current[0], current[1]};
                    if (canAdd(up, map)) {
                        visited[up[0]][up[1]] = true;
                        q.add(up);
                    }
                    if (canAdd(down, map)) {
                        visited[down[0]][down[1]] = true;
                        q.add(down);
                    }
                } else {
                    if (current[3] == 0) {
                        int[] left = new int[]{current[0], current[1] - 1, 0, -1, current[0], current[1]};
                        int[] right = new int[]{current[0], current[1] + 1, 0, 1, current[0], current[1]};
                        if (canAdd(left, map)) {
                            visited[left[0]][left[1]] = true;
                            q.add(left);
                        }
                        if (canAdd(right, map)) {
                            visited[right[0]][right[1]] = true;
                            q.add(right);
                        }
                    }
                }
            } else {
                int[] speed = calculateSpeed(c, current);
                int[] next = new int[]{speed[0] + current[0], speed[1] + current[1], speed[0], speed[1], current[0], current[1]};
                if (canAdd(next, map)) {
                    visited[next[0]][next[1]] = true;
                    q.add(next);
                }
            }
        }
    }

    static boolean isSpecialChar(int[] p, char[][] map) {
        char c = map[p[0]][p[1]];
        return (c == '|' || c =='-' || c == '/' || c =='\\');
    }

    static boolean isSplitting(char c, int[] current) {
        if (c == '|') {
            return current[2] == 0 && (current[3] == -1 || current[3] == 1);
        } else if (c == '-') {
            return (current[2] == 1 || current[2] == -1) && current[3] == 0;
        }
        return false;
    }

    static boolean canAdd(int[] point, char[][] map) {
        if (point[0] < 0 || point[0] >= map.length) return false;
        return point[1] >= 0 && point[1] < map[0].length;
    }

    static int[] calculateSpeed(char c, int[] currentBeam) {
        if (c == '/') {
            if (currentBeam[2] == 0) {
                if (currentBeam[3] == 1) {
                    return new int[]{-1 ,0};
                } else {
                    return new int[]{1 ,0};
                }
            } else {
                if (currentBeam[2] == 1) {
                    return new int[]{0 ,-1};
                } else {
                    return new int[]{0 ,1};
                }
            }
        } else if (c == '\\'){
            if (currentBeam[2] == 0) {
                if (currentBeam[3] == 1) {
                    return new int[]{1 ,0};
                } else {
                    return new int[]{-1 ,0};
                }
            } else {
                if (currentBeam[2] == 1) {
                    return new int[]{0 ,1};
                } else {
                    return new int[]{0 ,-1};
                }
            }
        }
        return new int[]{currentBeam[2], currentBeam[3]};
    }

    static long hash(String s) {
        long result = 0;
        for (var c : s.toCharArray()) {
            result += c;
            result *= 17;
            result %= 256;
        }
        return result;
    }


    static void print(char[][] arr) {
        for (var row : arr) {
            for (var c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

}
