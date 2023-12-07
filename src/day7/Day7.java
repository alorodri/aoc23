package day7;

import org.jetbrains.annotations.NotNull;
import utils.Problem;
import utils.ProblemType;
import utils.TestResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestResults(resultA = "6440", resultB = "5905")
public class Day7 extends Problem {
    public Day7() {
        super(7);
        cardValues.put('2', 2);
        cardValues.put('3', 3);
        cardValues.put('4', 4);
        cardValues.put('5', 5);
        cardValues.put('6', 6);
        cardValues.put('7', 7);
        cardValues.put('8', 8);
        cardValues.put('9', 9);
        cardValues.put('T', 10);
        cardValues.put('J', 11);
        cardValues.put('Q', 12);
        cardValues.put('K', 13);
        cardValues.put('A', 14);

        handValues.put(HandTypes.FIVE, 7);
        handValues.put(HandTypes.FOUR, 6);
        handValues.put(HandTypes.HOUSE, 5);
        handValues.put(HandTypes.THREE, 4);
        handValues.put(HandTypes.TWO_PAIR, 3);
        handValues.put(HandTypes.TWO, 2);
        handValues.put(HandTypes.ONE, 1);
    }

    Map<Character, Integer> cardValues = new HashMap<>();
    Map<HandTypes, Integer> handValues = new HashMap<>();
    List<Hand> hands;
    ProblemType type;

    @Override
    protected String solveProblem(ArrayList<String> lines, ProblemType type) {
        this.type = type;
        hands = new ArrayList<>();
        if (type == ProblemType.B) {
            cardValues.replace('J', 1);
        }
        for (var line : lines) {
            Hand hand = new Hand();
            var splittedLine = line.split(" ");
            for (var c : splittedLine[0].trim().toCharArray()) {
                hand.cards.add(c);
            }
            hand.bet = Long.parseLong(splittedLine[1].trim());
            getHandType(hand);

            hands.add(hand);
        }

        hands.sort(Hand::compareTo);
        Long result = 0L;
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).bet * (i + 1);
        }
        return String.valueOf(result);
    }

    private void getHandType(Hand hand) {
        Map<Character, Integer> entries = new HashMap<>();
        for (var card : hand.cards) {
            if (entries.containsKey(card)) {
                entries.replace(card, entries.get(card) + 1);
            } else {
                entries.put(card, 1);
            }
        }
        if (type == ProblemType.B && entries.containsKey('J')) {
            Character keyWithGreatestValue = null;
            Integer higherValue = 0;
            for (var entry : entries.entrySet()) {
                if (keyWithGreatestValue == null || higherValue < entry.getValue()) {
                    higherValue = entry.getValue();
                    keyWithGreatestValue = entry.getKey();
                } else if (higherValue.equals(entry.getValue()) && entry.getKey() != 'J') {
                    keyWithGreatestValue = cardValues.get(keyWithGreatestValue) > cardValues.get(entry.getKey())
                            ? keyWithGreatestValue
                            : entry.getKey();
                }
            }
            entries.replace(keyWithGreatestValue, entries.get(keyWithGreatestValue) + entries.get('J'));
            entries.remove('J');
        }
        if (entries.values().size() == 5) {
            // hay 5 cartas diferentes
            hand.type = HandTypes.ONE;
        } else if (entries.values().size() == 1) {
            // todas las cartas son la misma carta
            hand.type = HandTypes.FIVE;
        } else if (entries.values().size() == 2) {
            // es un FOUR o un HOUSE
            if (entries.containsValue(4)) {
                hand.type = HandTypes.FOUR;
            } else {
                hand.type = HandTypes.HOUSE;
            }
        } else if (entries.values().size() == 3) {
            // puede ser un THREE o un TWO PAIR
            if (entries.containsValue(3)) {
                hand.type = HandTypes.THREE;
            } else {
                hand.type = HandTypes.TWO_PAIR;
            }
        } else {
            // solamente queda el PAR(TWO)
            hand.type = HandTypes.TWO;
        }
    }

    enum HandTypes {
        FIVE,
        FOUR,
        HOUSE,
        THREE,
        TWO_PAIR,
        TWO,
        ONE
    }

    class Hand implements Comparable<Hand> {
        HandTypes type;
        List<Character> cards = new ArrayList<>();
        Long bet;

        @Override
        public String toString() {
            return "Hand{" +
                    "type=" + type +
                    ", cards=" + cards +
                    ", bet=" + bet +
                    '}';
        }

        @Override
        public int compareTo(@NotNull Hand o) {
            if (handValues.get(this.type) > handValues.get(o.type)) {
                return 1;
            } else if (handValues.get(this.type) < handValues.get(o.type)) {
                return -1;
            } else {
                // equal
                for (int i = 0; i < this.cards.size(); i++) {
                    var card = this.cards.get(i);
                    var oCard = o.cards.get(i);
                    if (cardValues.get(card) > cardValues.get(oCard)) {
                        return 1;
                    } else if (cardValues.get(card) < cardValues.get(oCard)) {
                        return -1;
                    }
                }
            }
            return 0;
        }
    }
}
