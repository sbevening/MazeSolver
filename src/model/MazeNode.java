package model;

import java.util.Objects;

/**
 * Represents a position in the maze as a graph position with a parent position (can be null) and a position.
 */
public record MazeNode(Position position, MazeNode parent) {
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeNode mazeNode = (MazeNode) o;
        return Objects.equals(position, mazeNode.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
