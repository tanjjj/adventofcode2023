import utils.Parser;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 9
public class Day9B implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day9.txt");
        List<List<Integer>> histories = input.stream()
                .map(this::getHistory)
                .collect(Collectors.toList());

        List<Integer> result = histories.stream()
                .map(this::getNextHistory)
                .collect(Collectors.toList());

        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private List<Integer> getHistory(String str) {
        return Arrays.stream(str.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private int getNextHistory(List<Integer> history) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> current = history;
        int i = 0;
        map.put(i, current);
        while (!current.stream().allMatch(x -> x == 0)) {
            i++;
            List<Integer> next = getNextLine(current);
            map.put(i, next);
            current = next;
        }

        int result = 0;
        for (int r = map.size() - 1 - 1; r >= 0; r--) {
            List<Integer> rowAbove = map.get(r);
            result = rowAbove.get(0) - result;
        }
        return result;
    }

    private List<Integer> getNextLine(List<Integer> current) {
        List<Integer> next = new ArrayList<>();
        for (int i = 1; i < current.size(); i++) {
            next.add(current.get(i) - current.get(i - 1));
        }
        return next;
    }
}
