package ui;

import model.Maze;
import model.MazeSolverStepTracker;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A set of buttons that can be used to control the maze creator's functionality.
 */
public class MazeCreatorButtons extends JPanel {
    private final MazeCreatorPanel mazeCreatorPanel;

    /**
     * Creates a set of buttons that can be used to draw each type of tile on this.mazeCreatorPanel.
     * @param mazeCreatorPanel The panel this set of buttons interacts with/controls.
     */
    public MazeCreatorButtons(MazeCreatorPanel mazeCreatorPanel) {
        this.mazeCreatorPanel = mazeCreatorPanel;
        setLayout(new GridLayout(1, 4));
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 100));
        add(generateCellTypeSelectionButton("wall", Maze.WALL));
        add(generateCellTypeSelectionButton("empty", Maze.EMPTY));
        add(generateCellTypeSelectionButton("target", Maze.TARGET));
        add(generateCellTypeSelectionButton("start", Maze.START));
        add(generateSubmitButton());
    }

    /**
     *
     * @param label The label to be displayed on the button.
     * @param cellType The integer corresponding to the cell type that clicking on the generated button will switch
     *                 the maze creator's cursor to.
     * @return A button with a given label that sets the selected cell type for the cursor in its corresponding
     * MazeCreatorPanel.
     */
    private JButton generateCellTypeSelectionButton(String label, int cellType) {
        JButton button = new JButton(label);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mazeCreatorPanel.setCellType(cellType);
                } catch (IllegalArgumentException iae) {
                    mazeCreatorPanel.setCellType(Maze.EMPTY); // set to empty as default if invalid number is given
                }
            }
        });
        return button;
    }

    /**
     *
     * @return A button that will submit the maze to be checked for validity and then solved and used to generate a
     * MazeStepsPanel.
     */
    private JButton generateSubmitButton() {
        JButton button = new JButton("submit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayNewMazeStepsPanel();
                } catch (IllegalArgumentException iae) {
                    System.out.println("whoop");
                }
            }
        });

        return button;
    }

    /**
     * If the maze in this.mazeCreatorPanel is valid, creates a new window with a MazeStepsPanel with steps on how to
     * solve that maze. If the maze is invalid, an IllegalArgumentException will be thrown.
     */
    private void displayNewMazeStepsPanel() {
        Maze maze = new Maze(mazeCreatorPanel.maze);
        MazeSolverStepTracker tracker = new MazeSolverStepTracker();
        maze.assignStepTracker(tracker);
        List<Position> path = maze.solveMaze();
        JFrame mazeFrame = new JFrame("Maze Viewer");
        mazeFrame.setResizable(false);
        mazeFrame.setSize(1000, 800);
        mazeFrame.setLayout(new BorderLayout());
        MazeStepsPanel msp = new MazeStepsPanel(tracker.getSteps(), path);
        mazeFrame.add(msp, BorderLayout.CENTER);
        mazeFrame.add(new MazeStepButtons(msp), BorderLayout.SOUTH);
        mazeFrame.setVisible(true);
    }
}
