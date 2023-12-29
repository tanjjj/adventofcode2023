package utils.day15;

public class Instruction {
    public final String label;
    public final Operation operation;
    public final int focalLength;

    public Instruction(String label, Operation operation, int focalLength) {
        this.label = label;
        this.operation = operation;
        this.focalLength = focalLength;
    }
}
