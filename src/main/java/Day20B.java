import utils.Parser;
import utils.day20.*;
import utils.day20.Module;

import java.util.*;
import java.util.stream.Collectors;

// 2023 puzzle 20 part 2
// note rx -> xx is added to the end of the input file to avoid NPE because rx is a dummy module type
// rx is connected to &kj. Output of &kj should be L. Input of &KJ should be H.
// Input of &kj comes from ln, dr, zx, vn.
// find how many button press to get ln, dr, zx, vn to output H. Get LCM of these numbers.
public class Day20B implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("day20.txt");
        List<String> conjunctions = List.of("ln", "dr", "zx", "vn"); // identified these manually from the input file

        // H = high, L = low
        List<Integer> buttonPressForEachConjunction = new ArrayList<>();
        for (String conjunction : conjunctions) {
            Map<String, Module> modules = input.stream()
                    .map(this::convertToModule)
                    .collect(Collectors.toMap(m -> m.name, m -> m));

            for (Module module : modules.values()) {
                module.destinations.stream()
                        .filter(child -> modules.get(child).type == ModuleType.CONJUNCTION)
                        .map(modules::get)
                        .forEach(child -> ((Conjunction) child).addParent(module.name));
            }

            int i = 0;
            while (true) {
                Queue<QueueElement> queue = new LinkedList<>();
                queue.add(new QueueElement("broadcaster", "L"));
                String current = process(queue, modules, conjunction);
                i++;
                if (current.contains("FOUND")) {
                    buttonPressForEachConjunction.add(i);
                    break;
                }
            }
        }


        long result = 1;
        for (Integer number : buttonPressForEachConjunction) {
            result = Day8B.getLCM(result, number);
        }

        System.out.println(result);
    }

    private Module convertToModule(String str) {
        String[] splits = str.split(" -> ");
        List<String> destinations = Arrays.asList(splits[1].split(", "));
        String typeandname = splits[0];
        if (typeandname.startsWith("output")) {// dummy testing type
            return new Dummy("output");
        }
        if (typeandname.startsWith("rx")) {// dummy type
            return new Dummy("rx");
        }
        if (typeandname.startsWith("broadcaster")) {
            return new Broadcaster(destinations);
        } else if (typeandname.startsWith("%")) {
            return new FlipFlop(typeandname.substring(1), destinations);
        } else {
            return new Conjunction(typeandname.substring(1), destinations);
        }
    }

    private String process(Queue<QueueElement> queue, Map<String, Module> modules, String conjunctionName) {
        StringBuilder result = new StringBuilder();
        while (queue.peek() != null) {
            QueueElement element = queue.poll();
            String currentModule = element.moduleName;
            String pulse = element.pulse;

            Module module = modules.get(currentModule);
            switch (module.type) {
                case DUMMY:
                    continue;
                case BROADCASTER:
                    for (String child : module.destinations) {
                        result.append(pulse);
                        updateConjunction(child, modules, pulse, currentModule);
                        queue.offer(new QueueElement(child, pulse));
                    }
                    continue;
                case FLIPFLOP:
                    if (pulse.equals("L")) {
                        String output;
                        // if currently on, send H and turn off
                        // if currently off, send L and turn on
                        if (((FlipFlop) module).isOn) {
                            ((FlipFlop) module).isOn = false;
                            output = "L";
                        } else {
                            ((FlipFlop) module).isOn = true;
                            output = "H";
                        }

                        for (String child : module.destinations) {
                            result.append(output);
                            updateConjunction(child, modules, output, currentModule);
                            queue.offer(new QueueElement(child, output));
                        }
                    }
                    // do nothing if input is H
                    continue;
                case CONJUNCTION:
                    Conjunction m = ((Conjunction) module);
                    String output;
                    // if all parents sent H, send L
                    // otherwise send H
                    if (m.memory.values().stream().allMatch(s -> s.equals("H"))) {
                        output = "L";
                    } else {
                        output = "H";
                        if (currentModule.equals(conjunctionName)) {
                            return "FOUND";
                        }
                    }
                    for (String child : module.destinations) {
                        result.append(output);
                        updateConjunction(child, modules, output, currentModule);
                        queue.add(new QueueElement(child, output));
                    }
                    continue;
            }
            throw new RuntimeException("unknown module");
        }
        return result.toString();
    }

    private void updateConjunction(String name, Map<String, Module> modules, String pulse, String parent) {
        Module module = modules.get(name);
        if (module instanceof Conjunction) {
            ((Conjunction) module).memory.put(parent, pulse);
        }
    }

}
