package utils.day20;

import java.util.List;

public class FlipFlop extends Module {
    public boolean isOn = false;

    public FlipFlop(String name, List<String> destinations) {
        super(name, ModuleType.FLIPFLOP, destinations);
    }
}
