package utils.pipes;

import java.util.Arrays;
import java.util.List;

public class HorizontalPipe implements Pipe {
    @Override
    public List<Direction> getDirections() {
        return Arrays.asList(Direction.EAST, Direction.WEST);
    }
}
