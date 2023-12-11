package solutions;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Day11Test {

    @Test
    public void dayEleven_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            var solution = Day11.solvePart2(br);
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 0)
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    public void dayEleven_partTwo_fast() throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            var solution = Day11.solvePart2_butCool(br);
        }
    }
}
