import model.Maze;
import model.MazeSolverStepTracker;
import model.MazeStep;
import ui.MazePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // sample maze
        int[][] cells = new int[][] {
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 1, 1},
                {1, 1, 1, 0, 0, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 1, 1},
                {1, 0, 3, 1, 1, 0, 1, 2, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 1, 0, 1, 1, 1, 1, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        MazeSolverStepTracker tracker = new MazeSolverStepTracker();
        Maze maze = new Maze(cells);
        maze.assignStepTracker(tracker);

        JFrame frame = new JFrame();
        frame.add(new MazePanel(maze));
        frame.setVisible(true);
    }
}