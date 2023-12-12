package utils.pipes;

import java.util.List;

public interface Pipe {
    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    List<Direction> getDirections();
}
