import utils.Parser;
import utils.day20.*;
import utils.day20.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 2023 puzzle 20
public class Day20 implements DayX {
    @Override
    public void run() {
        List<String> input = Parser.parseInputAsString("test.txt");
        Map<String, Module> modules = input.stream()
                .map(this::convertToModule)
                .collect(Collectors.toMap(m -> m.name, m -> m));

        for (Module module : modules.values()) {
            module.destinations.stream()
                    .filter(child -> modules.get(child).type == ModuleType.CONJUNCTION)
                    .map(modules::get)
                    .forEach(child -> ((Conjunction) child).addParent(module.name));
        }

        // H = high, L = low
        List<String> history = new ArrayList<>();
        int i = 0;
        String current = "";
        while (i < 200) {
            current = "L" + process("broadcaster", "L", modules);
            if (history.contains(current)) {
                break;
            }
            history.add(current);
            i++;
        }

        int startOfLoop = history.indexOf(current);
        int loopLength = i - startOfLoop;
        int indexOfFinalLoop = startOfLoop + (1000 - startOfLoop) % loopLength;
        String finalLoop = history.get(indexOfFinalLoop);

        long low = finalLoop.chars().filter(ch -> ch == 'L').count();
        long high = finalLoop.chars().filter(ch -> ch == 'H').count();
        System.out.println(low * high);
    }

    private Module convertToModule(String str) {
        String[] splits = str.split(" -> ");
        List<String> destinations = Arrays.asList(splits[1].split(", "));
        String typeandname = splits[0];
        if(typeandname.startsWith("output")){// dummy testing type
            return new Dummy("output");
        }
        if (typeandname.startsWith("broadcaster")) {
            return new Broadcaster(destinations);
        } else if (typeandname.startsWith("%")) {
            return new FlipFlop(typeandname.substring(1), destinations);
        } else {
            return new Conjunction(typeandname.substring(1), destinations);
        }
    }

    private String process(String currentModule, String pulse, Map<String, Module> modules) {
        Module module = modules.get(currentModule);
        switch (module.type) {
            case DUMMY:
                return "";
            case BROADCASTER:
                StringBuilder result = new StringBuilder();
                for (String child : module.destinations) {
                    result.append(pulse);
                    updateConjunction(child, modules, pulse);
                    result.append(process(child, pulse, modules));
                }
                return result.toString();
            case FLIPFLOP:
                StringBuilder r = new StringBuilder();
                if (pulse.equals("L")) {
                    if (((FlipFlop) module).isOn) {
                        ((FlipFlop) module).isOn = false;
                        for (String child : module.destinations) {
                            r.append("L");
                            updateConjunction(child, modules, "L");
                            r.append(process(child, "L", modules));
                        }
                    } else {
                        ((FlipFlop) module).isOn = true;
                        for (String child : module.destinations) {
                            r.append("H");
                            updateConjunction(child, modules, "H");
                            r.append(process(child, "H", modules));
                        }
                    }
                    // if currently on, send H and turn off
                    // if currently off, send L and turn on
                }
                return r.toString();
            // do nothing if input is H
            case CONJUNCTION:
                // if not all parents updated yet, do nothing
                // if all parents sent H, send L
                // otherwise send H
                StringBuilder r2 = new StringBuilder();
                Conjunction m = ((Conjunction) module);
                if (m.memory.size() == m.parents.size()) {
                    if (m.memory.stream().allMatch(s -> s.equals("H"))) {
                        for (String child : module.destinations) {
                            r2.append("L");
                            updateConjunction(child, modules, "L");
                            r2.append(process(child, "L", modules));
                        }
                    } else {
                        for (String child : module.destinations) {
                            r2.append("H");
                            updateConjunction(child, modules, "H");
                            r2.append(process(child, "H", modules));
                        }
                    }
                    m.memory = new ArrayList<>();
                    return r2.toString();
                } else {
                    return "";
                }
        }
        throw new RuntimeException("unknown module");
    }

    private void updateConjunction(String name, Map<String, Module> modules, String pulse) {
        Module module = modules.get(name);
        if (module instanceof Conjunction) {
            ((Conjunction) module).memory.add(pulse);
        }
    }

}
