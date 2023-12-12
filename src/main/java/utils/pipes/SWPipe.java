package utils.pipes;

import java.util.Arrays;
import java.util.List;

public class SWPipe implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return Arrays.asList(Direction.SOUTH, Direction.WEST);
    }
}
