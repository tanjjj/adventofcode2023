import utils.Parser;

import java.util.ArrayList;
import java.util.List;

// 2023 puzzle 13
public class Day13B implements DayX {

    @Override
    public void run() throws Exception {
        List<String> input = Parser.parseInputAsString("day13.txt");

        List<List<String>> patterns = Day13.getPatterns(input);

        int result = 0;
        for (List<String> pattern : patterns) {
            List<Integer> oldVertical = Day13.getLinesOnTheLeft(pattern);
            List<Integer> oldHorizontal = Day13.getLinesAbove(pattern);
            List<String> newPattern = fixSmudge(pattern);
            List<Integer> newVertical = Day13.getLinesOnTheLeft(newPattern);
            List<Integer> newHorizontal = Day13.getLinesAbove(newPattern);
            newVertical.removeAll(oldVertical);
            newHorizontal.removeAll(oldHorizontal);
            result = result + newVertical.stream().mapToInt(Integer::intValue).sum()
                    + 100 * newHorizontal.stream().mapToInt(Integer::intValue).sum();
        }

        System.out.println(result);
    }

    // locate and fix the smudge that causes a different reflection line to be valid
    private List<String> fixSmudge(List<String> pattern) throws Exception {
        int currentReflection = Day13.getLinesOnTheLeft(pattern).stream().mapToInt(Integer::intValue).sum()
                + 100 * Day13.getLinesAbove(pattern).stream().mapToInt(Integer::intValue).sum();
        for (int r = 0; r < pattern.size(); r++) {
            for (int i = 0; i < pattern.get(0).length(); i++) {
                List<String> newPattern = new ArrayList<>(pattern);
                String oldRow = pattern.get(r);
                char newChar = oldRow.charAt(i) == '.' ? '#' : '.';
                String newRow = oldRow.substring(0, i) + newChar + oldRow.substring(i + 1);
                newPattern.set(r, newRow);
                int newReflection = Day13.getLinesOnTheLeft(newPattern).stream().mapToInt(Integer::intValue).sum()
                        + 100 * Day13.getLinesAbove(newPattern).stream().mapToInt(Integer::intValue).sum();
                if (newReflection > 0 && newReflection != currentReflection) {
                    return newPattern;
                }
            }
        }
        throw new Exception("no smudge found");
    }
}
