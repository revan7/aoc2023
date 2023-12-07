package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day4 {

    public static int solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> m = new ArrayList<>();
        int sum = 0;
        while (line != null) {
            m.add(line);
            sum += part1(line);
            line = br.readLine();
        }
        return sum;
    }

    public static int solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> m = new ArrayList<>();
        List<Set<Integer>> numberEntries = new LinkedList<>();
        List<Set<Integer>> winningNumberEntries = new LinkedList<>();
        while (line != null) {
            Set<Integer> numbers = new HashSet<>();
            Set<Integer> winningNumbers = new HashSet<>();
            String[] entry = line.split(":")[1].trim().split("\\|");
            for (var s : entry[1].trim().split("\\s+")) {
                Integer number = Integer.parseInt(s.trim());
                winningNumbers.add(number);
            }
            for (var s : entry[0].trim().split("\\s+")) {
                Integer number = Integer.parseInt(s.trim());
                numbers.add(number);
            }
            numberEntries.add(numbers);
            winningNumberEntries.add(winningNumbers);
            line = br.readLine();
        }
        return part2(numberEntries, winningNumberEntries);
    }


    public static int solvePart2_with_logs(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<String> m = new ArrayList<>();
        List<Set<Integer>> numberEntries = new LinkedList<>();
        List<Set<Integer>> winningNumberEntries = new LinkedList<>();
        while (line != null) {
            Set<Integer> numbers = new HashSet<>();
            Set<Integer> winningNumbers = new HashSet<>();
            String[] entry = line.split(":")[1].trim().split("\\|");
            for (var s : entry[1].trim().split("\\s+")) {
                Integer number = Integer.parseInt(s.trim());
                winningNumbers.add(number);
            }
            for (var s : entry[0].trim().split("\\s+")) {
                Integer number = Integer.parseInt(s.trim());
                numbers.add(number);
            }
            numberEntries.add(numbers);
            winningNumberEntries.add(winningNumbers);
            line = br.readLine();
        }
        return part2_with_logs(numberEntries, winningNumberEntries);
    }

    static int part1(String line) {
        String[] entry = line.split(":")[1].trim().split("\\|");
        Set<Integer> winningNumbers = new HashSet<>();
        for (var s : entry[1].trim().split("\\s+")) {
            Integer number = Integer.parseInt(s.trim());
            winningNumbers.add(number);
        }
        int points = 0;
        for (var s : entry[0].trim().split("\\s+")) {
            Integer number = Integer.parseInt(s.trim());
            if (winningNumbers.contains(number)) {
                if (points == 0 ) points = 1;
                else points *= 2;
            }
        }
        return points;
    }

    static int part2_with_logs(List<Set<Integer>> numberEntries, List<Set<Integer>> winningNumberEntries) {
        int[] solutions = new int[numberEntries.size()];
        Arrays.fill(solutions, 1);
        for (int i = 0; i < numberEntries.size(); i++) {
            Set<Integer> numbers = numberEntries.get(i);
            Set<Integer> winningNumbers = winningNumberEntries.get(i);
            System.out.println("Analysing Card " + (i + 1));
            System.out.println(numbers);
            System.out.println(winningNumbers);
            int j = i;
            for (var n : numbers) {
                if (winningNumbers.contains(n)) {
                    System.out.println("Winning numbers contains " + n);
                    j++;
                }
            }
            System.out.println("Number of winnings for card: " + (j - i));
            for (int l = i + 1; l <= j; l++){
                System.out.println("Adding point to index " + (l + 1));
                solutions[l]++;
            }
            for (int instances = 1; instances < solutions[i]; instances++){
                System.out.println("Repeating for extra instances " + (instances - 1));
                for (int l = i + 1; l <= j; l++){
                    System.out.println("Adding point to index " + (l + 1));
                    solutions[l]++;
                }
            }
        }
        int sum = 0;
        for (int n : solutions) sum += n;
        return sum;
    }

    static int part2(List<Set<Integer>> numberEntries, List<Set<Integer>> winningNumberEntries) {
        int[] solutions = new int[numberEntries.size()];
        Arrays.fill(solutions, 1);
        for (int i = 0; i < numberEntries.size(); i++) {
            Set<Integer> numbers = numberEntries.get(i);
            Set<Integer> winningNumbers = winningNumberEntries.get(i);
            int j = i;
            for (var n : numbers) {
                if (winningNumbers.contains(n)) {
                    j++;
                }
            }
            for (int l = i + 1; l <= j; l++){
                solutions[l]++;
            }
            for (int instances = 1; instances < solutions[i]; instances++){
                for (int l = i + 1; l <= j; l++){
                    solutions[l]++;
                }
            }
        }
        int sum = 0;
        for (int n : solutions) sum += n;
        return sum;
    }
}
