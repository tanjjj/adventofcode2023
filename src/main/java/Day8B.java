import utils.day8.Node;
import utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 8
public class Day8B implements DayX {

    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day8.txt");
        List<Character> instructions = input.get(0).chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toList());
        int i = 2;
        Map<String, Node> nodes = new HashMap<>();

        while (i < input.size()) {
            String[] strings = input.get(i).split(" = ");
            String[] leaves = strings[1].substring(1, strings[1].length() - 1).split(", ");
            Node node = new Node(leaves[0], leaves[1]);
            nodes.put(strings[0], node);
            i++;
        }

        List<String> roots = nodes.keySet().stream()
                .filter(k -> k.endsWith("A"))
                .collect(Collectors.toList());
        List<Integer> steps = new ArrayList<>();
        for (String root : roots) {
            int result = 0;
            int index = 0;
            String key = root;
            while (!key.endsWith("Z")) {
                Character instruction = instructions.get(index);
                key = nodes.get(key).getNext(instruction);

                index++;
                if (index >= instructions.size()) {
                    index = 0;
                }
                result++;
            }

            steps.add(result);
        }

        long result = steps.get(0);
        for (Integer step : steps) {
            result = getLCM(result, step);
        }
        System.out.println(result);
    }


    public static long getLCM(long a, long b) {
        return (a * b) / getGCD(a, b);
    }

    private static long getGCD(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return getGCD(b, a % b);
        }
    }
}
