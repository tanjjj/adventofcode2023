import utils.Parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 4
public class Day4B implements DayX {
    Map<Integer, Long> resultOfOriginalCard = new HashMap<>();


    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day4.txt");

        for (String card : input) {
            calculate(card);
        }

        Map<Integer, Integer> result = new HashMap<>();

        int cardId = 1;
        while (resultOfOriginalCard.containsKey(cardId)) {
            Long winning = resultOfOriginalCard.get(cardId);

            int copyOfWinning = result.getOrDefault(cardId, 0);

            if (result.containsKey(cardId)) {
                result.put(cardId, result.get(cardId) + 1);
            } else {
                result.put(cardId, 1);
            }

            for (int i = 1; i <= winning; i++) {
                int newCardId = cardId + i;
                if (result.containsKey(newCardId)) {
                    result.put(newCardId, result.get(newCardId) + 1 + copyOfWinning);
                } else {
                    result.put(newCardId, 1 + copyOfWinning);
                }
            }

            cardId++;
        }


        System.out.println(result);
        System.out.println(result.values().stream().mapToInt(Integer::intValue).sum());
    }


    // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    private void calculate(String card) {
        int cardId = Integer.parseInt(card.split(":")[0].split("( )+")[1]);

        String[] splits = (card.split(":")[1]).split(" \\| ");
        List<Integer> winningNum = Arrays.stream(splits[0].split("( )+"))
                .filter(str -> !str.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> numbers = Arrays.stream(splits[1].split("( )+"))
                .filter(str -> !str.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        long noOfWin = numbers.stream()
                .filter(winningNum::contains)
                .count();

        resultOfOriginalCard.put(cardId, noOfWin);
    }
}
