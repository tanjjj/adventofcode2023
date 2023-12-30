package utils.day19;

import java.util.*;

public class RatingRange {
    public final Map<String, List<Integer>> rating;

    public RatingRange(List<Integer> x, List<Integer> m, List<Integer> a, List<Integer> s) {
        this.rating = new HashMap<>();
        this.rating.put("x", x);
        this.rating.put("m", m);
        this.rating.put("a", a);
        this.rating.put("s", s);
    }

    public long getTotal() {
        return rating.values().stream()
                .map(list -> list.get(1) - list.get(0))
                .reduce(1, (a, b) -> a * b);
    }
}
