package utils.pipes;

import java.util.ArrayList;
import java.util.List;

public class Ground implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return new ArrayList<>();
    }
}
