package solutions;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DayEleven {

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.SingleShotTime)
    public static void dayEleven_partTwo() throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            var solution = Day11.solvePart2(br);
            System.out.println("Day 11 part two answer: " + solution);
        }
    }

}
