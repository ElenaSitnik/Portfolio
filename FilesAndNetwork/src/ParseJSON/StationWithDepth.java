package ParseJSON;

import java.util.Objects;

public class StationWithDepth {
    private String name;
    private double depth;

    public StationWithDepth(String name, double depth) {
        this.name = name;
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public double getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return name + " " + depth + " м.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationWithDepth station = (StationWithDepth) o;
        return Objects.equals(name, station.name) && Objects.equals(depth, station.depth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, depth);
    }
}
