package ui;

import model.Maze;
import model.MazeSolverStepTracker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        maze.solveMaze();

        JFrame frame = new JFrame();
        MazeStepsPanel mazeStepsPanel = new MazeStepsPanel(tracker.getSteps());
        frame.add(mazeStepsPanel);
        frame.add(generateLastButton(mazeStepsPanel));
        frame.add(generateNextButton(mazeStepsPanel));
        frame.setVisible(true);
    }

    private static JButton generateNextButton(MazeStepsPanel mazeStepsPanel) {
        JButton nextButton = new JButton("next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeStepsPanel.nextStep();
            }
        });
        return nextButton;
    }

    private static JButton generateLastButton(MazeStepsPanel mazeStepsPanel) {
        JButton lastButton = new JButton("prev");
        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeStepsPanel.lastStep();
            }
        });
        return lastButton;
    }
}