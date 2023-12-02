import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 2
public class Day2B implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day2.txt");
        Map<Integer, List<Map<String, Integer>>> games = input
                .stream()
                .collect(Collectors.toMap(this::getGameId, this::getGames));

        List<Integer> result = games.values().stream()
                .map(this::getPower)
                .collect(Collectors.toList());

        System.out.println(result);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private int getGameId(String str) {
        return Integer.parseInt(str.split(":")[0].substring(5));
    }

    private List<Map<String, Integer>> getGames(String str) {
        String game = str.split(":")[1];
        return Arrays.stream(game.split(";"))
                .map(g -> g.split(","))
                .map(this::convertGame)
                .collect(Collectors.toList());
    }

    private Map<String, Integer> convertGame(String[] strs) {
        return Arrays.stream(strs)
                .map(str -> str.split(" "))
                .collect(Collectors.toMap(e -> e[2], e -> Integer.parseInt(e[1])));
    }

    private int getPower(List<Map<String, Integer>> games) {
        int red = 0;
        int green = 0;
        int blue = 0;

        // find max red, green, blue
        List<Map.Entry<String, Integer>> entries = games.stream()
                .flatMap(g -> g.entrySet().stream())
                .collect(Collectors.toList());
        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getKey().equals("red")) {
                red = red > entry.getValue() ? red : entry.getValue();
            }
            if (entry.getKey().equals("green")) {
                green = green > entry.getValue() ? green : entry.getValue();
            }
            if (entry.getKey().equals("blue")) {
                blue = blue > entry.getValue() ? blue : entry.getValue();
            }
        }

        return red * green * blue;
    }
}
