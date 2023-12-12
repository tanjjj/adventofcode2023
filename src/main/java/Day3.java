import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 2023 puzzle 3
// for each number found, validate if adjacent chars have symbol
// x -1, x + 1, y -1, y + 1
public class Day3 implements DayX {
    private Map<Coordinate, Character> map;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day3.txt");
        map = MapHelper.generateMap(input);

        List<Integer> result = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                StringBuilder number = new StringBuilder();
                List<Coordinate> coords = new ArrayList<>();
                while (x < str.length() && Character.isDigit(str.charAt(x))) {
                    number.append(str.charAt(x));
                    coords.add(new Coordinate(x, y));
                    x++;
                }
                if (coords.stream()
                        .anyMatch(this::hasAdjacentSymbol)) {
                    result.add(Integer.parseInt(number.toString()));
                }
            }
        }

        System.out.println(result);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private boolean hasAdjacentSymbol(Coordinate coord) {
        for (int x = coord.x - 1; x <= coord.x + 1; x++) {
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                Coordinate tmp = new Coordinate(x, y);
                if (map.containsKey(tmp)) {
                    Character ch = map.get(tmp);
                    if ((!Character.isDigit(ch)) && ch != '.') {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
