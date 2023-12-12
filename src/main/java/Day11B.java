import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 11
public class Day11B implements DayX {
    private Map<Coordinate, Character> map;
    private final long expansionRate = 1000000;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day11.txt");
        map = MapHelper.generateMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        List<Integer> emptyColumns = getEmptyColumns(maxX);
        List<Integer> emptyRows = getEmptyRows(maxY);

        List<Coordinate> galaxies = map.entrySet().stream()
                .filter(entry -> entry.getValue().equals('#'))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Long> result = getDistances(galaxies, emptyColumns, emptyRows);
        System.out.println(result.stream().mapToLong(Long::longValue).sum());
    }

    private List<Integer> getEmptyRows(int maxY) {
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
        return emptyRows;
    }

    private List<Integer> getEmptyColumns(int maxX) {
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
        return emptyColumns;
    }

    private List<Long> getDistances(List<Coordinate> galaxies, List<Integer> emptyColumns, List<Integer> emptyRows) {
        List<Long> result = new ArrayList<>();
        if (galaxies.size() == 1) {
            return new ArrayList<>();
        }

        Coordinate start = galaxies.get(0);
        for (int i = 1; i < galaxies.size(); i++) {
            Coordinate end = galaxies.get(i);
            // horizon distance
            long emptyColumnsInBetween = emptyColumns.stream()
                    .filter(c -> c > Math.min(start.x, end.x) && c < Math.max(start.x, end.x))
                    .count();
            long emptyRowsInBetween = emptyRows.stream()
                    .filter(r -> r > Math.min(start.y, end.y) && r < Math.max(start.y, end.y))
                    .count();
            result.add(Math.abs(start.x - end.x) + emptyColumnsInBetween * (expansionRate - 1)
                    + Math.abs(start.y - end.y) + emptyRowsInBetween * (expansionRate - 1));
        }

        result.addAll(getDistances(galaxies.subList(1, galaxies.size()), emptyColumns, emptyRows));
        return result;
    }

}
