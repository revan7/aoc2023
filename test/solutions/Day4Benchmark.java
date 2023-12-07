package solutions;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class Day4Benchmark {

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.SingleShotTime)
    public void day_4_no_logs() throws InterruptedException, Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("day_four.txt"))) {
            var solution = Day4.solvePart2(br);
            System.out.println("Day 4 part two answer :" + solution);
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.SingleShotTime)
    public void init() throws InterruptedException, Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("day_four.txt"))) {
            var solution = Day4.solvePart2_with_logs(br);
            System.out.println("Day 4 part two answer :" + solution);
        }
    }
}
