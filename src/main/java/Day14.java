import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2023 puzzle 14
public class Day14 implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day14.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        Map<Coordinate, Character> newMap = tilt(maxX, maxY, map);
        int result = calculateLoad(maxX, maxY, newMap);
        System.out.println(result);
    }

    private Map<Coordinate, Character> tilt(int maxX, int maxY, Map<Coordinate, Character> map) {
        Map<Coordinate, Character> newMap = new HashMap<>();
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                Character oldValue = map.get(coordinate);
                if (y == 0 || oldValue.equals('#') || oldValue.equals('.')) {
                    newMap.put(coordinate, oldValue);
                    continue;
                }

                // slide
                int tmpY = y;
                while (tmpY > 0 && isEmpty(new Coordinate(x, tmpY - 1), newMap)) {
                    tmpY--;
                }
                newMap.put(new Coordinate(x, tmpY), oldValue);
            }

        }

        return newMap;
    }

    private boolean isEmpty(Coordinate coordinate, Map<Coordinate, Character> map) {
        return !map.containsKey(coordinate) || map.get(new Coordinate(coordinate.x, coordinate.y)).equals('.');
    }

    private int calculateLoad(int maxX, int maxY, Map<Coordinate, Character> map) {
        int result = 0;
        int load = maxY + 1;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (map.getOrDefault(new Coordinate(x, y), ' ').equals('O')) {
                    result += load;
                }
            }
            load--;
        }

        return result;
    }

}
