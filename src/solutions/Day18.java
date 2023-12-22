package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day18 {

    static Map<String, Integer[]> directions = new HashMap();

    public static long solvePart1(BufferedReader br) throws IOException {
        initMap();
        String line = br.readLine();
        List<Instruction> instructions = new LinkedList<>();
        while (line != null) {
            String[] split = line.split("\\s+");
            instructions.add(new Instruction(split[0], Integer.parseInt(split[1]), split[2]));
            line = br.readLine();
        }
        System.out.println(instructions);
        return part1(instructions);
    }

    static void initMap() {
        directions = new HashMap<>();
        directions.put("U", new Integer[]{-1, 0});
        directions.put("D", new Integer[]{1, 0});
        directions.put("R", new Integer[]{0, 1});
        directions.put("L", new Integer[]{0, -1});
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        initMap();
        String line = br.readLine();
        List<Instruction> instructions = new LinkedList<>();
        while (line != null) {
            String[] split = line.split("\\s+");
            instructions.add(new Instruction(getDirection(split), getLength(split), split[2]));
            line = br.readLine();
        }
        System.out.println(instructions);
        return part1(instructions);
    }

    static String getDirection(String[] in) {
        char last = in[2].charAt(7);
        if (last == '0') return "R";
        if (last == '1') return "D";
        if (last == '2') return "L";
        return "U";
    }

    static Integer getLength(String[] in) {
        return Integer.parseInt(in[2].trim().substring(2, 7), 16);
    }

    static long part1(List<Instruction> instructions) {
        initMap();
        long result = 0;
        Point origin = new Point(0,0);
        List<Point> points = new LinkedList<>();
        points.add(origin);
        for (var instruction : instructions) {
            int vx = instruction.length * directions.get(instruction.direction)[0];
            int vy = instruction.length * directions.get(instruction.direction)[1];
            Point next = new Point(origin.x + vx, origin.y + vy);
            points.add(next);
            origin = next;
        }
        System.out.println(points);
        long perimeter = getPerimeter(points);
        long area = getArea(points);
        result = perimeter / 2 + 1 + area;
        System.out.println(result);
        return result;
    }

    public static record Instruction(String direction, Integer length, String color){}

    public static record Point(Integer x, Integer y) {}

    public static long getArea(List<Point> points) {
        points.add(new Point(0,0));
        long result = 0L;
        for (int i = 1; i < points.size(); i ++) {
            Point p0 = points.get(i - 1);
            Point p1 = points.get(i);
            result += (((long) p0.x * p1.y) - ((long) p0.y * p1.x));
        }
        return Math.abs(result / 2);
    }

    public static long getPerimeter(List<Point> points) {
        long result = 0L;
        for (int i = 0; i < points.size(); i ++) {
            Point p0 = points.get(i);
            Point p1 = points.get((i + 1) % points.size());
            result += distance(p0, p1);
        }
        return result;
    }

    public static long distance(Point a, Point b) {
        return Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
    }

}
