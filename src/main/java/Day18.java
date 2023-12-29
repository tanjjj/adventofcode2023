import utils.Coordinate;
import utils.Direction;
import utils.Parser;
import utils.day18.DigInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 18
public class Day18 implements DayX {
    @Override
    public void run() throws Exception {
        List<String> input = Parser.parseInputAsString("day18.txt");
        List<DigInstruction> instructions = input.stream()
                .map(this::convertToInstruction)
                .collect(Collectors.toList());

        List<Coordinate> trench = new ArrayList<>();
        Coordinate current = new Coordinate(0, 0);
        for (DigInstruction instruction : instructions) {
            List<Coordinate> coords = dig(current, instruction);
            trench.addAll(coords);
            current = coords.get(coords.size() - 1);
        }

        // todo fix how we calculate the area
        List<Integer> Ys = trench.stream()
                .map(c -> c.y)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        long result = 0;
        for (int y : Ys) {
            List<Integer> edge = trench.stream()
                    .filter(c -> c.y == y)
                    .map(c -> c.x)
                    .sorted()
                    .collect(Collectors.toList());
            result += Math.abs(edge.get(edge.size() - 1) - edge.get(0)) + 1;
        }

        System.out.println(result);
    }

    private DigInstruction convertToInstruction(String str) {
        String[] splits = str.split(" ");
        return new DigInstruction(splits[0], Integer.parseInt(splits[1]), splits[2]);
    }

    private List<Coordinate> dig(Coordinate current, DigInstruction instruction) {
        List<Coordinate> result = new ArrayList<>();
        int i = instruction.distance;
        while (i > 0) {
            Coordinate next = getNext(current, instruction.direction);
            result.add(next);
            current = next;
            i--;
        }
        return result;
    }

    private Coordinate getNext(Coordinate current, Direction direction) {
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
        throw new RuntimeException("direction not found");
    }
}
