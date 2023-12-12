package utils.pipes;

import java.util.Arrays;
import java.util.List;

public class VerticalPipe implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return Arrays.asList(Direction.NORTH, Direction.SOUTH);
    }
}
