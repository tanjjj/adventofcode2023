package utils.day19;

import java.util.HashMap;
import java.util.Map;

public class Rating {
    public final Map<String, Integer> rating;

    public Rating(String rating) {
        this.rating = new HashMap<>();
        rating = rating.substring(1, rating.length() - 1);
        String[] splits = rating.split(",");
        for (String str : splits) {
            String[] r = str.split("=");
            this.rating.put(r[0], Integer.parseInt(r[1]));
        }
    }

    public int getTotal() {
        return rating.values().stream().mapToInt(Integer::intValue).sum();
    }
}
