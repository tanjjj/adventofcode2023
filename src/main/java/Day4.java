import utils.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 4
public class Day4 implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day4.txt");

        List<Integer> result = input.stream()
                .map(this::getPoints)
                .collect(Collectors.toList());

        System.out.println(result);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }


    // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    private int getPoints(String card) {
        String[] splits = (card.split(":")[1]).split(" \\| ");
        List<Integer> winningNum = Arrays.stream(splits[0].split("( )+"))
                .filter(str -> !str.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
       List<Integer> numbers = Arrays.stream(splits[1].split("( )+"))
                .filter(str -> !str.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int total = 0;
        for (int n : numbers) {
            if (winningNum.contains(n)) {
                total = total == 0 ? 1 : total * 2;
            }
        }

        return total;
    }

}
