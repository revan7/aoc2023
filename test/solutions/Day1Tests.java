package solutions;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day1Tests {

    @Test
    public void partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_one.txt"))) {
            var solution = Day1.solvePart1(br);
            System.out.println("Day 1 part one answer: " + solution);
        }
    }

    @Test
    public void partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_one_part2.txt"))) {
            var solution = Day1.solvePart2(br);
            System.out.println("Day 1 part two answer: " + solution);
        }
    }

}
