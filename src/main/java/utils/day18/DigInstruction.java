package utils.day18;

import utils.Direction;

public class DigInstruction {
    public final Direction direction;
    public final int distance;
    public final String color;

    public DigInstruction(String direction, int distance, String color) {
        this.direction = convertToDirection(direction);
        this.distance = distance;
        this.color = color;
    }

    private Direction convertToDirection(String s) {
        switch (s){
            case "U":
                return Direction.UP;
            case "D":
                return Direction.DOWN;
            case "L":
                return Direction.LEFT;
            case "R":
                return Direction.RIGHT;
        }
        throw new RuntimeException("direction not found");
    }
}
