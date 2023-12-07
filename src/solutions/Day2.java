package solutions;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2 {

    static boolean part1(String s) {
        int reds = 0;
        int greens = 0;
        int blue = 0;
        String t = s.split(":")[1].trim();
        String[] rounds = t.split(";");
        for (String round : rounds) {
            String[] hands = round.split(",");
            for (String hand : hands) {
                String[] numberColor = hand.trim().split(" ");
                if (numberColor[1].equalsIgnoreCase("red")) reds += Integer.parseInt(numberColor[0].trim());
                if (numberColor[1].equalsIgnoreCase("blue")) blue += Integer.parseInt(numberColor[0].trim());
                if (numberColor[1].equalsIgnoreCase("green")) greens += Integer.parseInt(numberColor[0].trim());
            }
            if (reds > 12 || greens > 13 || blue > 14) {
                return false;
            }
            reds = 0;
            greens = 0;
            blue = 0;
        }
        return true;
    }

    static int part2(String s) {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        String t = s.split(":")[1].trim();
        String[] rounds = t.split(";");
        for (String round : rounds) {
            String[] hands = round.split(",");
            for (String hand : hands) {
                String[] numberColor = hand.trim().split(" ");
                int number = Integer.parseInt(numberColor[0].trim());
                if (numberColor[1].equalsIgnoreCase("red")) maxRed = Math.max(number, maxRed);
                if (numberColor[1].equalsIgnoreCase("blue")) maxBlue = Math.max(number, maxBlue);
                if (numberColor[1].equalsIgnoreCase("green")) maxGreen = Math.max(number, maxGreen);
            }
        }
        return maxRed * maxBlue * maxGreen;
    }

    public static int solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        int game = 1;
        int sum = 0;
        while (line != null) {
            if (part1(line)) {
                sum += game;
            }
            game++;
            line = br.readLine();
        }
        return sum;
    }

    public static int solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        int sum = 0;
        while (line != null) {
            sum += part2(line);
            line = br.readLine();
        }
        return sum;
    }
}
