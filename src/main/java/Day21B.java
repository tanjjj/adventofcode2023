import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 21 part 2
public class Day21B implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day21.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;

        Coordinate start = findStart(map);// 65,65
        map.put(start, '.');


        // find the shortest path from start to each tile
        Map<Coordinate, Integer> shortestDistances = new HashMap<>();
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(start);
        shortestDistances.put(start, 0);
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            List<Coordinate> next = getNextStep(current, map, maxX, maxY);
            int distance = shortestDistances.get(current) + 1;
            for (Coordinate c : next) {
                if (distance < shortestDistances.getOrDefault(c, Integer.MAX_VALUE)) {
                    shortestDistances.put(c, distance);
                    queue.add(c);
                }
            }
        }

        // calculate final result
        int totalSteps = 26501365; // 26501365 = 65 + (202300 * 131)
        long numberOfMapsFromStart = (totalSteps - 65) / 131;

        long oddPlotsPerMap = shortestDistances.entrySet().stream()
                .filter(e -> e.getValue() % 2 == 1)
                .count();
        long evenPlotsPerMap = shortestDistances.entrySet().stream()
                .filter(e -> e.getValue() % 2 == 0)
                .count();
        long totalOddPlots = (numberOfMapsFromStart + 1) * (numberOfMapsFromStart + 1) * oddPlotsPerMap;
        long totalEvenPlots = numberOfMapsFromStart * numberOfMapsFromStart * evenPlotsPerMap;

        long oddPlotsInCorner = shortestDistances.entrySet().stream()
                .filter(e -> e.getValue() > 65)
                .filter(e -> e.getValue() % 2 == 1)
                .count();
        long evenPlotsInCorner = shortestDistances.entrySet().stream()
                .filter(e -> e.getValue() > 65)
                .filter(e -> e.getValue() % 2 == 0)
                .count();
        long totalOddPlotsInCorner = (numberOfMapsFromStart + 1) * oddPlotsInCorner;
        long totalEvenPlotsInCorner = numberOfMapsFromStart * evenPlotsInCorner;

        System.out.println(totalOddPlots + totalEvenPlots - totalOddPlotsInCorner + totalEvenPlotsInCorner);
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
