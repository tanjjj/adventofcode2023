import utils.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 14 part 2 - array faster than coordinate
public class Day14BNew implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day14.txt");
        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        char[][] map = new char[maxY + 1][maxX + 1];
        List<char[]> tmp = input.stream()
                .map(String::toCharArray)
                .collect(Collectors.toList());
        for (int y = 0; y <= maxY; y++) {
            map[y] = tmp.get(y);
        }

        List<String> history = new ArrayList<>();
        history.add(convertToString(map));
        int cycles = 0;
        String str = "";
        while (cycles < 300) {
            tiltNorth(maxX, maxY, map);
            tiltWest(maxX, maxY, map);
            tiltSouth(maxX, maxY, map);
            tiltEast(maxX, maxY, map);
            str = convertToString(map);
            if (history.contains(str)) {
                // stop when we find a loop
                break;
            }
            history.add(str);
            cycles++;
        }

        int beforeLoop = history.indexOf(str);
        String final_grid = history.get((1000000000 - beforeLoop) % (cycles + 1 - beforeLoop)
                + beforeLoop);

        char[][] fMap = new char[maxY + 1][maxX + 1];
        for (int i = 0; i <= maxY; i++) {
            fMap[i] = final_grid.substring(0, maxX + 1).toCharArray();
            final_grid = final_grid.substring(maxX + 1);
        }
        System.out.println(calculateLoad(maxX, maxY, fMap));
    }

    private void tiltNorth(int maxX, int maxY, char[][] map) {
        for (int x = 0; x <= maxX; x++) {
            for (int y = 1; y <= maxY; y++) {
                char oldValue = map[y][x];
                if (oldValue == 'O') {
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
    }

    private void tiltWest(int maxX, int maxY, char[][] map) {
        for (int y = 0; y <= maxY; y++) {
            for (int x = 1; x <= maxX; x++) {
                char oldValue = map[y][x];
                if (oldValue == 'O') {
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
    }

    private void tiltSouth(int maxX, int maxY, char[][] map) {
        for (int x = 0; x <= maxX; x++) {
            for (int y = maxY - 1; y >= 0; y--) {
                char oldValue = map[y][x];
                if (oldValue == 'O') {
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
    }

    private void tiltEast(int maxX, int maxY, char[][] map) {
        for (int y = 0; y <= maxY; y++) {
            for (int x = maxX - 1; x >= 0; x--) {
                char oldValue = map[y][x];
                if (oldValue == 'O') {
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

    private String convertToString(char[][] map) {
        StringBuilder result = new StringBuilder();
        for (char[] chars : map) {
            result.append(chars);
        }
        return result.toString();
    }
}
