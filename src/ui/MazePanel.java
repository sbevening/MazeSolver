package ui;

import model.Maze;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel that is a visual representation of a given maze.
 */
public class MazePanel extends JPanel {
    private final Maze maze;
    protected final static int SCALE = 20;

    /**
     * @param maze Maze to be displayed in panel.
     */
    public MazePanel(Maze maze) {
        this.maze = maze;
        this.setSize(maze.getMazeHeight() * SCALE, maze.getMazeWidth() * SCALE);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paint(g);
        MazePainter.paintMaze(graphics, maze, SCALE);
    }
}
