package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

// Tracks steps taken to solve a given maze
public class MazeSolverStepTracker {
    private ArrayList<MazeStep> steps = new ArrayList<>();

    public void recordStep(Maze maze, Set<MazeNode> visited, LinkedList<MazeNode> toVisit) {
        steps.add(new MazeStep(maze, visited, toVisit));
    }

    public ArrayList<MazeStep> getSteps() {
        return steps;
    }
}
