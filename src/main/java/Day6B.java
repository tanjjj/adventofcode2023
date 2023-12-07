import utils.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 6
public class Day6B implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day6.txt");

        long time = Long.parseLong(input.get(0).replaceAll("Time:", "").replaceAll(" ", ""));
        long distance = Long.parseLong(input.get(1).replaceAll("Distance:", "").replaceAll(" ", ""));

        long result = Day6.getNumberOfSolutions(time, distance);
        System.out.println(result);
    }
}
