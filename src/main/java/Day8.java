import utils.day8.Node;
import utils.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 8
public class Day8 implements DayX {

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

        int result = 0;
        int index = 0;
        String root = "AAA";
        while (!root.equals("ZZZ")) {
            Character instruction = instructions.get(index);
            root = nodes.get(root).getNext(instruction);

            index++;
            if (index >= instructions.size()) {
                index = 0;
            }
            result++;
        }


        System.out.println(result);
    }

}
