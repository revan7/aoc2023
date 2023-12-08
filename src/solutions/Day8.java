package solutions;

import org.openjdk.jmh.annotations.Benchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Day8 {


    public static Long solvePart1(BufferedReader br) throws IOException {
        String pattern = br.readLine();
        br.readLine();
        String line = br.readLine();
        Map<String, ArrayList<String>> graph = new HashMap<>();
        String source = "";
        String destination = "";
        while (line != null) {
            String[] split = line.split(" = ");
            String node = split[0].trim();
            if (source.isEmpty()) {
                source = node;
            }
            destination = node;
            graph.put(node, new ArrayList<>());
            String[] directions = split[1].split(",");
            String left = directions[0].substring(1).trim();
            String right = directions[1].substring(0, 4).trim();
            ArrayList<String> edges = new ArrayList<>();
            edges.add(left); edges.add(right);
            graph.put(node, edges);
            line = br.readLine();
        }
        pattern = pattern.replace('R', '1').replace('L', '0');
        System.out.println("Pattern: " + pattern);
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        for (var entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        return part1(source, destination, graph, pattern);
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        return 0L;
    }

    static Long part2(Map<String, Integer> game) {
        return 0L;
    }

    static Long part1(String source, String destination, Map<String, ArrayList<String>> graph, String pattern) {
        Long steps = 0L;
        int directionIndex = 0;
        while (!source.equalsIgnoreCase(destination)) {
            ArrayList<String> edges = graph.get(source);
            String s = edges.get(Integer.parseInt(String.valueOf(pattern.charAt(directionIndex++))));
            source = s;
            directionIndex %= pattern.length();
            steps++;
        }
        return steps;
    }
}
