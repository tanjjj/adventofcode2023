package utils.day7;

import utils.Card2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand2 {
    private static final Character J = 'J';

    public final List<Card2> cards;
    public final int bid;
    private final int type;

    public Hand2(String cards, int bid) {
        this.cards = cards.chars()
                .mapToObj(i -> (char) i)
                .map(Card2::new)
                .collect(Collectors.toList());
        this.bid = bid;
        this.type = getType(cards.toCharArray());
    }

    // return 1 if this hand is weaker
    public int compare(Hand2 hand) {
        if (this.type > hand.type) {
            return -1;
        } else if (this.type < hand.type) {
            return 1;
        }

        for (int i = 0; i < cards.size(); i++) {
            Card2 card = cards.get(i);
            if (card.compareTo(hand.cards.get(i)) < 0) {
                return -1;
            } else if (card.compareTo(hand.cards.get(i)) > 0) {
                return 1;
            }
        }
        return 0;
    }

    private int getType(char[] cards) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : cards) {
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
        }
        if (map.containsValue(5)) {
            return 1;
        } else if (map.containsValue(4)) {
            if (map.containsKey(J)) {
                return 1;
            }
            return 2;
        } else if (map.containsValue(3) && map.containsValue(2)) {
            if (map.containsKey(J)) {
                if (map.get(J) == 2 || map.get(J) == 3) {
                    return 1; // 5 cards same
                } else if (map.get(J) == 1) {
                    return 2; // 4 cards same
                }
            }
            return 3;
        } else if (map.containsValue(3) && !map.containsValue(2)) {
            if (map.containsKey(J)) {
                if (map.get(J) == 3 || map.get(J) == 1) {
                    return 2; // 4 cards same
                }
            }
            return 4;
        } else if (map.values().stream().filter(x -> x == 2).count() == 2) {
            if (map.containsKey(J)) {
                if (map.get(J) == 2) {
                    return 2; // 4 cards same
                } else if (map.get(J) == 1) {
                    return 3; // 3 + 2 cards same
                }
            }
            return 5;
        } else if (map.values().stream().filter(x -> x == 2).count() == 1) {
            if (map.containsKey(J)) {
                if (map.get(J) == 2) {
                    return 4; // 3 cards same, 2 diff
                } else if (map.get(J) == 1) {
                    return 4; // 3 cards same, 2 diff
                }
            }
            return 6;
        }

        if (map.containsKey(J)) {
            return 6;
        }
        return 7;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                ", bid=" + bid +
                '}';
    }
}
