package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day1 {

    static int partOne(String s) {
        char[] charArray = s.toCharArray();
        char firstDigit = 0, lastDigit = 0;
        for (int i = 0; i < charArray.length; i++) {
            char charAt = charArray[i];
            if (Character.isDigit(charAt)) {
                firstDigit = charAt;
                break;
            }
        }
        for (int i = charArray.length - 1; i >= 0; i--) {
            char charAt = charArray[i];
            if (Character.isDigit(charAt)) {
                lastDigit = charAt;
                break;
            }
        }
        String number = "";
        number += firstDigit;
        number += lastDigit;
        return Integer.parseInt(number);
    }

    static int partTwo(String s) {
        char[] charArray = s.toCharArray();
        char firstDigit = 0, lastDigit = 0;
        for (int i = 0; i < charArray.length; i++) {
            char charAt = charArray[i];
            if (Character.isDigit(charAt)) {
                firstDigit = charAt;
                break;
            }
            Character digitFromMap = getDigitFromMap(i, s);
            if (digitFromMap != null) {
                firstDigit = digitFromMap;
                break;
            }
        }
        for (int i = charArray.length - 1; i >= 0; i--) {
            char charAt = charArray[i];
            if (Character.isDigit(charAt)) {
                lastDigit = charAt;
                break;
            }
            Character digitFromMap = getDigitFromMap(i, s);
            if (digitFromMap != null) {
                lastDigit = digitFromMap;
                break;
            }
        }
        String number = "";
        number += firstDigit;
        number += lastDigit;
        return Integer.parseInt(number);
    }

    private static Character getDigitFromMap(int charAt, String s) {
        Map<String, Character> digitsToInt = new HashMap<>();
        digitsToInt.put("zero", '0');
        digitsToInt.put("one", '1');
        digitsToInt.put("two", '2');
        digitsToInt.put("three", '3');
        digitsToInt.put("four", '4');
        digitsToInt.put("five", '5');
        digitsToInt.put("six", '6');
        digitsToInt.put("seven", '7');
        digitsToInt.put("eight", '8');
        digitsToInt.put("nine", '9');
        for (var entry : digitsToInt.entrySet()) {
            if (s.substring(charAt).startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static int solvePart1(BufferedReader br) throws IOException {
        String line = br.readLine();
        int sum = 0;
        while (line != null) {
            sum += partOne(line);
            line = br.readLine();
        }
        return sum;
    }

    public static int solvePart2(BufferedReader br) throws IOException {
        String line = br.readLine();
        int sum = 0;
        while (line != null) {
            sum += partTwo(line);
            line = br.readLine();
        }
        return sum;
    }

}
