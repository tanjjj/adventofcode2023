package utils.day20;

import java.util.List;

public abstract class Module {
    public final String name;
    public final ModuleType type;
    public final List<String> destinations;

    protected Module(String name, ModuleType type, List<String> destinations) {
        this.name = name;
        this.type = type;
        this.destinations = destinations;
    }

}
