package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Day6 {

    public static Long solvePart1(BufferedReader br) throws IOException {
        String timesLine = br.readLine();
        String distancesLine = br.readLine();
        Long[] times = Arrays.stream(timesLine.split(":")[1].trim().split("\\s+")).map(s -> Long.parseLong(s.trim())).toArray(Long[]::new);
        Long[] distances = Arrays.stream(distancesLine.split(":")[1].trim().split("\\s+")).map(s -> Long.parseLong(s.trim())).toArray(Long[]::new);
        return part1(times, distances);
    }


    public static Long solvePart2(BufferedReader br) throws IOException {
        String timesLine = br.readLine();
        String distancesLine = br.readLine();
        Long time = Long.parseLong(timesLine.replaceAll("[^0-9]", ""));
        Long distance = Long.parseLong(distancesLine.replaceAll("[^0-9]", ""));
        System.out.println("Time : " + time + " distance " + distance);
        return calc(time, distance);
    }

    static Long part2(Long[] times, Long[] distances) {
        Long result = 1L;
        for (int i = 0; i < times.length; i++) {
            result *= calc(times[i], distances[i]);
        }
        return result;
    }

    static Long part1(Long[] times, Long[] distances) {
        Long result = 1L;
        for (int i = 0; i < times.length; i++) {
            result *= calc(times[i], distances[i]);
        }
        return result;
    }

    private static Long calc(Long time, Long distance) {
        Long solutions = 0L;
        for (int i = 1; i < time - 1; i ++) {
            if ((time - i) * i > distance) solutions++;
        }
        return solutions;
    }

}
