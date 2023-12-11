package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHelper {
    public static Map<Coordinate, Character> generateMap(List<String> input) {
        Map<Coordinate, Character> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                map.put(new Coordinate(x, y), str.charAt(x));
            }
        }
        return map;
    }
}
