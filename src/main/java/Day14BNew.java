import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2023 puzzle 14
public class Day14BNew implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;

        int remainingCircles = 1000000000;
        while (remainingCircles > 0) {
            tiltNorth(maxX, maxY, map);
            remainingCircles--;
            System.out.println(remainingCircles);
        }

        Map<Coordinate, Character> tmpMap = tiltNorth(maxX, maxY, map);
        int result = calculateLoad(maxX, maxY, tmpMap);
        System.out.println("result" + result);
    }

/*    private Map<Coordinate, Character> circle(int maxX, int maxY, Map<Coordinate, Character> map) {
        return tiltEast(maxX, maxY, tiltSouth(maxX, maxY, tiltWest(maxX, maxY, tiltNorth(maxX, maxY, map))));
    }*/

    private Map<Coordinate, Character> tiltNorth(int maxX, int maxY, Map<Coordinate, Character> map) {
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                Character oldValue = map.get(coordinate);
                if (y == 0 || oldValue == '#' || oldValue == '.') {
                    continue;
                }

                // slide
                int tmpY = y;
                while (tmpY > 0 && map.get(new Coordinate(x, tmpY - 1)).equals('.')) {
                    tmpY--;
                }
                map.put(new Coordinate(x, y), '.');
                map.put(new Coordinate(x, tmpY), oldValue);
            }

        }

        return map;
    }

    /*private Map<Coordinate, Character> tiltWest(int maxX, int maxY, Map<Coordinate, Character> map) {
        Map<Coordinate, Character> newMap = new HashMap<>();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Character oldValue = map.get(coordinate);
                if (x == 0 || oldValue == '#' || oldValue == '.') {
                    newMap.put(coordinate, oldValue);
                    continue;
                }

                // slide
                int tmpX = x;
                while (tmpX > 0 && isEmpty(new Coordinate(tmpX - 1, y), newMap)) {
                    tmpX--;
                }
                newMap.put(new Coordinate(x, y), '.');
                newMap.put(new Coordinate(tmpX, y), oldValue);
            }

        }

        return newMap;
    }

    private Map<Coordinate, Character> tiltSouth(int maxX, int maxY, Map<Coordinate, Character> map) {
        Map<Coordinate, Character> newMap = new HashMap<>();
        for (int x = 0; x <= maxX; x++) {
            for (int y = maxY; y >= 0; y--) {
                Coordinate coordinate = new Coordinate(x, y);
                Character oldValue = map.get(coordinate);
                if (y == maxY || oldValue == '#' || oldValue == '.') {
                    newMap.put(coordinate, oldValue);
                    continue;
                }

                // slide
                int tmpY = y;
                while (tmpY < maxY && isEmpty(new Coordinate(x, tmpY + 1), newMap)) {
                    tmpY++;
                }
                newMap.put(new Coordinate(x, y), '.');
                newMap.put(new Coordinate(x, tmpY), oldValue);
            }

        }

        return newMap;
    }

    private Map<Coordinate, Character> tiltEast(int maxX, int maxY, Map<Coordinate, Character> map) {
        Map<Coordinate, Character> newMap = new HashMap<>();
        for (int y = 0; y <= maxY; y++) {
            for (int x = maxX; x >= 0; x--) {
                Coordinate coordinate = new Coordinate(x, y);
                Character oldValue = map.get(coordinate);
                if (x == maxX || oldValue == '#' || oldValue == '.') {
                    newMap.put(coordinate, oldValue);
                    continue;
                }

                // slide
                int tmpX = x;
                while (tmpX < maxX && isEmpty(new Coordinate(tmpX + 1, y), newMap)) {
                    tmpX++;
                }
                newMap.put(new Coordinate(x, y), '.');
                newMap.put(new Coordinate(tmpX, y), oldValue);
            }

        }

        return newMap;
    }*/

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
