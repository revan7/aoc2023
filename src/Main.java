
import solutions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
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
//        dayEight_partOne();
//        dayNine_partOne();
//        dayNine_partTwo();
//        dayTen_partOne();
//        dayEleven_partOne();
//        dayEleven_partTwo();
//            dayTwelve_partOne();
//            dayTwelve_partTwo();
//        dayThirteen_partOne();
//        dayThirteen_partTwo();
//          dayFourteen_partOne();
//            dayFourteen_partTwo();
//        dayFifteen_partOne();
//        dayFifteen_partTwo();
//        daySixteen_partOne();
//          daySixteen_partTwo();
//        daySeventeen_partOne();
//        daySeventeen_partTwo();
//        dayEighteen_partOne();
//        dayEighteen_partTwo();
//        dayNineteen_partOne();
//        dayNineteen_partTwo();
//        dayTwenty_partOne();
//        dayTwenty_partOne();
//        dayTwenty_partOTwo();
//        dayTwentyOne_partOne();
//          dayTwentyOne_partOTwo();
          dayTwentyTwo_partOne();
          //dayTwentyTwo_partTwo();
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

    public static void dayEight_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eight.txt"))) {
            var solution = Day8.solvePart2(br);
            System.out.println("Day 8 part one answer: " + solution);
        }
    }

    public static void daySeven_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_seven.txt"))) {
            var solution = Day7.solvePart2(br);
            System.out.println("Day 7 part two answer: " + solution);
        }
    }

    public static void dayNine_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_nine.txt"))) {
            var solution = Day9.solvePart1(br);
            System.out.println("Day 9 part one answer: " + solution);
        }
    }

    public static void dayNine_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_nine.txt"))) {
            var solution = Day9.solvePart2(br);
            System.out.println("Day 9 part two answer: " + solution);
        }
    }

    public static void dayTen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_ten.txt"))) {
            var solution = Day10.solvePart2(br);
            System.out.println("Day 10 part one answer: " + solution);
        }
    }

    public static void dayTen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_ten.txt"))) {
            var solution = Day10.solvePart2(br);
            System.out.println("Day 10 part two answer: " + solution);
        }
    }

    public static void dayEleven_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            var solution = Day11.solvePart1(br);
            System.out.println("Day 11 part one answer: " + solution);
        }
    }

    public static void dayEleven_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eleven.txt"))) {
            var solution = Day11.solvePart2(br);
            System.out.println("Day 11 part two answer: " + solution);
        }
    }

    public static void dayTwelve_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twelve.txt"))) {
            var solution = Day12.solvePart1(br);
            System.out.println("Day 12 part one answer: " + solution);
        }
    }

    public static void dayTwelve_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twelve_example.txt"))) {
            var solution = Day12.solvePart2(br);
            System.out.println("Day 12 part one answer: " + solution);
        }
    }

    public static void dayThirteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_thirteen.txt"))) {
            var solution = Day13.solvePart1(br);
            System.out.println("Day 13 part one answer: " + solution);
        }
    }

    public static void dayThirteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_thirteen.txt"))) {
            var solution = Day13.solvePart2(br);
            System.out.println("Day 13 part two answer: " + solution);
        }
    }

    public static void dayFourteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_fourteen_example.txt"))) {
            var solution = Day14.solvePart1(br);
            System.out.println("Day 14 part one answer: " + solution);
        }
    }

    public static void dayFourteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_fourteen.txt"))) {
            var solution = Day14.solvePart2(br);
            System.out.println("Day 14 part two answer: " + solution);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dayFifteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_fifteen.txt"))) {
            var solution = Day15.solvePart1(br);
            System.out.println("Day 15 part one answer: " + solution);
        }
    }

    //wrong too low: 211965 235010
    public static void dayFifteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_fifteen.txt"))) {
            var solution = Day15.solvePart2(br);
            System.out.println("Day 15 part two answer: " + solution);
        }
    }

    public static void daySixteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_sixteen.txt"))) {
            var solution = Day16.solvePart1(br);
            System.out.println("Day 15 part one answer: " + solution);
        }
    }

    public static void daySixteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_sixteen.txt"))) {
            var solution = Day16.solvePart2(br);
            System.out.println("Day 15 part two answer: " + solution);
        }
    }

    public static void daySeventeen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_seventeen.txt"))) {
            var solution = Day17.solvePart1(br);
            System.out.println("Day 17 part one answer: " + solution);
        }
    }

    public static void daySeventeen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_seventeen.txt"))) {
            var solution = Day17.solvePart2(br);
            System.out.println("Day 17 part two answer: " + solution);
        }
    }
    public static void dayEighteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eighteen.txt"))) {
            var solution = Day18.solvePart1(br);
            System.out.println("Day 18 part one answer: " + solution);
        }
    }

    public static void dayEighteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_eighteen.txt"))) {
            var solution = Day18.solvePart2(br);
            System.out.println("Day 18 part two answer: " + solution);
        }
    }

    public static void dayNineteen_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_nineteen.txt"))) {
            var solution = Day19.solvePart1(br);
            System.out.println("Day 19 part one answer: " + solution);
        }
    }

    public static void dayNineteen_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_nineteen.txt"))) {
            var solution = Day19.solvePart2(br);
            System.out.println("Day 19 part two answer: " + solution);
        }
    }

    public static void dayTwenty_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twenty.txt"))) {
            var solution = Day20.solvePart1(br);
            System.out.println("Day 20 part two answer: " + solution);
        }
    }

    public static void dayTwenty_partOTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twenty.txt"))) {
            var solution = Day20.solvePart2(br);
            System.out.println("Day 20 part two answer: " + solution);
        }
    }

    public static void dayTwentyOne_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twentyone.txt"))) {
            var solution = Day21.solvePart1(br);
            System.out.println("Day 21 part two answer: " + solution);
        }
    }

    public static void dayTwentyOne_partOTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twentyone.txt"))) {
            var solution = Day21.solvePart2(br);
            System.out.println("Day 21 part two answer: " + solution);
        }
    }

    //too high 1323, 1199
    public static void dayTwentyTwo_partOne() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twentytwo_example.txt"))) {
            var solution = Day22.solvePart1(br);
            System.out.println("Day 22 part one answer: " + solution);
        }
    }

    public static void dayTwentyTwo_partTwo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("day_twentytwo.txt"))) {
            var solution = Day22.solvePart2(br);
            System.out.println("Day 22 part two answer: " + solution);
        }
    }

}