import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 2
// Determine which games would have been possible if the bag had been loaded with only
// 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
// Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
public class Day2 implements DayX {
    private static final int RED_MAX = 12;
    private static final int GREEN_MAX = 13;
    private static final int BLUE_MAX = 14;

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
        Map<Integer, List<String[]>> games = input
                .stream()
                .collect(Collectors.toMap(this::getGameId, this::getGames));

        List<Integer> result = games.entrySet()
                .stream()
                .filter(game -> validateGame(game.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(result);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private int getGameId(String str) {
        return Integer.parseInt(str.split(":")[0].substring(5));
    }

    private List<String[]> getGames(String str) {
        String game = str.split(":")[1];
        return Arrays.stream(game.split(";"))
                .map(g -> g.split(","))
                .collect(Collectors.toList());
    }

    // true if all game valid
    private boolean validateGame(List<String[]> games) {
        long impossibleGames = games.stream()
                .map(this::convertGame)
                .filter(this::isNotPossible)
                .count();
        return impossibleGames == 0;
    }

    private Map<String, Integer> convertGame(String[] strs) {
        return Arrays.stream(strs)//" 3 blue"
                .map(str -> str.split(" "))
                .collect(Collectors.toMap(e -> e[2], e -> Integer.parseInt(e[1])));
    }

    private boolean isNotPossible(Map<String, Integer> game) {
        if (game.containsKey("red") && game.get("red") > 12) {
            return true;
        }
        if (game.containsKey("green") && game.get("green") > 13) {
            return true;
        }
        if (game.containsKey("blue") && game.get("blue") > 14) {
            return true;
        }
        return false;
    }
}
