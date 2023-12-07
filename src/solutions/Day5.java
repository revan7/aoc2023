package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Day5 {

    public static int solvePart1_no(BufferedReader br) throws IOException {
        String line = br.readLine();
        List<Integer> seeds = Arrays.stream(line.split(": ")[1].split(" ")).map(s -> Integer.parseInt(s.trim())).toList();
        line = br.readLine();
        int[][] map = new int[7][100];
        int i = 0;
        while (line != null) {
            if (line.endsWith("map:")) {
                line = br.readLine();
                while (line != null && !line.isEmpty()) {
                    List<Integer> list = Arrays.stream(line.split(" ")).map(String::trim).map(Integer::parseInt).toList();
                    int source = list.get(1);
                    int destination = list.get(0);
                    int range = list.get(2);
                    for (int j = 0; j < range; j++) {
                        map[i][source + j] = destination + j;
                    }
                    line = br.readLine();
                }
                i++;
            }
            line = br.readLine();
        }
        return part1_no(map, seeds);
    }

    public static long solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        int lineCount = 1;
        List<Long> seeds = Arrays.stream(line.split(": ")[1].split(" ")).map(s -> Long.parseLong(s.trim())).toList();
        line = br.readLine();
        lineCount++;
        List<List<List<Long>>> maps = new LinkedList<>();
        while (line != null) {
            if (line.endsWith("map:")) {
                List<List<Long>> map = new ArrayList<>();
                line = br.readLine();
                lineCount++;
                while (line != null && !line.isEmpty()) {
                    List<Long> mapLine = Arrays.stream(line.split(" ")).map(String::trim).map(Long::parseLong).toList();
                    line = br.readLine();
                    map.add(mapLine);
                    lineCount++;
                }
                maps.add(map);
            }
            line = br.readLine();
            lineCount++;
        }
        System.out.println("Lines counted " + lineCount);
        return part1(maps, seeds);
    }

    public static long solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        int lineCount = 1;
        List<Long> seeds = Arrays.stream(line.split(": ")[1].split(" ")).map(s -> Long.parseLong(s.trim())).toList();
        line = br.readLine();
        lineCount++;
        List<List<List<Long>>> maps = new LinkedList<>();
        while (line != null) {
            if (line.endsWith("map:")) {
                List<List<Long>> map = new ArrayList<>();
                line = br.readLine();
                lineCount++;
                while (line != null && !line.isEmpty()) {
                    List<Long> mapLine = Arrays.stream(line.split(" ")).map(String::trim).map(Long::parseLong).toList();
                    line = br.readLine();
                    map.add(mapLine);
                    lineCount++;
                }
                maps.add(map);
            }
            line = br.readLine();
            lineCount++;
        }
        System.out.println("Lines counted " + lineCount);
        return part2(maps, seeds);
    }


    public static long solvePart2_sorting(BufferedReader br) throws IOException {
        String line = br.readLine();
        int lineCount = 1;
        List<Long> seeds = Arrays.stream(line.split(": ")[1].split(" ")).map(s -> Long.parseLong(s.trim())).toList();
        line = br.readLine();
        lineCount++;
        List<List<Long>> maps = new LinkedList<>();
        while (line != null) {
            if (line.endsWith("map:")) {
                List<Long> map = new ArrayList<>();
                line = br.readLine();
                lineCount++;
                while (line != null && !line.isEmpty()) {
                    String[] split = line.split(" ");
                    line = br.readLine();
                    map.add(Long.parseLong(split[1].trim()));
                    map.add(Long.parseLong(split[0].trim()) + Long.parseLong(split[2].trim()) - 1);
                    lineCount++;
                }
                map.sort(Comparator.naturalOrder());
                maps.add(map);
            }
            line = br.readLine();
            lineCount++;
        }
        System.out.println("Lines counted " + lineCount);
        return part2_sorting(maps, seeds);
    }

    public static long solvePart2_pq(BufferedReader br) throws IOException {
        String line = br.readLine();
        int lineCount = 1;
        List<Long> seeds = Arrays.stream(line.split(": ")[1].split(" ")).map(s -> Long.parseLong(s.trim())).toList();
        line = br.readLine();
        var pqSeeds = new PriorityQueue<Long[]>(Comparator.comparingLong(a -> a[0]));
        for (int i = 0; i < seeds.size() - 1; i += 2) {
            Long first = seeds.get(i);
            Long second = seeds.get(i + 1);
            Long[] toAdd = new Long[]{first, first + second - 1};
            pqSeeds.add(toAdd);
        }
        lineCount++;
        List<List<Long[]>> almanac = new LinkedList<>();
        while (line != null) {
            if (line.endsWith("map:")) {
                LinkedList<Long[]> map = new LinkedList<>();
                line = br.readLine();
                lineCount++;
                while (line != null && !line.isEmpty()) {
                    String[] split = line.split(" ");
                    Long destination = Long.parseLong(split[0].trim());
                    Long source = Long.parseLong(split[1].trim());
                    Long range = Long.parseLong(split[2].trim());
                    map.add(new Long[]{source, source + range - 1, destination, destination + range - 1});
                    line = br.readLine();
                    lineCount++;
                }
                map.sort(Comparator.comparingLong((a) -> a[0]));
                almanac.add(map.stream().toList());
            }
            line = br.readLine();
            lineCount++;
        }
/*        for (var map : almanac) {
            System.out.println("Map: ");
            for (int i = 0; i < map.size(); i++){
                Long[] poll = map.get(i);
                System.out.println(poll[0] + " " + poll[1] + " " + poll[2] + " " + poll[3]);
            }
        }*/
        System.out.println("Lines counted " + lineCount);
        return part2_bs(almanac, pqSeeds.stream().toList());
    }

    static long part2_bs(List<List<Long[]>> almanac, List<Long[]> seeds) {
        long min = Long.MAX_VALUE;
        for (var seedRange : seeds) {
            for (long seed = seedRange[0]; seed <= seedRange[1]; seed++) {
                long source = seed;
                for (var map : almanac) {
                    source = bs(map, source);
                }
                min = Math.min(min, source);
            }
        }
        return min;
    }

    static long bs(List<Long[]> map, long target) {
        int s = 0, e = map.size() - 1;
        while (s <= e) {
            int m = (e - s) / 2 + s;
            Long[] entry = map.get(m);
            Long start = entry[0];
            Long end = entry[1];
            if (target >= start && target <= end) {
                Long offset = target - start;
                return entry[2] + offset;
            } else if (target < start) {
                e = m - 1;
            } else {
                s = m + 1;
            }
        }
        return target;
    }

    static long part2_rev(List<List<Long[]>> almanac, List<Long[]> seeds) {
        var map = almanac.get(almanac.size() - 1);
        for (int i = 0; i < map.size(); i++) {
            Long[] curr = map.get(i);
            for (int j = curr.length / 2 + 1; j < curr.length; j ++) {
                Long location = curr[j];
                Long target = curr[j - curr.length / 2];
                if (found(target, almanac, seeds)) return location;
            }
        }
        return -1;
    }

    static boolean found(Long target, List<List<Long[]>> almanac, List<Long[]> seeds) {
        for (int i = almanac.size() - 2; i >= 0; i --) {
            List<Long[]> longs = almanac.get(i);
            boolean foundInAlmanac = false;
            for (var entry : longs) {
                if (foundInAlmanac) break;
                for (int j = entry.length / 2 + 1; j < entry.length; j ++) {
                    if (foundInAlmanac) break;
                    if (entry[j] == target) {
                        target = entry[j - entry.length / 2];
                        foundInAlmanac = true;
                    }
                }
            }
        }
        for (var seed : seeds) {
            if (seed[0] <= target && target <= seed[1]) return true;
        }
        return false;
    }

    static boolean searchInAlmanac(List<List<Long[]>> almanac, int index, List<Long[]> seeds, Long target) {
        if (index < 0) {
            for (var seed : seeds) {
                if (seed[0] <= target && target <= seed[1]);
            }
        }
        var map = almanac.get(index);
        for (int i = 0; i < map.size(); i ++) {
            Long[] curr = map.get(i);
            for (int j = curr.length / 2 + 1; j < curr.length; j ++) {
                Long x = curr[j];
                if (x == target) {
                    if (searchInAlmanac(almanac, index - 1, seeds, curr[j - curr.length / 2])) return true;
                }
            }
        }
        return false;
    }

    static int part1_no(int[][] map, List<Integer> seeds) {
        int minLoc = 100;
        for (var seed : seeds) {
            int location = seed;
            for (int i = 0; i < map.length; i++) {
                location = map[i][location] > 0 ? map[i][location] : location;
            }
            minLoc = Math.min(minLoc, location);
        }
        return minLoc;
    }

    static Long part1(List<List<List<Long>>> maps, List<Long> seeds) {
        long minLoc = Long.MAX_VALUE;
        for (var seed : seeds) {
            Long location = seed;
            for (var map : maps) {
                for (var line : map) {
                    long destination = line.get(0);
                    long source = line.get(1);
                    long range = line.get(2);
                    if (source <= location && location < source + range) {
                        location = destination + (location - source);
                        break;
                    }
                }
            }
            minLoc = Math.min(minLoc, location);
        }
        return minLoc;
    }

    static Long part1_sorting(List<List<Long>> maps, List<Long> seeds) {
        long minLoc = Long.MAX_VALUE;
        for (var seed : seeds) {
            System.out.println("Seed: " + seed);
            Long location = seed;
            for (var map : maps) {
                System.out.println("Map: " + map);
                for (int i = map.size() / 2; i < map.size(); i++) {
                    long source = map.get(i);
                    long destination = map.get(i - map.size() / 2);
                    System.out.println(source + " " + destination);
                    if (source <= location && location < destination) {
                        location = destination + (location - source);
                        System.out.println("Found location: " + location);
                        break;
                    }
                }
            }
            minLoc = Math.min(minLoc, location);
        }
        System.out.println("Returning: " + minLoc);
        return minLoc;
    }

    static Long part2(List<List<List<Long>>> maps, List<Long> seedsWithRanges) {
        long minLoc = Long.MAX_VALUE;
        List<Long> seeds = new LinkedList<>();
        for (int i = 0; i < seedsWithRanges.size(); i += 2) {
            Long startSeed = seedsWithRanges.get(i);
            Long endSeed = startSeed + seedsWithRanges.get(i + 1);
            seeds.add(startSeed);
            seeds.add(endSeed);
        }
        seeds.sort(Comparator.naturalOrder());
        for (int i = 0; i < seeds.size(); i += 2) {
            Long startSeed = seeds.get(i);
            Long endSeed = seeds.get(i + 1);
            for (; startSeed < endSeed; startSeed++) {
                long result = part1(maps, List.of(startSeed));
                minLoc = Math.min(result, minLoc);
            }
        }
        return minLoc;
    }
    static Long part2_sorting(List<List<Long>> maps, List<Long> seedsWithRanges) {
        long minLoc = Long.MAX_VALUE;
        List<Long> seeds = new LinkedList<>();
        for (int i = 0; i < seedsWithRanges.size(); i += 2) {
            Long startSeed = seedsWithRanges.get(i);
            Long endSeed = startSeed + seedsWithRanges.get(i + 1);
            seeds.add(startSeed);
            seeds.add(endSeed);
        }
        System.out.println("Seeds: " + seeds);
        for (int i = 0; i < seeds.size(); i += 2) {
            Long startSeed = seeds.get(i);
            Long endSeed = seeds.get(i + 1);
            for (; startSeed < endSeed; startSeed++){
                minLoc = Math.min(part1_sorting(maps, List.of(startSeed)), minLoc);
            }
        }
        return minLoc;
    }

}
