package utils.day7;

import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card> {
    private static final List<Character> order = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    public final char value;

    public Card(char value) {
        this.value = value;
    }

    /**
     * Returns:
     * a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Card o) {
        int thisOrder = order.indexOf(value);
        int otherOrder = order.indexOf(o.value);
        return Integer.compare(otherOrder, thisOrder); // return 1 if this card is stronger
    }

    @Override
    public String toString() {
        return "" + value;
    }
}