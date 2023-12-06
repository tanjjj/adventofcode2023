import utils.Coordinate;
import utils.Parser;

import java.util.*;

// 2023 puzzle 3
public class Day3B implements DayX {
    private final Map<Coordinate, Character> map = new HashMap<>();
    private final Map<Coordinate, Integer> numbers = new HashMap<>();

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day3.txt");
        generateMap(input);

        Map<Coordinate, Set<Coordinate>> result = new HashMap<>();
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

                String n = number.toString();
                Integer num = n.isEmpty() ? 0 : Integer.parseInt(n);
                Coordinate c = n.isEmpty() ? null : coords.get(0);
                numbers.put(c, num);

                coords.stream()
                        .flatMap(co -> getAdjacentSymbols(co).stream())
                        .forEach(symbol -> {
                            if (result.containsKey(symbol)) {
                                result.get(symbol).add(c);
                            } else {
                                Set<Coordinate> set = new HashSet<>();
                                set.add(c);
                                result.put(symbol, set);
                            }
                        });
            }
        }

        int sum = result.values().stream()
                .filter(coordinates -> coordinates.size() >= 2)
                .map(v -> {
                    List<Coordinate> l = new ArrayList<>(v);
                    return numbers.get(l.get(0)) * numbers.get(l.get(1));
                })
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(result);
        System.out.println(sum);
    }


    private void generateMap(List<String> input) {
        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                map.put(new Coordinate(x, y), str.charAt(x));
            }
        }
    }

    private List<Coordinate> getAdjacentSymbols(Coordinate coord) {
        Set<Coordinate> symbols = new HashSet<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++) {
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                Coordinate tmp = new Coordinate(x, y);
                if (map.containsKey(tmp)) {
                    Character ch = map.get(tmp);
                    if (ch == '*') {
                        symbols.add(tmp);
                    }
                }
            }
        }
        return new ArrayList<>(symbols);
    }
}
