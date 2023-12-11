package solutions;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day11Test {

    @Test
    public void dayEleven_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            Day11.solvePart1(br);
        }
    }

    @Test
    public void dayEleven_partTwo() throws InterruptedException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            Day11.solvePart2_butCool(br);
        }
    }
}
