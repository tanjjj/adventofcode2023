package utils.pipes;

import java.util.Arrays;
import java.util.List;

public class SEPipe implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return Arrays.asList(Direction.SOUTH, Direction.EAST);
    }
}
