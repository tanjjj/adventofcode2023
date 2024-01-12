import utils.Coordinate;
import utils.Parser;

import java.util.List;

// 2023 puzzle 23
// stackoverflow - use -Xss1m in run configuration, use 2D array instead of map
public class Day23B implements DayX {
    private static Coordinate end;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day23.txt");
        int startX = input.get(0).indexOf(".");
        Coordinate start = new Coordinate(startX, 0);
        int endX = input.get(input.size() - 1).indexOf(".");
        end = new Coordinate(endX, input.size() - 1);

        int result = getMaxPathLength(start, readGrid(input));
        System.out.println(result);
    }

    private char[][] readGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            grid[y] = line.toCharArray();
        }
        return grid;
    }

    private int getMaxPathLength(Coordinate start, char[][] grid) {
        if (grid[start.y][start.x] == 'O') {
            return 0;
        } else if (start.equals(end)) {
            return calculatePathLength(grid);
        } else {
            grid[start.y][start.x] = 'O';
            int maxLength = 0;
            if (start.y - 1 >= 0 && grid[start.y - 1][start.x] != '#') {
                int nextLength = getMaxPathLength(new Coordinate(start.x, start.y - 1), grid);
                if (nextLength > maxLength) {
                    maxLength = nextLength;
                }
            }
            if (start.x + 1 < grid[start.y].length && grid[start.y][start.x + 1] != '#') {
                int nextLength = getMaxPathLength(new Coordinate(start.x + 1, start.y), grid);
                if (nextLength > maxLength) {
                    maxLength = nextLength;
                }
            }
            if (start.y + 1 < grid.length && grid[start.y + 1][start.x] != '#') {
                int nextLength = getMaxPathLength(new Coordinate(start.x, start.y + 1), grid);
                if (nextLength > maxLength) {
                    maxLength = nextLength;
                }
            }
            if (start.x - 1 >= 0 && grid[start.y][start.x - 1] != '#') {
                int nextLength = getMaxPathLength(new Coordinate(start.x - 1, start.y), grid);
                if (nextLength > maxLength) {
                    maxLength = nextLength;
                }
            }

            grid[start.y][start.x] = '.';
            return maxLength;
        }
    }

    private int calculatePathLength(char[][] grid) {
        int result = 0;
        for (char[] chars : grid) {
            for (char ch : chars) {
                if (ch == 'O') {
                    result++;
                }
            }
        }
        return result;
    }
}
