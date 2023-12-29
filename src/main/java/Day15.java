import utils.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 15
public class Day15 implements DayX {
    @Override
    public void run() {
        String input = Parser.parseInputAsString("day15.txt").get(0);

        List<Integer> results = Arrays.stream(input.split(","))
                .map(this::getHashCode)
                .collect(Collectors.toList());

        System.out.println(results.stream().mapToInt(Integer::intValue).sum());
    }

    private int getHashCode(String str) {
        int result = 0;
        for (char ch : str.toCharArray()) {
            int ascii = Character.hashCode(ch);
            result += ascii;
            result = result * 17;
            result = result % 256;
        }
        return result;
    }

}
