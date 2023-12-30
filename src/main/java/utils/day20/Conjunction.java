package utils.day20;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conjunction extends Module {
    public Map<String, String> memory = new HashMap<>();

    public Conjunction(String name, List<String> destinations) {
        super(name, ModuleType.CONJUNCTION, destinations);
    }

    public void addParent(String parent) {
        memory.put(parent, "L");

    }
}
