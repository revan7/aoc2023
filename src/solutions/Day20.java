package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import static solutions.Day20.Modules.BROADCASTER;

public class Day20 {

    static Long highCount = 0L;
    static Long lowCount = 0L;

    public static long solvePart1(BufferedReader br) throws IOException {
        List<String> lines = new LinkedList<>();
        String line = br.readLine();
        Map<String,Module> modules = new HashMap<>();
        modules.put("output", new Output("output"));
        while (line != null) {
            lines.add(line);
            String[] split = line.split("->");
            String type = split[0].trim();
            if (Objects.equals(type, "broadcaster")) {
                modules.put(type, new Broadcaster("broadcaster"));
            }
            if (type.startsWith("%")) {
                String name = type.substring(1);
                modules.put(name, new FlipFlop(type.substring(1)));
            } else if (type.startsWith("&")) {
                String name = type.substring(1);
                modules.put(name, new Conjunction(type.substring(1)));
            }
            line = br.readLine();
        }
        for (var l : lines) {
            String[] split = l.split("->");
            String command = split[1].trim();
            String inputName = getName(l);
            Module inputModule = modules.get(inputName);
            String[] outputs = command.split(",");
            for (var n : outputs) {
                Module out = modules.get(n.trim());
                if (out == null) {
                    out = new Output(n.trim());
                    modules.put(n.trim(),out);
                }
                inputModule.getOutputs().add(out);
                if (out instanceof Conjunction) {
                    ((Conjunction) out).inputs.put(inputModule.getName(), 0);
                }
            }
        }/*
        for (var k : modules.entrySet()) {
            System.out.println(k);
        }*/
        return part1(modules.get(BROADCASTER.name));
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        List<String> lines = new LinkedList<>();
        String line = br.readLine();
        Map<String,Module> modules = new HashMap<>();
        modules.put("output", new Output("output"));
        while (line != null) {
            lines.add(line);
            String[] split = line.split("->");
            String type = split[0].trim();
            if (Objects.equals(type, "broadcaster")) {
                modules.put(type, new Broadcaster("broadcaster"));
            }
            if (type.startsWith("%")) {
                String name = type.substring(1);
                modules.put(name, new FlipFlop(type.substring(1)));
            } else if (type.startsWith("&")) {
                String name = type.substring(1);
                modules.put(name, new Conjunction(type.substring(1)));
            }
            line = br.readLine();
        }
        String activationNodeForRX = "";
        for (var l : lines) {
            String[] split = l.split("->");
            String command = split[1].trim();
            String inputName = getName(l);
            Module inputModule = modules.get(inputName);
            String[] outputs = command.split(",");
            for (var n : outputs) {
                Module out = modules.get(n.trim());
                if (out == null) {
                    out = new Output(n.trim());
                    modules.put(n.trim(),out);
                }
                if (n.trim().equals("rx")) activationNodeForRX = inputName;
                inputModule.getOutputs().add(out);
                if (out instanceof Conjunction) {
                    ((Conjunction) out).inputs.put(inputModule.getName(), 0);
                }
            }
        }

        /*
        for (var k : modules.entrySet()) {
            System.out.println(k);
        }*/
        return part2(modules.get(BROADCASTER.name), activationNodeForRX);
    }

    public static Long part1(Module broadcaster) {
        for (int i = 0; i < 1000; i ++) {
            Queue<Command> q = new LinkedList<>();
            q.add(new Command(null, broadcaster, 0));
            while (!q.isEmpty()) {
                Command poll = q.poll();
                if (poll.to == null) {
                    continue;
                }
                q.addAll(poll.to.receiveAndGetCommand(poll.from, poll.state));
            }
        }
        return lowCount * highCount;
    }
    private static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    public static Long part2(Module broadcaster, String activationNodeForRX) {
        Set<String> visited = new HashSet<>();
        Map<String, Integer> buttonPressesToNode = new HashMap<>();
        int buttonPresses = 0;
        while (visited.size() < 4){
            buttonPresses++;
            Queue<Command> q = new LinkedList<>();
            q.add(new Command(null, broadcaster, 0));
            while (!q.isEmpty()) {
                Command poll = q.poll();
                if (Objects.equals(poll.to.getName(), activationNodeForRX) && !visited.contains(poll.from.getName())) {
                    String key = poll.from.getName();
                    if (poll.state == 1) {
                        visited.add(key);
                        buttonPressesToNode.put(key, buttonPresses);
                    }
                }
                q.addAll(poll.to.receiveAndGetCommand(poll.from, poll.state));
            }
        }
        long result = 1;
        for (var n : buttonPressesToNode.values()) {
            result = lcm(result, n);
        }
        return result;
    }

    static String getName(String s) {
        String[] split = s.split("->");
        String type = split[0].trim();
        if (getType(s) == BROADCASTER) return BROADCASTER.name;
        return type.substring(1);
    }

