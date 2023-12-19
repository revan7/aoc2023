package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Day19 {

    static Map<String, Integer[]> directions = new HashMap();

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, Workflow> workflowMap = new HashMap<>();
        LinkedList<Part> parts = new LinkedList<>();
        while (line != null && !line.isBlank()) {
            processWorkflow(line, workflowMap);
            line = br.readLine();
        }
        line = br.readLine();
        while (line != null) {
            processPart(line, parts);
            line = br.readLine();
        }
        return part1(workflowMap, parts);
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, Workflow> workflowMap = new HashMap<>();
        while (line != null && !line.isBlank()) {
            processWorkflow(line, workflowMap);
            line = br.readLine();
        }
        return part2(workflowMap);
    }

    static long part1(Map<String, Workflow> workflowMap, List<Part> parts) {
        long result = 0;
        LinkedList<Part> accepted = new LinkedList<>();
        for (var p : parts) {
            String destination = workflowMap.get("in").traverse(p);
            while (!destination.equalsIgnoreCase("A") && !destination.equalsIgnoreCase("R")) {
                destination = workflowMap.get(destination).traverse(p);
            }
            if (destination.equalsIgnoreCase("A")) accepted.add(p);
        }
        for (var p : accepted) {
            result += p.x.value;
            result += p.m.value;
            result += p.a.value;
            result += p.s.value;
        }
        return result;
    }
    static long part2(Map<String, Workflow> workflowMap) {
        long result = 0;
        LinkedList<RangePart> accepted = new LinkedList<>();
        RangePart start = new RangePart(
                new RangeRating("x", 1L, 4000L),
                new RangeRating("m", 1L, 4000L),
                new RangeRating("a", 1L, 4000L),
                new RangeRating("s", 1L, 4000L)
        );
        Workflow in = workflowMap.get("in");
        workflowMap.put("A", new Workflow("A", null));
        workflowMap.put("R", new Workflow("R", null));
        in.traverseRange("in", start, workflowMap, accepted);
        Set<String> duplicate = new HashSet<>();
        for (var a : accepted) {
            if (!duplicate.add(a.toString())) continue;
            result += (a.getRange("x") * a.getRange("m") * a.getRange("a") * a.getRange("s"));
            //result += (a.x.max * a.s.max * a.m.max * a.a.max);
            //result += (a.x.min * a.s.min * a.m.min * a.a.min);
        }
        return result;
    }

    static void processWorkflow(String line, Map<String, Workflow> workflowMap) {
        int i = line.indexOf("{");
        String name = line.substring(0, i);
        line = line.substring(i + 1, line.length() - 1);
        String[] split = line.split(",");
        LinkedList<Rule> rules = new LinkedList<>();
        Workflow toAdd = new Workflow(name, rules);
        for (var s : split) {
            if (s.contains(":")) {
                String[] rule = s.split(":");
                rules.addLast(new Rule(rule[0], rule[1]));
            } else {
                rules.addLast(new Rule(null, s));
            }
        }
        workflowMap.put(name, toAdd);
    }


    static void processPart(String line, LinkedList<Part> parts) {
        line = line.substring(1, line.length() - 1);
        String[] split = line.split(",");
        String[] xs = split[0].split("=");
        String[] ms = split[1].split("=");
        String[] as = split[2].split("=");
        String[] ss = split[3].split("=");
        parts.add(new Part(
                new Rating(
                        xs[0],
                        Long.parseLong(xs[1])
                ),
                new Rating(
                        ms[0],
                        Long.parseLong(ms[1])
                ),
                new Rating(
                        as[0],
                        Long.parseLong(as[1])
                ),
                new Rating(
                        ss[0],
                        Long.parseLong(ss[1])
                )
        ));
    }

    static void initMap() {
        directions = new HashMap<>();
        directions.put("U", new Integer[]{-1, 0});
        directions.put("D", new Integer[]{1, 0});
        directions.put("R", new Integer[]{0, 1});
        directions.put("L", new Integer[]{0, -1});
    }


    public static record Workflow(String name, LinkedList<Rule> rules){
        String traverse(Part a) {
            for (int i = 0; i < rules.size(); i ++) {
                var rule = rules.get(i);
                if (a.rate(rule)) {
                    return rule.destination;
                }
            }
            return "";
        }

        void traverseRange(String node, RangePart part, Map<String, Workflow> workflowMap, LinkedList<RangePart> accepted) {
            System.out.println("Traversing: " + part + " node:" + node);
            if (node.equalsIgnoreCase("R") || part.isInvalid()) {
                System.out.println("Rejected");
                return;
            }
            if (node.equalsIgnoreCase("A")) {
                System.out.println("Accepted");
                accepted.add(part);
                return;
            }
            for (var rule : workflowMap.get(name).rules) {
                if (rule.condition == null) {
                    workflowMap.get(rule.destination).traverseRange(rule.destination, part, workflowMap, accepted);
                } else {
                    int i = rule.condition.contains("<") ? rule.condition.indexOf("<") : rule.condition.indexOf(">");
                    String name = rule.condition.substring(0, i);
                    Long value = Long.parseLong(rule.condition.substring(i + 1));
                    RangePart greater = part.getCopyWithGreater(name, value - 1);
                    RangePart lesser = part.getCopyWithLesser(name, value + 1);
                    if (greater.rate(rule)) {
                        workflowMap.get(rule.destination).traverseRange(rule.destination, greater, workflowMap, accepted);
                    } else {
                        part = greater;
                    }
                    if (lesser.rate(rule)) {
                        workflowMap.get(rule.destination).traverseRange(rule.destination, lesser, workflowMap, accepted);
                    } else {
                        part = lesser;
                    }
                }
            }
        }

    }

    public static record Part(Rating x, Rating m, Rating a, Rating s) {
        boolean rate(Rule rule) {
            if (rule.condition == null) return true;
            if (rule.condition.contains(">")) {
                String[] split = rule.condition.split(">");
                String name = split[0].trim();
                Long value = Long.parseLong(split[1]);
                if (name.equals(x.name)) return x.value > value;
                if (name.equals(m.name)) return m.value > value;
                if (name.equals(a.name)) return a.value > value;
                if (name.equals(s.name)) return s.value > value;
            }
            String[] split = rule.condition.split("<");
            String name = split[0].trim();
            Long value = Long.parseLong(split[1]);
            if (name.equals(x.name)) return x.value < value;
            if (name.equals(m.name)) return m.value < value;
            if (name.equals(a.name)) return a.value < value;
            return s.value < value;
        }

        public Rating getBy(String name) {
            if (name.equals("x")) return x;
            if (name.equals("m")) return m;
            if (name.equals("a")) return a;
            return s;
        }

        public Part copy(Rating newRating) {
            return switch (newRating.name) {
                case "x" -> new Part(
                        new Rating(newRating.name, newRating.value),
                        new Rating(m.name, m.value),
                        new Rating(a.name, a.value),
                        new Rating(s.name, s.value)
                );
                case "m" -> new Part(
                        new Rating(x.name, x.value),
                        new Rating(newRating.name, newRating.value),
                        new Rating(a.name, a.value),
                        new Rating(s.name, s.value)
                );
                case "a" -> new Part(
                        new Rating(x.name, x.value),
                        new Rating(m.name, m.value),
                        new Rating(newRating.name, newRating.value),
                        new Rating(s.name, s.value)
                );
                case "s" -> new Part(
                        new Rating(x.name, x.value),
                        new Rating(m.name, m.value),
                        new Rating(a.name, a.value),
                        new Rating(newRating.name, newRating.value)
                );
                default -> throw new IllegalStateException("Unexpected value: " + newRating.name);
            };
        }
    }
    public static record RangePart(RangeRating x, RangeRating m, RangeRating a, RangeRating s) {

        @Override
        public String toString() {
            return "x:" + "(" + x.min + "," + x.max + ") " + " m:(" + m.min + "," + m.max + ")"
                    + " a:(" + a.min + "," + a.max + ")"+ " s:(" + s.min + "," + s.max + ")" ;
        }

        boolean isInvalid() {
            return x.max < x.min || m.max < m.min || a.max < a.min || s.max < s.min;
        }

        boolean rate(Rule rule) {
            if (rule.condition == null) return true;
            if (rule.condition.contains(">")) {
                String[] split = rule.condition.split(">");
                String name = split[0].trim();
                Long value = Long.parseLong(split[1]);
                if (name.equals(x.name)) return x.min > value;
                if (name.equals(m.name)) return m.min > value;
                if (name.equals(a.name)) return a.min > value;
                if (name.equals(s.name)) return s.min > value;
            }
            String[] split = rule.condition.split("<");
            String name = split[0].trim();
            Long value = Long.parseLong(split[1]);
            if (name.equals(x.name)) return x.max < value;
            if (name.equals(m.name)) return m.max < value;
            if (name.equals(a.name)) return a.max < value;
            return s.max < value;
        }

        long getRange(String name) {
            if (name.equals(x.name)) return x.max - x.min;
            if (name.equals(m.name)) return m.max - m.min;
            if (name.equals(a.name)) return a.max - a.min;
            return s.max - s.min;
        }

        RangePart getCopyWithGreater(String name, Long value) {
            return switch (name) {
                case "x" -> new RangePart(
                        new RangeRating(x.name, x.min, Math.min(x.max, value)),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "m" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, m.min, Math.min(m.max, value)),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "a" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name, a.min, Math.min(a.max, value)),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "s" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name, s.min, Math.min(s.max, value))
                );
                default -> throw new IllegalStateException("Unexpected value: " + name);
            };

        }

        RangePart getCopyWithLesser(String name, Long value) {
            return switch (name) {
                case "x" -> new RangePart(
                        new RangeRating(x.name,Math.max(x.min, value), x.max),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "m" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, Math.max(m.min, value), m.max),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "a" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name,Math.max(a.min, value), a.max),
                        new RangeRating(s.name, s.min, s.max)
                );
                case "s" -> new RangePart(
                        new RangeRating(x.name, x.min, x.max),
                        new RangeRating(m.name, m.min, m.max),
                        new RangeRating(a.name, a.min, a.max),
                        new RangeRating(s.name,Math.max(s.min, value), s.max)
                );
                default -> throw new IllegalStateException("Unexpected value: " + name);
            };

        }

    }

    public static record Rating(String name, Long value) {}
    public static record RangeRating(String name, Long min, Long max) {}

    public static record Rule(String condition, String destination) {}


}
