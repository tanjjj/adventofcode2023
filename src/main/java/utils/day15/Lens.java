package utils.day15;

import java.util.Objects;

public class Lens {
    public final String label;
    public int focalLength;

    public Lens(String label, int focalLength) {
        this.label = label;
        this.focalLength = focalLength;
    }

    // identify the lens only by label
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lens lens = (Lens) o;
        return Objects.equals(label, lens.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
