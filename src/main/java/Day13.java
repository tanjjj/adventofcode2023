import utils.Parser;

import java.util.ArrayList;
import java.util.List;

// 2023 puzzle 13
public class Day13 implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day13.txt");

        List<List<String>> patterns = getPatterns(input);
        int result = 0;
        for (List<String> pattern : patterns) {
            result += getLinesOnTheLeft(pattern).stream().mapToInt(Integer::intValue).sum()
                    + 100 * getLinesAbove(pattern).stream().mapToInt(Integer::intValue).sum();
        }

        System.out.println(result);
    }

    public static List<List<String>> getPatterns(List<String> input) {
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

    public static List<Integer> getLinesOnTheLeft(List<String> pattern) {
        List<Integer> numberOfLInesOnTheLeft = new ArrayList<>();
        for (int i = 0; i < pattern.get(0).length() - 1; i++) {
            if (isVerticalReflection(i, pattern)) {
                numberOfLInesOnTheLeft.add(i + 1);
            }
        }
        return numberOfLInesOnTheLeft;
    }

    public static boolean isVerticalReflection(int i, List<String> pattern) {
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

    public static List<Integer> getLinesAbove(List<String> pattern) {
        List<Integer> numberOfLinesAbove = new ArrayList<>();
        for (int i = 0; i < pattern.size() - 1; i++) {
            if (isHorizontalReflection(i, pattern)) {
                numberOfLinesAbove.add(i + 1);
            }
        }
        return numberOfLinesAbove;
    }

    public static boolean isHorizontalReflection(int i, List<String> pattern) {
        int maxIndex = pattern.size();
        int upIndex = i;
        int downIndex = i + 1;
        while (upIndex >= 0 && downIndex < maxIndex) {
            if (!pattern.get(upIndex).equals(pattern.get(downIndex))) {
                return false;
            }
            upIndex--;
            downIndex++;
        }
        return true;
    }

}
