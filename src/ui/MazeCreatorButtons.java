package ui;

import model.Maze;
import model.MazeSolverStepTracker;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * A set of buttons that can be used to control the maze creator's functionality.
 */
public class MazeCreatorButtons extends JPanel {
    private final MazeCreatorPanel mazeCreatorPanel;
    private final int screenWidth;
    private final JLabel currentCellTypeLabel;

    private static final Map<Integer, String> cellNameMap = Map.of(
            Maze.EMPTY, "Empty",
            Maze.WALL, "Wall",
            Maze.TARGET, "Target",
            Maze.START, "Start");

    /**
     * Creates a set of buttons that can be used to draw each type of tile on this.mazeCreatorPanel.
     * @param mazeCreatorPanel The panel this set of buttons interacts with/controls.
     */
    public MazeCreatorButtons(MazeCreatorPanel mazeCreatorPanel) {
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.mazeCreatorPanel = mazeCreatorPanel;
        setLayout(new GridLayout(3, 2));
        setPreferredSize(new Dimension(screenWidth, 100));

        add(generateCellTypeSelectionButton("Wall Square", Maze.WALL));
        add(generateCellTypeSelectionButton("Empty Square", Maze.EMPTY));
        add(generateCellTypeSelectionButton("Target Square", Maze.TARGET));
        add(generateCellTypeSelectionButton("Start Square", Maze.START));
        add(generateSubmitButton());
        currentCellTypeLabel = new JLabel("Selected: Wall", SwingConstants.CENTER);
        add(currentCellTypeLabel);
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
        button.setPreferredSize(new Dimension(screenWidth / 2, 33));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mazeCreatorPanel.setCellType(cellType);
                    currentCellTypeLabel.setText("Selected: " + cellNameMap.get(cellType));
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
        JButton button = new JButton("Solve Maze");
        button.setPreferredSize(new Dimension(screenWidth, 33));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayNewMazeStepsPanel();
                } catch (IllegalArgumentException iae) {
                    // pass
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
