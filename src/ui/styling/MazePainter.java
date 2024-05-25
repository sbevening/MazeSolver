package ui.styling;

import model.Maze;
import model.Position;

import java.awt.*;
import java.util.Map;

/**
 * Class to paint a given maze onto a Graphics2D.
 */
public class MazePainter {
    private final static Color EMPTY_COLOR = Color.WHITE;
    private final static Color WALL_COLOR = Color.BLACK;
    private final static Color TARGET_COLOR = Color.RED;
    private final static Color START_COLOR = Color.GREEN;

    public final static Map<Integer, Color> CELL_COLOR_MAP = Map.of(
            Maze.EMPTY, EMPTY_COLOR,
            Maze.WALL, WALL_COLOR,
            Maze.TARGET, TARGET_COLOR,
            Maze.START, START_COLOR);

    /**
     * Paints a given maze onto a Graphics2D at a specified size.
     * @param graphics Graphics2D to be painted on.
     * @param maze The maze whose cells will be painted.
     * @param scale The side length of each cell.
     */
    public static void paintMaze(Graphics2D graphics, Maze maze, int scale) {
        setRenderingHints(graphics);
        for (int y = 0; y < maze.getMazeHeight(); y++) {
            for (int x = 0; x < maze.getMazeWidth(); x++) {
                graphics.setColor(CELL_COLOR_MAP.get(maze.getCellByPosition(new Position(x, y))));
                graphics.fillRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    public static void setRenderingHints(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
