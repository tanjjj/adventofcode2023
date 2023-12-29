package utils.day16;

import utils.Coordinate;
import utils.Direction;

public class Beam {
    public final Coordinate coordinate;
    public final Direction direction;

    public Beam(Coordinate coordinate, Direction direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }
}
