package utils.pipes;

import java.util.Arrays;
import java.util.List;

public class Start implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }
}
