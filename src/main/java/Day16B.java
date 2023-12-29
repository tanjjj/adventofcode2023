import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;
import utils.day16.Beam;
import utils.day16.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2023 puzzle 16 part 2
public class Day16B implements DayX {
    @Override
    public void run() throws Exception {
        List<String> input = Parser.parseInputAsString("day16.txt");
        Map<Coordinate, Character> map = MapHelper.generateMap(input);
        int result = 0;
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        for (int x = 0; x <= maxX; x++) {
            Map<Coordinate, Boolean> energyMap = new HashMap<>();
            Map<Coordinate, List<Direction>> visited = new HashMap<>();
            energize(new Coordinate(x, 0), Direction.DOWN, map, energyMap, visited, maxX, maxY);
            result = Math.max(energyMap.size(), result);

            energyMap = new HashMap<>();
            visited = new HashMap<>();
            energize(new Coordinate(x, maxY), Direction.UP, map, energyMap, visited, maxX, maxY);
            result = Math.max(energyMap.size(), result);

        }

        for (int y = 0; y <= maxY; y++) {
            Map<Coordinate, Boolean> energyMap = new HashMap<>();
            Map<Coordinate, List<Direction>> visited = new HashMap<>();
            energize(new Coordinate(0, y), Direction.RIGHT, map, energyMap, visited, maxX, maxY);
            result = Math.max(energyMap.size(), result);

            energyMap = new HashMap<>();
            visited = new HashMap<>();
            energize(new Coordinate(maxX, y), Direction.LEFT, map, energyMap, visited, maxX, maxY);
            result = Math.max(energyMap.size(), result);
        }

        System.out.println(result);
    }

    private static void energize(Coordinate start,
                                 Direction startDirection,
                                 Map<Coordinate, Character> map, Map<Coordinate, Boolean> energyMap,
                                 Map<Coordinate, List<Direction>> visited,
                                 int maxX,
                                 int maxY) throws Exception {
        List<Beam> beams = List.of(new Beam(start, startDirection));
        while (!beams.isEmpty()) {
            List<Beam> tmp = new ArrayList<>();
            for (Beam beam : beams) {
                tmp.addAll(Day16.nextStep(beam.coordinate, beam.direction, map, energyMap, maxX, maxY, visited));
            }
            beams = tmp;
        }
    }

}
