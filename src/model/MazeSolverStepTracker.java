package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

// Implementation of Observer that tracks the steps taken in solving a given
public class MazeSolverStepTracker {
    private ArrayList<MazeStep> steps = new ArrayList<>();

    public void recordStep(Maze maze, Set<Position> visited, LinkedList<Position> toVisit) {
        steps.add(new MazeStep(maze, visited, toVisit));
    }

    public ArrayList<MazeStep> getSteps() {
        return steps;
    }
}
