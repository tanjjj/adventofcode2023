import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 21
public class Day21 implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day21.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int maxX = input.get(0).length();
        int maxY = input.size();

        Coordinate start = findStart(map);
        map.put(start, '.');

        int i = 1;
        Set<Coordinate> current = Set.of(start);
        while (i <= 64) {
            current = current.stream()
                    .flatMap(c -> getNextStep(c, map, maxX, maxY).stream())
                    .collect(Collectors.toSet());
            i++;
        }

        System.out.println(current.size());
    }

    private Coordinate findStart(Map<Coordinate, Character> map) {
        return map.entrySet().stream()
                .filter(e -> e.getValue() == 'S')
                .map(Map.Entry::getKey)
                .findAny()
                .get();
    }

    private List<Coordinate> getNextStep(Coordinate coordinate, Map<Coordinate, Character> map, int maxX, int maxY) {
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(coordinate.x, coordinate.y - 1));
        coordinates.add(new Coordinate(coordinate.x, coordinate.y + 1));
        coordinates.add(new Coordinate(coordinate.x - 1, coordinate.y));
        coordinates.add(new Coordinate(coordinate.x + 1, coordinate.y));
        return coordinates.stream()
                .filter(c -> c.x >= 0 && c.y >= 0 && c.x <= maxX && c.y <= maxY && map.get(c) == '.')
                .collect(Collectors.toList());
    }
}
