package solutions;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2Tests {

    @Test
    public void partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_two.txt"))) {
                Day2.solvePart1(br);
        }
    }

    @Test
    public void partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_two.txt"))) {
            Day2.solvePart2(br);
        }
    }

}
