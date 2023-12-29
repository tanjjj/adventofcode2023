import utils.Parser;
import utils.day15.Instruction;
import utils.day15.Lens;
import utils.day15.Operation;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 15 part 2
public class Day15B implements DayX {
    @Override
    public void run() {
        String input = Parser.parseInputAsString("day15.txt").get(0);
        List<Instruction> instructions = Arrays.stream(input.split(","))
                .map(this::convertInstruction)
                .collect(Collectors.toList());

        Map<Integer, List<Lens>> boxes = new HashMap<>();
        for (Instruction instruction : instructions) {
            int hash = Day15.getHashCode(instruction.label);
            List<Lens> lenses = boxes.getOrDefault(hash, new ArrayList<>());
            Lens newLens = new Lens(instruction.label, instruction.focalLength);
            if (instruction.operation == Operation.ADD) {
                if (lenses.contains(newLens)) {
                    int index = lenses.indexOf(newLens);
                    lenses.get(index).focalLength = instruction.focalLength;
                } else {
                    lenses.add(newLens);
                }
            } else {
                lenses.remove(newLens);
            }
            boxes.put(hash, lenses);
        }

        int result = boxes.entrySet().stream()
                .mapToInt(entry -> getFocusingPowerOfABox(entry.getKey(), entry.getValue()))
                .sum();
        System.out.println(result);
    }

    private Instruction convertInstruction(String str) {
        if (str.contains("=")) {
            String[] splits = str.split("=");
            return new Instruction(splits[0], Operation.ADD, Integer.parseInt(splits[1]));
        } else {
            return new Instruction(str.substring(0, str.length() - 1), Operation.REMOVE, 0);
        }
    }

    private int getFocusingPowerOfABox(int boxIndex, List<Lens> lenses) {
        int box = boxIndex + 1;
        int result = 0;
        for (int i = 0; i < lenses.size(); i++) {
            result += box * (i + 1) * lenses.get(i).focalLength;
        }
        return result;
    }
}
