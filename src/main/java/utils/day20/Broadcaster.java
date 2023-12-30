package utils.day20;

import java.util.List;

public class Broadcaster extends Module {

    public Broadcaster(List<String> destinations) {
        super("broadcaster", ModuleType.BROADCASTER, destinations);
    }
}
