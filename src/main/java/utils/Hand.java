package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    public final List<Card> cards;
    public final int bid;
    private final int type;

    public Hand(String cards, int bid) {
        this.cards = cards.chars()
                .mapToObj(i -> (char) i)
                .map(Card::new)
                .collect(Collectors.toList());
        this.bid = bid;
        this.type = getType(cards.toCharArray());
    }

    // return 1 if this hand is weaker
    public int compare(Hand hand) {
        if(this.type > hand.type){
            return -1;
        } else if(this.type < hand.type){
            return 1;
        }

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
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
            return 2;
        } else if (map.containsValue(3) && map.containsValue(2)) {
            return 3;
        } else if (map.containsValue(3) && !map.containsValue(2)) {
            return 4;
        } else if (map.values().stream().filter(x -> x == 2).count() == 2) {
            return 5;
        } else if (map.values().stream().filter(x -> x == 2).count() == 1) {
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
