import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 1 part 2
public class Day1B implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day1.txt");
        List<Integer> result = input.stream()
                .map(this::getDigitFromString)
                .collect(Collectors.toList());
        System.out.println(result);
        System.out.println(result.stream().mapToInt(Integer::intValue).sum());
    }

    private int getDigitFromString(String str) {
        System.out.println(str);
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                digits.add(Integer.parseInt(c + ""));
            } else {
                int x = getDigitFromSubString(str.substring(i));
                if (x > 0) {
                    if (x == 1 || x == 2 | x == 6) {
                        digits.add(x);
                        //i += 2;
                    } else if (x == 3 || x == 7 | x == 8) {
                        digits.add(x);
                        //i += 4;
                    } else if (x == 4 || x == 5 | x == 9) {
                        digits.add(x);
                        //i += 3;
                    }
                }
            }
        }

        System.out.println(digits);
        if (digits.size() >= 2) {
            return digits.get(0) * 10 + digits.get(digits.size() - 1);
        } else {
            return digits.get(0) * 10 + digits.get(0);
        }
    }

    private int getDigitFromSubString(String str) {
        if (str.startsWith("one")) {
            return 1;
        } else if (str.startsWith("two")) {
            return 2;
        } else if (str.startsWith("three")) {
            return 3;
        } else if (str.startsWith("four")) {
            return 4;
        } else if (str.startsWith("five")) {
            return 5;
        } else if (str.startsWith("six")) {
            return 6;
        } else if (str.startsWith("seven")) {
            return 7;
        } else if (str.startsWith("eight")) {
            return 8;
        } else if (str.startsWith("nine")) {
            return 9;
        } else {
            return 0;
        }
    }
}