    static Modules getType(String s) {
        String[] split = s.split("->");
        String type = split[0].trim();
        if (type.equalsIgnoreCase("broadcaster")) return BROADCASTER;
        if (type.startsWith("%")) return Modules.FLIP_FLOP;
        return Modules.CONJUNCTION;
    }

    static long part1() {
        long result = 0;

        return result;
    }

    static enum Modules {
        FLIP_FLOP("flipflop"),
        CONJUNCTION("conjunction"),
        BROADCASTER("broadcaster");
        public final String name;

        private Modules(String name) {this.name = name;}
    }

    public static record Command(Module from, Module to, int state) {}

    public static interface Module {

        String getName();

        void receive(Module module, int signal);

        List<Command> receiveAndGetCommand(Module module, int signal);

        List<Module> getOutputs();
        List<Module> getInputs();
    }

    public static class FlipFlop implements Module {

        String name;

        int state = 0;

        List<Module> outputs = new LinkedList<>();
        public FlipFlop(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<Module> getOutputs() {
            return outputs;
        }

        @Override
        public List<Module> getInputs() {
            return null;
        }

        @Override
        public void receive(Module module, int signal) {
            if (signal == 0) {
                state = state == 0 ? 1 : 0;
                if (state == 0) {
                    for (var m : outputs) {
                        m.receive(this, 1);
                    }
                }
            }
        }

        @Override
        public List<Command> receiveAndGetCommand(Module module, int signal) {
            ////System.out.println(module + " sent signal " + signal + " to " + this);
            if (signal == 0) lowCount++;
            if (signal == 1) highCount++;
            List<Command> next = new LinkedList<>();
            if (signal == 0) {
                state = state == 0 ? 1 : 0;
                int propagatedState = state == 1 ? 1 : 0;
                for (var m : outputs) {
                    next.add(new Command(this, m, propagatedState));
                }
            }
            return next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FlipFlop flipFlop = (FlipFlop) o;
            return state == flipFlop.state && Objects.equals(name, flipFlop.name) && Objects.equals(outputs, flipFlop.outputs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, state, outputs);
        }

        @Override
        public String toString() {
            return "FlipFlop{" +
                    "name='" + name + '}';
        }
    }

    public static class Conjunction implements Module {

        @Override
        public String getName() {
            return name;
        }

        String name;

        Map<String, Integer> inputs = new HashMap<>();

        List<Module> outputs = new LinkedList<>();
        public Conjunction(String name) {
            this.name = name;
        }

        @Override
        public void receive(Module module, int signal) {

        }

        @Override
        public List<Command> receiveAndGetCommand(Module module, int signal) {
            //System.out.println(module + " sent signal " + signal + " to " + this);
            if (signal == 0) lowCount++;
            if (signal == 1) highCount++;
            List<Command> next = new LinkedList<>();
            inputs.put(module.getName(), signal);
            boolean highPulse = true;
            for (var entry : inputs.entrySet()) {
                if (highPulse && entry.getValue() == 0) highPulse = false;
            }
            int propagatedState = highPulse ? 0 : 1;
            for (var m : outputs) {
                next.add(new Command(this, m, propagatedState));
            }
            return next;
        }


        @Override
        public List<Module> getOutputs() {
            return outputs;
        }

        @Override
        public List<Module> getInputs() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Conjunction that = (Conjunction) o;
            return Objects.equals(name, that.name) && Objects.equals(inputs, that.inputs) && Objects.equals(outputs, that.outputs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "Conjunction{" +
                    "name='" + name +"}";
        }
    }

    public static class Broadcaster implements Module {

        String name;

        List<Module> outputs = new LinkedList<>();

        public Broadcaster(String name) {
            this.name = name;
        }

        @Override
        public void receive(Module module, int signal) {

        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<Command> receiveAndGetCommand(Module module, int signal) {
            //System.out.println(module + " sent signal " + signal + " to " + this);
            if (signal == 0) lowCount++;
            if (signal == 1) highCount++;
            List<Command> next = new LinkedList<>();
            for (var m : outputs) {
                next.add(new Command(this, m, signal));
            }
            return next;
        }

        @Override
        public List<Module> getOutputs() {
            return outputs;
        }

        @Override
        public List<Module> getInputs() {
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Broadcaster that = (Broadcaster) o;
            return Objects.equals(name, that.name) && Objects.equals(outputs, that.outputs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, outputs);
        }

        @Override
        public String toString() {
            return "Broadcaster{" +
                    "name='" + name + '}';
        }
    }
    public static class Output implements Module {

        String name;

        public Output(String name) {
            this.name = name;
        }

        @Override
        public void receive(Module module, int signal) {

        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<Module> getOutputs() {
            return null;
        }

        @Override
        public List<Module> getInputs() {
            return null;
        }

        @Override
        public List<Command> receiveAndGetCommand(Module module, int signal) {
            //System.out.println(module + " sent signal " + signal + " to " + this);
            if (signal == 0) lowCount++;
            if (signal == 1) highCount++;
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "Output{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
