import utils.Coordinate;
import utils.MapHelper;
import utils.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 14
public class Day14BNew implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day14.txt");
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        char[][] map = new char[maxX + 1][maxX + 1];
        List<char[]> tmp = input.stream()
                .map(String::toCharArray)
                .collect(Collectors.toList());
        for (int y = 0; y <= maxY; y++) {
            map[y] = tmp.get(y);
        }


        int remainingCircles = 1000000000;
        while (remainingCircles > 0) {
            tiltNorth(maxX, maxY, map);
            tiltWest(maxX, maxY, map);
            tiltSouth(maxX, maxY, map);
            tiltEast(maxX, maxY, map);
            remainingCircles--;
            System.out.println(remainingCircles);
        }

        int result = calculateLoad(maxX, maxY, map);
        System.out.println("result " + result);
    }

    private void tiltNorth(int maxX, int maxY, char[][] map) {
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                char oldValue = map[y][x];
                if (y == 0 || oldValue == '#' || oldValue == '.') {
                    continue;
                }

                // slide
                int tmpY = y;
                while (tmpY > 0 && map[tmpY - 1][x] == '.') {
                    tmpY--;
                }

                map[y][x] = '.';
                map[tmpY][x] = oldValue;
            }
        }
    }

    private void tiltWest(int maxX, int maxY, char[][] map) {
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                char oldValue = map[y][x];
                if (x == 0 || oldValue == '#' || oldValue == '.') {
                    continue;
                }

                // slide
                int tmpX = x;
                while (tmpX > 0 && map[y][tmpX - 1] == '.') {
                    tmpX--;
                }

                map[y][x] = '.';
                map[y][tmpX] = oldValue;
            }
        }
    }

    private void tiltSouth(int maxX, int maxY, char[][] map) {
        for (int x = 0; x <= maxX; x++) {
            for (int y = maxY; y >= 0; y--) {
                char oldValue = map[y][x];
                if (y == maxY || oldValue == '#' || oldValue == '.') {
                    continue;
                }

                // slide
                int tmpY = y;
                while (tmpY < maxY && map[tmpY + 1][x] == '.') {
                    tmpY++;
                }

                map[y][x] = '.';
                map[tmpY][x] = oldValue;
            }
        }
    }

    private void tiltEast(int maxX, int maxY, char[][] map) {
        for (int y = 0; y <= maxY; y++) {
            for (int x = maxX; x >= 0; x--) {
                char oldValue = map[y][x];
                if (x == maxX || oldValue == '#' || oldValue == '.') {
                    continue;
                }

                // slide
                int tmpX = x;
                while (tmpX < maxX && map[y][tmpX + 1] == '.') {
                    tmpX++;
                }

                map[y][x] = '.';
                map[y][tmpX] = oldValue;
            }
        }
    }

    private int calculateLoad(int maxX, int maxY, char[][] map) {
        int result = 0;
        int load = maxY + 1;
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if (map[y][x] == 'O') {
                    result += load;
                }
            }
            load--;
        }

        return result;
    }

}
