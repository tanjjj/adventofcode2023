import utils.Coordinate;
import utils.Parser;
import utils.pipes.*;

import java.util.*;

// 2023 puzzle 10
public class Day10 implements DayX {
    private final Map<Coordinate, Pipe> map = new HashMap<>();

    @Override
    public void run() throws Exception {
        List<String> input = Parser.parseInputAsString("day10.txt");

        Coordinate start = null;
        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                Coordinate coord = new Coordinate(x, y);
                map.put(coord, getPipe(str.charAt(x)));
                if (str.charAt(x) == 'S') {
                    start = coord;
                }
            }
        }

        if (start == null) {
            throw new Exception("start point not found");
        }

        Set<Coordinate> visited = new HashSet<>();
        Set<Coordinate> coords = new HashSet<>();
        coords.add(start);
        int result = 0;
        while (true) {
            Set<Coordinate> nextCoords = new HashSet<>();
            for (Coordinate coord : coords) {
                visited.add(coord);
                nextCoords.addAll(getConnectedCoords(coord, visited));
            }
            if (nextCoords.isEmpty()) {
                break;
            }
            result++;
            coords = nextCoords;
        }

        System.out.println(result);
    }

    private Pipe getPipe(char ch) throws Exception {
        switch (ch) {
            case '|':
                return new VerticalPipe();
            case '-':
                return new HorizontalPipe();
            case 'L':
                return new NEPIpe();
            case 'J':
                return new NWPIpe();
            case '7':
                return new SWPipe();
            case 'F':
                return new SEPipe();
            case '.':
                return new Ground();
            case 'S':
                return new Start();
        }
        throw new Exception("no pipe type found");
    }

    List<Coordinate> getConnectedCoords(Coordinate current, Set<Coordinate> visited) {
        List<Coordinate> connectedCoords = new ArrayList<>();
        for (Pipe.Direction direction : map.get(current).getDirections()) {
            Coordinate nextCoord = getNext(direction, current);
            if (nextCoord != null && !visited.contains(nextCoord)) {
                connectedCoords.add(nextCoord);
            }
        }
        return connectedCoords;
    }

    Coordinate getNext(Pipe.Direction direction, Coordinate current) {
        int x = current.x;
        int y = current.y;
        Coordinate nextCoord;
        switch (direction) {
            case NORTH:
                nextCoord = new Coordinate(x, y - 1);
                if (map.containsKey(nextCoord)) {
                    List<Pipe.Direction> nextPipeDirections = map.get(nextCoord).getDirections();
                    if (nextPipeDirections.contains(Pipe.Direction.SOUTH)) {
                        return nextCoord;
                    }
                }
                break;
            case SOUTH:
                nextCoord = new Coordinate(x, y + 1);
                if (map.containsKey(nextCoord)) {
                    List<Pipe.Direction> nextPipeDirections = map.get(nextCoord).getDirections();
                    if (nextPipeDirections.contains(Pipe.Direction.NORTH)) {
                        return nextCoord;
                    }
                }
                break;
            case EAST:
                nextCoord = new Coordinate(x + 1, y);
                if (map.containsKey(nextCoord)) {
                    List<Pipe.Direction> nextPipeDirections = map.get(nextCoord).getDirections();
                    if (nextPipeDirections.contains(Pipe.Direction.WEST)) {
                        return nextCoord;
                    }
                }
                break;
            case WEST:
                nextCoord = new Coordinate(x - 1, y);
                if (map.containsKey(nextCoord)) {
                    List<Pipe.Direction> nextPipeDirections = map.get(nextCoord).getDirections();
                    if (nextPipeDirections.contains(Pipe.Direction.EAST)) {
                        return nextCoord;
                    }
                }
                break;
        }
        return null;
    }
}
