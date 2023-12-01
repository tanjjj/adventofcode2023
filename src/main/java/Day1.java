import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 2023 puzzle 1
public class Day1 implements DayX {
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
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                digits.add(Integer.parseInt(c + ""));
            }
        }

/*        int sum = 0;
        for(int i = 0; i < digits.size(); i++){
            sum += (int) (digits.get(i) * Math.pow(10, digits.size() - 1 - i));
        }
        return sum;*/

        if (digits.size() >= 2) {
            return digits.get(0) * 10 + digits.get(digits.size() - 1);
        } else {
            return digits.get(0) * 10 + digits.get(0);
        }
    }
}
