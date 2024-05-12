package ui;

import model.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Panel that lets the user draw a maze with the UI and send it to be solved and used to create a MazeStepsPanel.
 */
public class MazeCreatorPanel extends JPanel {
    private static final int MAZE_HEIGHT = 30;
    private static final int MAZE_WIDTH = 60;
    int[][] maze = new int[MAZE_HEIGHT][MAZE_WIDTH];
    private static final int SCALE = 20;

    private int cellType = Maze.WALL;
    private final ArrayList<Integer> validCellTypes;

    public MazeCreatorPanel() {
        validCellTypes = new ArrayList<>(Arrays.asList(Maze.EMPTY, Maze.WALL, Maze.TARGET, Maze.START));
        MouseAdapter dragAdapter = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                onDrag(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onDrag(e);
            }
        };
        addMouseListener(dragAdapter);
        addMouseMotionListener(dragAdapter);
    }

    /**
     * Paints the cells contained in the maze matrix.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(Color.BLACK);
        Map<Integer, Color> colorMap = MazePainter.CELL_COLOR_MAP;

        for (int y = 0; y < MAZE_HEIGHT; y++) {
            for (int x = 0; x < MAZE_WIDTH; x++) {
                int cellType = maze[y][x];
                Color appropriateColor = colorMap.get(cellType);
                graphics.setColor(appropriateColor);
                graphics.fillRect(SCALE * x, SCALE * y, SCALE, SCALE);
            }
        }
    }

    /**
     * Event listener for when the mouse is held down.
     * @param e The MouseEvent triggering the onDrag.
     */
    private void onDrag(MouseEvent e) {
        int mouseRawX = e.getX();
        int mouseRawY = e.getY();

        int scaledMouseX = mouseRawX / SCALE;
        int scaledMouseY = mouseRawY / SCALE;

        if (0 < scaledMouseY && scaledMouseY < maze.length && 0 < scaledMouseX && scaledMouseX < maze[0].length) {
            maze[scaledMouseY][scaledMouseX] = cellType;
        }

        repaint();
    }

    /**
     * If the cell type given is a valid cell type, sets the cell type that will be put into the maze when a user adds
     * a cell. If the cell type is invalid, throws an IllegalArgumentException.
     * @param cellType The type of cell this panel's cursor will put into the maze when dragged.
     */
    public void setCellType(int cellType) {
        if (validCellTypes.contains(cellType)) {
            this.cellType = cellType;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
