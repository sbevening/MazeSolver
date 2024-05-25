package ui;

import model.Maze;
import model.MazeSolverStepTracker;
import model.Position;
import ui.mazeCreator.AutomataMazeGenerator;
import ui.mazeCreator.MazeCreatorButtons;
import ui.mazeCreator.MazeCreatorPanel;
import ui.mazeCreator.RecursiveDivisionMazeGenerator;
import ui.mazeStepper.MazeStepButtons;
import ui.mazeStepper.MazeStepsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Creator");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout());
        MazeCreatorPanel mcp = new MazeCreatorPanel();
        frame.add(mcp, BorderLayout.CENTER);
        frame.add(new MazeCreatorButtons(mcp), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}