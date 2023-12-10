import solutions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        dayOne_partOne();
//        dayOne_partTwo();
//        dayTwo_partOne();
//        dayTwo_partTwo();
//        dayThree_partOne();
//        dayThree_partTwo();
//        dayFour_partOne();
//        dayFour_partTwo();
//        dayFive_partOne();
//          dayFive_partTwo();
//        daySix_partOne();
//        daySix_partTwo();
//        daySeven_partOne();
        //daySeven_partTwo();
        dayEight_partOne();
    }


    public static void dayOne_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_one.txt"))) {
            var solution = Day1.solvePart1(br);
            System.out.println("Day 1 part one answer: " + solution);
        }
    }

    public static void dayOne_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_one_part2.txt"))) {
            var solution = Day1.solvePart2(br);
            System.out.println("Day 1 part two answer: " + solution);
        }
    }

    public static void dayTwo_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_two.txt"))) {
            var solution = Day2.solvePart1(br);
            System.out.println("Day 2 part one answer: " + solution);
        }
    }

    public static void dayTwo_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_two.txt"))) {
            var solution = Day2.solvePart2(br);
            System.out.println("Day 2 part two answer: " + solution);
        }
    }

    public static void dayThree_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_three.txt"))) {
            var solution = Day3.solvePart1(br);
            System.out.println("Day 3 part one answer: " + solution);
        }
    }

    public static void dayThree_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_three.txt"))) {
            var solution = Day3.solvePart2(br);
            System.out.println("Day 3 part two answer: " + solution);
        }
    }

    public static void dayFour_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_four_example.txt"))) {
            var solution = Day4.solvePart1(br);
            System.out.println("Day 4 part one answer: " + solution);
        }
    }

    public static void dayFour_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_four.txt"))) {
            var solution = Day4.solvePart2(br);
            System.out.println("Day 4 part two answer: " + solution);
        }
    }

    public static void dayFive_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_five.txt"))) {
            var solution = Day5.solvePart1(br);
            System.out.println("Day 5 part one answer: " + solution);
        }
    }

    public static void dayFive_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_five.txt"))) {
            var solution = Day5.solvePart2_pq(br);
            System.out.println("Day 5 part two answer: " + solution);
        }
    }

    public static void daySix_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_six.txt"))) {
            var solution = Day6.solvePart1(br);
            System.out.println("Day 6 part one answer: " + solution);
        }
    }

    public static void daySix_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_six.txt"))) {
            var solution = Day6.solvePart2(br);
            System.out.println("Day 6 part two answer: " + solution);
        }
    }

    public static void daySeven_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_seven.txt"))) {
            var solution = Day7.solvePart1(br);
            System.out.println("Day 7 part one answer: " + solution);
        }
    }

    public static void daySeven_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_seven.txt"))) {
            var solution = Day7.solvePart2(br);
            System.out.println("Day 7 part two answer: " + solution);
        }
    }

    public static void dayEight_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eight.txt"))) {
            var solution = Day8.solvePart2(br);
            System.out.println("Day 8 part one answer: " + solution);
        }
    }

}
//245942328 WRONG
//246149935 WRONG
//246181539 WRONG
//246350798 WRONG
//246236009 WRONG
//246349006 WRONG
//245799468 WRONG