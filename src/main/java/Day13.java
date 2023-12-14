import utils.Parser;

import java.util.ArrayList;
import java.util.List;

// 2023 puzzle 11
public class Day13 implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");

        List<List<String>> patterns = getPatterns(input);
        int result = 0;
        for (List<String> pattern : patterns) {
            result += getLinesOnTheLeft(pattern).stream().mapToInt(Integer::intValue).sum()
                    + 100 * getLinesAbove(pattern).stream().mapToInt(Integer::intValue).sum();
        }

        System.out.println(result);
    }

    private List<List<String>> getPatterns(List<String> input) {
        List<List<String>> patterns = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (String s : input) {
            if (s.isEmpty()) {
                patterns.add(tmp);
                tmp = new ArrayList<>();
            } else {
                tmp.add(s);
            }
        }
        patterns.add(tmp);
        return patterns;
    }

    private List<Integer> getLinesOnTheLeft(List<String> pattern) {
        List<Integer> indexOfLeftVerticalLine = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length() - 1; i++) {
            if (isVerticalReflection(i, pattern)) {
                indexOfLeftVerticalLine.add(i + 1);
            }
        }
        return indexOfLeftVerticalLine;
    }

    private boolean isVerticalReflection(int i, List<String> pattern) {
        int maxIndex = pattern.get(0).length();
        int leftIndex = i;
        int rightIndex = i + 1;
        while (leftIndex >= 0 && rightIndex < maxIndex) {
            int left = leftIndex;
            int right = rightIndex;
            if (!pattern.stream()
                    .allMatch(str -> str.charAt(left) == str.charAt(right))) {
                return false;
            }
            leftIndex--;
            rightIndex++;
        }
        return true;
    }

    private List<Integer> getLinesAbove(List<String> pattern) {
        return new ArrayList<>();
    }
}
