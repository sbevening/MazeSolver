package model;

import java.util.LinkedList;
import java.util.Set;

public record MazeStep(Maze maze, Set<Position> visited, LinkedList<Position> toVisit) {
}