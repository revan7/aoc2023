package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day7 {

    enum Rank {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIRS(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        public final Integer value;

        Rank(Integer value) {
            this.value = value;
        }
    }

    static Map<Character, Integer> cardMappings;

    public static Long solvePart1(BufferedReader br) throws IOException {
        initMap1();
        String line = br.readLine();
        Map<String, Integer> game = new HashMap<>();
        while (line != null) {
            String[] split = line.split(" ");
            String hand = split[0];
            Integer bid = Integer.parseInt(split[1].trim());
            game.put(hand, bid);
            line = br.readLine();
        }
        return part1(game);
    }

    private static void initMap2() {
        cardMappings = new HashMap<>();
        cardMappings.put('A', 12);
        cardMappings.put('K', 11);
        cardMappings.put('Q', 10);
        cardMappings.put('T', 9);
        cardMappings.put('9', 8);
        cardMappings.put('8', 7);
        cardMappings.put('7', 6);
        cardMappings.put('6', 5);
        cardMappings.put('5', 4);
        cardMappings.put('4', 3);
        cardMappings.put('3', 2);
        cardMappings.put('2', 1);
        cardMappings.put('J', 0);
    }

    public static void initMap1() {
        cardMappings = new HashMap<>();
        cardMappings.put('A', 12);
        cardMappings.put('K', 11);
        cardMappings.put('Q', 10);
        cardMappings.put('T', 9);
        cardMappings.put('9', 8);
        cardMappings.put('8', 7);
        cardMappings.put('7', 6);
        cardMappings.put('6', 5);
        cardMappings.put('5', 4);
        cardMappings.put('4', 3);
        cardMappings.put('3', 2);
        cardMappings.put('2', 1);
        cardMappings.put('J', 0);
    }

    public static Long solvePart2(BufferedReader br) throws IOException {
        initMap2();
        String line = br.readLine();
        Map<String, Integer> game = new HashMap<>();
        while (line != null) {
            String[] split = line.split(" ");
            String hand = split[0];
            Integer bid = Integer.parseInt(split[1].trim());
            game.put(hand, bid);
            line = br.readLine();
        }
        return part2(game);
    }

    static Long part2(Map<String, Integer> game) {
        List<Map.Entry<String, Integer>> gameSorted = new ArrayList<>(game.entrySet().stream().sorted((a, b) -> {
            Integer rankA = getRank(a.getKey()).value;
            Integer rankB = getRank(b.getKey()).value;
            if (Objects.equals(rankA, rankB)) {
                int i = 0, j = 0;
                while (i < a.getKey().length() && a.getKey().charAt(i) == b.getKey().charAt(j)) {
                    i++;
                    j++;
                }
                return cardMappings.get(a.getKey().charAt(i)) - cardMappings.get(b.getKey().charAt(j));
            }
            return rankA - rankB;
        }).toList());
        Long count = 1L;
        Long result = 0L;
        while(!gameSorted.isEmpty()) {
            result += (game.get(gameSorted.remove(0).getKey()) * count);
            count++;
        }
        return result;
    }

    private static String sortHand(String hand) {
        Character[] charArray = new Character[5];
        for (int i = 0; i < hand.length(); i ++) {
            charArray[i] = hand.charAt(i);
        }
        Arrays.sort(charArray, Comparator.comparingInt(a -> cardMappings.get(a)));
        StringBuilder sb = new StringBuilder();
        for (var c : charArray) sb.append(c);
        return sb.toString();
    }

    static Long part1(Map<String, Integer> game) {
        List<Map.Entry<String, Integer>> gameSorted = new ArrayList<>(game.entrySet().stream().sorted((a, b) -> {
            Integer rankA = getRank(a.getKey()).value;
            Integer rankB = getRank(b.getKey()).value;
            if (Objects.equals(rankA, rankB)) {
                int i = 0, j = 0;
                while (i < a.getKey().length() && a.getKey().charAt(i) == b.getKey().charAt(j)) {
                    i++;
                    j++;
                }
                return cardMappings.get(a.getKey().charAt(i)) - cardMappings.get(b.getKey().charAt(j));
            }
            return rankA - rankB;
        }).toList());
        Long count = 1L;
        Long result = 0L;

        System.out.println(gameSorted);
        for (var entry : gameSorted) {
            System.out.println(entry.getKey() + " " + getRank(entry.getKey()));
        }
        while(!gameSorted.isEmpty()) {
            result += (game.get(gameSorted.remove(0).getKey()) * count);
            count++;
        }
        System.out.println(count);
        return result;
    }
    //KKKAK

    static Rank getRank(String hand) {
        if (hand.contains("J")) return getRankWithWildCard(hand);
        hand= sortHand(hand);
        Map<Character, Integer> cardCount = new HashMap<>();
        for (int i = 0; i < hand.length(); i ++) {
            Character c = hand.charAt(i);
            Integer cCount = cardCount.getOrDefault(c, 0);
            cardCount.put(c, cCount + 1);
        }
        List<Integer> counts = cardCount.values().stream().sorted(Comparator.reverseOrder()).toList();
        if (counts.size() == 1) return Rank.FIVE_OF_A_KIND;
        if (counts.size() == 2) {
            for (var entry : cardCount.entrySet()) {
                if (entry.getValue() == 4 || entry.getValue() == 1) return Rank.FOUR_OF_A_KIND;
                return Rank.FULL_HOUSE;
            }
        }
        if (counts.size() == 3) {
            if (hand.charAt(0) == hand.charAt(2)) return Rank.THREE_OF_A_KIND;
            if (hand.charAt(1) == hand.charAt(3)) return  Rank.THREE_OF_A_KIND;
            if (hand.charAt(2) == hand.charAt(4)) return  Rank.THREE_OF_A_KIND;
            return Rank.TWO_PAIRS;
        }
        if (counts.size() == 4) return Rank.ONE_PAIR;
        return Rank.HIGH_CARD;
    }

    static Rank getRankWithWildCard(String hand) {
        if (hand.equalsIgnoreCase("JJJJJ")) return Rank.FIVE_OF_A_KIND;
        Map<Character, Integer> cardCount = new HashMap<>();
        Character freqChar= null;
        Integer freqCount = 0;
        for (int i = 0; i < hand.length(); i ++) {
            Character c = hand.charAt(i);
            if (c == 'J') continue;
            Integer cCount = cardCount.getOrDefault(c, 0);
            cardCount.put(c, cCount + 1);
            if (freqChar == null || cCount > freqCount) {
                freqCount = cCount;
                freqChar = c;
            }
        }
        return getRank(hand.replace('J', freqChar));
    }
    //J5252 Full house
    //269TJ One pair
    //2KQJQ Three
    //45JJQ

    static Rank getRankBruteForce(String hand) {
        Rank max = Rank.ONE_PAIR;
        for (int i = 0; i < hand.length(); i ++) {
            if (hand.charAt(i) == 'J') {
                for (var newChar : cardMappings.keySet()) {
                    if (newChar == 'J') continue;
                    String newHand = hand.substring(0, i) + newChar + hand.substring(i + 1);
                    Rank r = getRank(newHand);
                    if (r == Rank.FIVE_OF_A_KIND) return Rank.FIVE_OF_A_KIND;
                    max = r.value > max.value ? r : max;
                }
            }
        }
        return max;
    }

}
