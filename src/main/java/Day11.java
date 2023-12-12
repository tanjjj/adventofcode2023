import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 11
public class Day11 implements DayX {
    private Map<Coordinate, Character> map;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day11.txt");
        map = MapHelper.generateMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        expandMap(maxX, maxY);

        List<Coordinate> galaxies = map.entrySet().stream()
                .filter(entry -> entry.getValue().equals('#'))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> result = getDistances(galaxies);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private void expandMap(int maxX, int maxY) {
        List<Integer> emptyColumns = new ArrayList<>();
        // find empty columns
        for (int i = maxX; i >= 0; i--) {
            int x = i;
            boolean isEmpty = map.entrySet().stream()
                    .filter(entry -> entry.getKey().x == x)
                    .allMatch(entry -> entry.getValue().equals('.'));
            if (isEmpty) {
                emptyColumns.add(i);
            }
        }

        List<Integer> emptyRows = new ArrayList<>();
        // find empty columns
        for (int j = maxY; j >= 0; j--) {
            int y = j;
            boolean isEmpty = map.entrySet().stream()
                    .filter(entry -> entry.getKey().y == y)
                    .allMatch(entry -> entry.getValue().equals('.'));
            if (isEmpty) {
                emptyRows.add(j);
            }
        }

        int newMaxX = maxX;
        // double empty column
        for (int c : emptyColumns) {
            for (int x = newMaxX; x >= c; x--) {
                for (int y = 0; y <= maxY; y++) {
                    map.put(new Coordinate(x + 1, y), map.get(new Coordinate(x, y)));
                }
            }
            newMaxX++;
        }


        int newMaxY = maxY;
        // double empty rows
        for (int r : emptyRows) {
            for (int y = newMaxY; y >= r; y--) {
                for (int x = 0; x <= newMaxX; x++) {
                    map.put(new Coordinate(x, y + 1), map.get(new Coordinate(x, y)));
                }
            }
            newMaxY++;
        }
    }

    private List<Integer> getDistances(List<Coordinate> galaxies) {
        List<Integer> result = new ArrayList<>();
        if (galaxies.size() == 1) {
            return new ArrayList<>();
        }

        Coordinate start = galaxies.get(0);
        for (int i = 1; i < galaxies.size(); i++) {
            Coordinate end = galaxies.get(i);
            result.add(Math.abs(start.x - end.x) + Math.abs(start.y - end.y));
        }

        result.addAll(getDistances(galaxies.subList(1, galaxies.size())));
        return result;
    }

}
