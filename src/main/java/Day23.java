import utils.MapHelper;
import utils.Parser;

import utils.Coordinate;

import java.util.List;
import java.util.Map;

// 2023 puzzle 23
public class Day23 implements DayX {
    private static Map<Coordinate, Character> originalMap;
    private static int maxX;
    private static int maxY;
    private static Coordinate end;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day23.txt");
        originalMap = MapHelper.generateMap(input);
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int startX = input.get(0).indexOf(".");
        Coordinate start = new Coordinate(startX, 0);
        int endX = input.get(input.size() - 1).indexOf(".");
        end = new Coordinate(endX, input.size() - 1);

        maxX = input.get(0).length() - 1;
        maxY = input.size() - 1;

        long result = getMaxPathLength(start, map);
        System.out.println(result);
    }

    private long getMaxPathLength(Coordinate start, Map<Coordinate, Character> map) {
        if (map.get(start) == 'O') {
            return 0;
        } else if (start.equals(end)) {
            return calculatePathLength(map);
        } else {
            map.put(start, 'O');
            char current = originalMap.get(start);
            long max = 0;
            if (current == '>') {
                long nextLength = getMaxPathLength(new Coordinate(start.x + 1, start.y), map);
                if (nextLength > max) {
                    max = nextLength;
                }
            } else if (current == '<') {
                long nextLength = getMaxPathLength(new Coordinate(start.x - 1, start.y), map);
                if (nextLength > max) {
                    max = nextLength;
                }
            } else if (current == '^') {
                long nextLength = getMaxPathLength(new Coordinate(start.x, start.y - 1), map);
                if (nextLength > max) {
                    max = nextLength;
                }
            } else if (current == 'v') {
                long nextLength = getMaxPathLength(new Coordinate(start.x, start.y + 1), map);
                if (nextLength > max) {
                    max = nextLength;
                }
            } else {
                Coordinate next = new Coordinate(start.x, start.y - 1);
                if (isValidStep(next)) {
                    long nextLength = getMaxPathLength(next, map);
                    if (nextLength > max) {
                        max = nextLength;
                    }
                }

                next = new Coordinate(start.x + 1, start.y);
                if (isValidStep(next)) {
                    long nextLength = getMaxPathLength(next, map);
                    if (nextLength > max) {
                        max = nextLength;
                    }
                }

                next = new Coordinate(start.x, start.y + 1);
                if (isValidStep(next)) {
                    long nextLength = getMaxPathLength(next, map);
                    if (nextLength > max) {
                        max = nextLength;
                    }
                }

                next = new Coordinate(start.x - 1, start.y);
                if (isValidStep(next)) {
                    long nextLength = getMaxPathLength(next, map);
                    if (nextLength > max) {
                        max = nextLength;
                    }
                }
            }
            map.put(start, '.');
            return max;
        }
    }

    private boolean isValidStep(Coordinate coordinate) {
        return coordinate.x >= 0 && coordinate.x <= maxX
                && coordinate.y >= 0 && coordinate.y <= maxY
                && originalMap.get(coordinate) != '#';
    }

    private long calculatePathLength(Map<Coordinate, Character> map) {
        return map.values().stream()
                .filter(v -> v == 'O')
                .count();
    }
}
