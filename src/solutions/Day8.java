package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day8 {


    @Deprecated
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
            edges.add(left);
            edges.add(right);
            graph.put(node, edges);
            line = br.readLine();
        }
        pattern = pattern.replace('R', '1').replace('L', '0');
        for (var entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        return part1(source, destination, graph, pattern);
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        String pattern = br.readLine();
        br.readLine();
        String line = br.readLine();
        Map<String, Node> graph = new HashMap<>();
        Node firstNode = null;
        Node lastNode = null;
        while (line != null) {
            String[] split = line.split(" = ");
            String[] lr = split[1].split(", ");
            String nodeName = split[0].trim();
            String left = lr[0].trim().substring(1);
            String right = lr[1].trim().substring(0, 3);
            Node leftNode = graph.getOrDefault(left, new Node(left));
            Node rightNode = graph.getOrDefault(right, new Node(right));
            graph.put(left, leftNode);
            graph.put(right, rightNode);
            Node node = graph.getOrDefault(nodeName, new Node(nodeName));
            node.left = leftNode;
            node.right = rightNode;
            graph.put(nodeName, node);
            if (firstNode == null) {
                firstNode = node;
            }
            lastNode = node;
            line = br.readLine();
        }
        for (var entry : graph.values()) {
            entry.left = graph.get(entry.left.name);
            entry.right = graph.get(entry.right.name);
        }
        List<Node> sources = new ArrayList<>();
        for (var name : graph.keySet()) {
            if (name.endsWith("A"))
            sources.add(graph.get(name));
        }
        return part2_fst(sources, pattern);
    }

    static Long part2(List<Node> sources, String pattern) {
        Long steps = 0L;
        int patternSize = pattern.length();
        int patternIndex = 0;
        boolean foundAll = false;
        while (!foundAll) {
            foundAll = true;
            char direction = pattern.charAt(patternIndex);
            List<Node> newSources = new LinkedList<>();
            for (Node curr : sources) {
                if (direction == 'L') {
                    curr = curr.left;
                } else curr = curr.right;
                if (!curr.name.endsWith("Z")) foundAll = false;
                newSources.add(curr);
            }
            sources = newSources;
            patternIndex++;
            patternIndex %= patternSize;
            steps++;
        }
        return steps;
    }

    static Long part2_fst(List<Node> sources, String pattern) {
        List<Long> results = new ArrayList<>();
        for (Node curr : sources) {
            results.add(part2_exitEndsWithZ(curr, pattern));
        }
        return lcmOfArray(results);
    }

    // Method to find the GCD of two numbers
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Method to find the LCM of two numbers
    private static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    // Method to find the LCM of an array of Long values
    public static long lcmOfArray(List<Long> array) {
        long result = 1;
        for (Long number : array) {
            result = lcm(result, number);
        }
        return result;
    }

    @Deprecated
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

    public static Long solvePart1n(BufferedReader br) throws IOException {
        String pattern = br.readLine();
        br.readLine();
        String line = br.readLine();
        Map<String, Node> graph = new HashMap<>();
        Node firstNode = null;
        Node lastNode = null;
        while (line != null) {
            String[] split = line.split(" = ");
            String[] lr = split[1].split(", ");
            String nodeName = split[0].trim();
            String left = lr[0].trim().substring(1);
            String right = lr[1].trim().substring(0, 3);
            Node leftNode = graph.getOrDefault(left, new Node(left));
            Node rightNode = graph.getOrDefault(right, new Node(right));
            graph.put(left, leftNode);
            graph.put(right, rightNode);
            Node node = graph.getOrDefault(nodeName, new Node(nodeName));
            node.left = leftNode;
            node.right = rightNode;
            graph.put(nodeName, node);
            if (firstNode == null) {
                firstNode = node;
            }
            lastNode = node;
            line = br.readLine();
        }
        for (var entry : graph.values()) {
            entry.left = graph.get(entry.left.name);
            entry.right = graph.get(entry.right.name);
        }
        return part1n(graph.get("AAA"), graph.get("ZZZ"), pattern);
    }

    static Long part1n(Node start, Node destination, String pattern) {
        Node curr = start;
        Long steps = 0L;
        int patternSize = pattern.length();
        int patternIndex = 0;
        Map<Node, Integer> visited = new HashMap<>();
        while (curr != destination) {
            char direction = pattern.charAt(patternIndex);
            Integer orDefault = visited.getOrDefault(curr, 0);
            visited.put(curr, orDefault + 1);
            if (direction == 'L') {
                curr = curr.left;
            } else curr = curr.right;
            patternIndex++;
            patternIndex %= patternSize;
            steps++;
        }
        return steps;
    }

    static Long part2_exitEndsWithZ(Node start, String pattern) {
        Node curr = start;
        Long steps = 0L;
        int patternSize = pattern.length();
        int patternIndex = 0;
        Map<Node, Integer> visited = new HashMap<>();
        while (!curr.name.endsWith("Z")) {
            char direction = pattern.charAt(patternIndex);
            Integer orDefault = visited.getOrDefault(curr, 0);
            visited.put(curr, orDefault + 1);
            if (direction == 'L') {
                curr = curr.left;
            } else curr = curr.right;
            patternIndex++;
            patternIndex %= patternSize;
            steps++;
        }
        return steps;
    }

    public static class Node {
        Node left, right;
        String name;

        public Node(Node left, Node right, String name) {
            this.left = left;
            this.right = right;
            this.name = name;
        }

        public Node(String name) {
            this.name = name;
        }
    }
}

