import utils.Coordinate;
import utils.day16.Beam;
import utils.day16.Direction;
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

        List<Beam> beams = List.of(new Beam(start, Direction.RIGHT));
        while (!beams.isEmpty()) {
            List<Beam> tmp = new ArrayList<>();
            for (Beam beam : beams) {
                int maxX = input.get(0).length() - 1;
                int maxY = input.size() - 1;
                tmp.addAll(nextStep(beam.coordinate, beam.direction, map, energyMap, maxX, maxY, visited));
            }
            beams = tmp;
        }


        System.out.println(energyMap.size());
    }

    private List<Beam> nextStep(Coordinate current, Direction direction,
                                Map<Coordinate, Character> map, Map<Coordinate, Boolean> energyMap,
                                int maxX, int maxY, Map<Coordinate, List<Direction>> visited) throws Exception {
        if (current.x < 0 || current.y < 0 || current.x > maxX || current.y > maxY) {
            return new ArrayList<>();
        }

        System.out.println(current + " " + direction);
        List<Direction> visitedList = visited.getOrDefault(current, new ArrayList<>());
        if (visitedList.contains(direction)) {
            return new ArrayList<>(); // already visited
        }
        visitedList.add(direction);
        visited.put(current, visitedList);

        energyMap.put(current, true);
        Character currentCell = map.get(current);
        if (currentCell == '.') {
            return List.of(new Beam(getNextCoordinate(current, direction), direction));
        } else if (currentCell == '|') {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                return List.of(new Beam(getNextCoordinate(current, direction), direction));
            } else {
                return List.of(
                        new Beam(getNextCoordinate(current, Direction.UP), Direction.UP),
                        new Beam(getNextCoordinate(current, Direction.DOWN), Direction.DOWN));
            }
        } else if (currentCell == '-') {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                return List.of(new Beam(getNextCoordinate(current, direction), direction));
            } else {
                return List.of(
                        new Beam(getNextCoordinate(current, Direction.LEFT), Direction.LEFT),
                        new Beam(getNextCoordinate(current, Direction.RIGHT), Direction.RIGHT));
            }
        } else if (currentCell == '/') {
            if (direction == Direction.RIGHT) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.UP),  Direction.UP));
            } else if (direction == Direction.LEFT) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.DOWN),  Direction.DOWN));
            } else if (direction == Direction.UP) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.RIGHT),  Direction.RIGHT));
            } else if (direction == Direction.DOWN) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.LEFT),  Direction.LEFT));
            }
        } else if (currentCell == '\\') {
            if (direction == Direction.RIGHT) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.DOWN),  Direction.DOWN));
            } else if (direction == Direction.LEFT) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.UP),  Direction.UP));
            } else if (direction == Direction.UP) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.LEFT),  Direction.LEFT));
            } else if (direction == Direction.DOWN) {
                return List.of(new Beam(getNextCoordinate(current,  Direction.RIGHT),  Direction.RIGHT));
            }
        }

        return new ArrayList<>();
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
