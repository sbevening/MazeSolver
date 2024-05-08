package ui;

import model.Maze;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * JPanel that is a visual representation of a given maze.
 */
public class MazePanel extends JPanel {
    private final Maze maze;
    private final int SCALE = 50;

    private final Map<Integer, Color> cellColorMap;

    /**
     * @param maze Maze to be displayed in panel.
     */
    public MazePanel(Maze maze) {
        final Color EMPTY_COLOR = Color.WHITE;
        final Color WALL_COLOR = Color.BLACK;
        final Color TARGET_COLOR = Color.RED;
        final Color START_COLOR = Color.GREEN;

        this.maze = maze;
        this.setSize(maze.getMazeHeight() * SCALE, maze.getMazeWidth() * SCALE);
        this.cellColorMap = Map.of(
                Maze.EMPTY, EMPTY_COLOR,
                Maze.WALL, WALL_COLOR,
                Maze.TARGET, TARGET_COLOR,
                Maze.START, START_COLOR);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        for (int y = 0; y < maze.getMazeHeight(); y++) {
            for (int x = 0; x < maze.getMazeWidth(); x++) {
                graphics.setColor(getAppropriateColor(maze.getCellByPosition(new Position(x, y))));
                graphics.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }
    }

    private Color getAppropriateColor(int cellType) {
        return cellColorMap.get(cellType);
    }
}
