import utils.day7.Hand2;
import utils.Parser;

import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 7
public class Day7B implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day7.txt");
        List<Hand2> hands = input.stream()
                .map(this::getHands)
                .sorted(Hand2::compare)
                .collect(Collectors.toList());

        // bid * rank
        int result = 0;
        for (int i = 0; i < hands.size(); i++) {
            result += hands.get(i).bid * (i + 1);
        }
        System.out.println(result);
    }

    private Hand2 getHands(String str) {
        String[] strings = str.split(" ");
        return new Hand2(strings[0], Integer.parseInt(strings[1]));
    }
}
