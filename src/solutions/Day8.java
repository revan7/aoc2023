package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day8 {

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
        return part1n(firstNode, lastNode, pattern);
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

    /**
     * q1 = BBB AAA AAA BBB AAA BBB ZZZ
     */

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


