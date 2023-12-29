import utils.Coordinate;
import utils.Direction;
import utils.MapHelper;
import utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2023 puzzle 16
public class Day16 implements DayX {
    @Override
    public void run() throws Exception {
        List<String> input = Parser.parseInputAsString("day16.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        Map<Coordinate, Boolean> energyMap = new HashMap<>();
        Map<Coordinate, List<Direction>> visited = new HashMap<>();
        Coordinate start = new Coordinate(0, 0);
        energyMap.put(start, true);
        List<Direction> tmp = new ArrayList<>();
        tmp.add(Direction.RIGHT);
        visited.put(start, tmp);
        nextStep(start, Direction.RIGHT, map, energyMap, input.get(0).length() - 1, input.size() - 1, visited);
        System.out.println(energyMap.size());
    }

    private void nextStep(Coordinate current, Direction direction,
                          Map<Coordinate, Character> map, Map<Coordinate, Boolean> energyMap,
                          int maxX, int maxY, Map<Coordinate, List<Direction>> visited) throws Exception {
        List<Direction> visitedList = visited.getOrDefault(current, new ArrayList<>());
        visitedList.add(direction);
        visited.put(current, visitedList);
        Coordinate nextCoord = getNextCoordinate(current, direction);
        if (nextCoord.x < 0 || nextCoord.y < 0 || nextCoord.x > maxX || nextCoord.y > maxY) {
            return;
        }

        if (visited.getOrDefault(nextCoord, new ArrayList<>()).contains(direction)) {
            return;
        }

        energyMap.put(nextCoord, true);
        System.out.println(nextCoord);
        Character nextCell = map.get(nextCoord);
        if (nextCell == '.') {
            nextStep(nextCoord, direction, map, energyMap, maxX, maxY, visited);
        } else if (nextCell == '|') {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                nextStep(nextCoord, direction, map, energyMap, maxX, maxY, visited);
            } else {
                nextStep(nextCoord, Direction.UP, map, energyMap, maxX, maxY, visited);
                nextStep(nextCoord, Direction.DOWN, map, energyMap, maxX, maxY, visited);
            }
        } else if (nextCell == '-') {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                nextStep(nextCoord, direction, map, energyMap, maxX, maxY, visited);
            } else {
                nextStep(nextCoord, Direction.LEFT, map, energyMap, maxX, maxY, visited);
                nextStep(nextCoord, Direction.RIGHT, map, energyMap, maxX, maxY, visited);
            }
        } else if (nextCell == '/') {
            if (direction == Direction.RIGHT) {
                nextStep(nextCoord, Direction.UP, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.LEFT) {
                nextStep(nextCoord, Direction.DOWN, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.UP) {
                nextStep(nextCoord, Direction.RIGHT, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.DOWN) {
                nextStep(nextCoord, Direction.LEFT, map, energyMap, maxX, maxY, visited);
            }
        } else if (nextCell == '\\') {
            if (direction == Direction.RIGHT) {
                nextStep(nextCoord, Direction.DOWN, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.LEFT) {
                nextStep(nextCoord, Direction.UP, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.UP) {
                nextStep(nextCoord, Direction.LEFT, map, energyMap, maxX, maxY, visited);
            } else if (direction == Direction.DOWN) {
                nextStep(nextCoord, Direction.RIGHT, map, energyMap, maxX, maxY, visited);
            }
        }
    }

    private Coordinate getNextCoordinate(Coordinate current, Direction direction) throws Exception {
        switch (direction) {
            case UP:
                return new Coordinate(current.x, current.y - 1);
            case DOWN:
                return new Coordinate(current.x, current.y + 1);
            case LEFT:
                return new Coordinate(current.x - 1, current.y);
            case RIGHT:
                return new Coordinate(current.x + 1, current.y);
        }
        throw new Exception("direction not found");
    }
}
