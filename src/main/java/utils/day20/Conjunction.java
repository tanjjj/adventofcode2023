package utils.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conjunction extends Module {
    public List<String> parents = new ArrayList<>();
    public List<String> memory = new ArrayList<>();

    public Conjunction(String name, List<String> destinations) {
        super(name, ModuleType.CONJUNCTION, destinations);
    }

    public void addParent(String parent) {
        parents.add(parent);
    }
}
