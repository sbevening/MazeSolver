package ui;

import model.Maze;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MazePanel extends JPanel {
    private Maze maze;
    private final int SCALE = 50;

    private final Color EMPTY_COLOR = Color.WHITE;
    private final Color WALL_COLOR = Color.BLACK;
    private final Color TARGET_COLOR = Color.RED;
    private final Color START_COLOR = Color.GREEN;

    private final Map<Integer, Color> cellColorMap;

    public MazePanel(Maze maze) {
        this.maze = maze;
        this.setSize(maze.getNumColumns() * SCALE, maze.getNumRows() * SCALE);
        this.cellColorMap = Map.of(
                Maze.EMPTY, EMPTY_COLOR,
                Maze.WALL, WALL_COLOR,
                Maze.TARGET, TARGET_COLOR,
                Maze.START, START_COLOR);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        for (int y = 0; y < maze.getNumColumns(); y++) {
            for (int x = 0; x < maze.getNumRows(); x++) {
                graphics.setColor(getAppropriateColor(maze.getCell(x, y)));
                graphics.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }
    }

    private Color getAppropriateColor(int cellType) {
        return cellColorMap.get(cellType);
    }
}
