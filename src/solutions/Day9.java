package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day9 {

    public static Long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<Long>> history = new LinkedList<>();
        while (line != null) {
            List<Long> historyLine = Arrays.stream(line.split(" ")).map(s-> Long.parseLong(s.trim())).toList();
            history.add(historyLine);
            line = br.readLine();
        }
        return part1(history);
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<List<Long>> history = new LinkedList<>();
        while (line != null) {
            List<Long> historyLine = Arrays.stream(line.split(" ")).map(s-> Long.parseLong(s.trim())).toList();
            history.add(historyLine);
            line = br.readLine();
        }
        return part2(history);
    }

    public static Long part1(List<List<Long>> history) {
        long result = 0L;
        for (var historyLine : history) {
            result += process(historyLine);
        }
        return result;
    }

    public static Long part2(List<List<Long>> history) {
        long result = 0L;
        for (var historyLine : history) {
            result += process_part2(historyLine);
        }
        return result;
    }

    private static Long processHistoryLine(List<Long> historyLine) {
        System.out.println("Analysing history: " + historyLine);
        long deltaA = historyLine.get(1) - historyLine.get(0);
        int size = historyLine.size();
        long deltaB = historyLine.get(size - 1) - historyLine.get(size - 2);
        if (deltaB == deltaA) {
            System.out.println("Returning: " + deltaB);
            return deltaB + historyLine.get(size - 1);
        }
        List<Long> historyDelta = new ArrayList<>();
        for (int i = 1; i < historyLine.size(); i ++) {
            historyDelta.add(historyLine.get(i) - historyLine.get(i - 1));
        }
        Long delta = historyLine.get(size - 1) + processHistoryLine(historyDelta);
        System.out.println("Returning: " + delta);
        return delta;
    }

    public static Long process(List<Long> historyLine) {
        //System.out.println("Analysing history: " + historyLine);
        int size = historyLine.size();
        List<Long> historyDelta = new ArrayList<>();
        boolean allZeroes = true;
        for (int i = 1; i < historyLine.size(); i ++) {
            historyDelta.add(historyLine.get(i) - historyLine.get(i - 1));
            if (historyDelta.get(i - 1) != 0) allZeroes = false;
        }
        if (allZeroes) return historyLine.get(size - 1);
        return process(historyDelta) + historyLine.get(size - 1);
    }


    public static Long process_part2(List<Long> historyLine) {
        //System.out.println("Analysing history: " + historyLine);
        int size = historyLine.size();
        List<Long> historyDelta = new ArrayList<>();
        boolean allZeroes = true;
        for (int i = 1; i < historyLine.size(); i ++) {
            historyDelta.add(historyLine.get(i) - historyLine.get(i - 1));
            if (historyDelta.get(i - 1) != 0) allZeroes = false;
        }
        if (allZeroes) return historyLine.get(size - 1);
        return historyLine.get(0) - process_part2(historyDelta);
    }

}
