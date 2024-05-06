package model;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a maze composed of empty, wall, target, and start squares with uniform size and length.
 */
public class Maze implements Iterable<Integer> {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int TARGET = 2;
    public static final int START = 3;

    private final int[][] maze;
    private final int mazeHeight;
    private final int mazeWidth;

    /**
     *
     * @param matrix 2d array of integers representing a maze. throws IllegalArgumentException if maze is not valid.
     *               The array's sub-arrays must be equal in size to each other.
     */
    public Maze(int[][] matrix) {
        this.maze = matrix;
        if (!isValid()) {
            throw new IllegalArgumentException();
        }
        mazeHeight = maze.length;
        mazeWidth = maze[0].length;
    }

    /**
     *
     * @return The value of the cell with the given x and y coordinates in the maze.
     */
    public int getCell(int x, int y) {
        return maze[y][x];
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    /**
     *
     * @return Returns true if the maze contains <b>exactly one target square</b> and <b>exactly one start square</b>,
     * and only numbers that correspond to cells.
     *
     */
    public Boolean isValid() {
        int targetSquares = 0;
        int startSquares = 0;
        for (int[] row : maze) {
            for (int cell : row) {
                if (cell != EMPTY && cell != WALL && cell != TARGET && cell != START) {
                    return false;
                } else if (cell == TARGET) {
                    targetSquares++;
                } else if (cell == START) {
                    startSquares++;
                }
            }
        }

        return targetSquares == 1 && startSquares == 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mazeHeight; i++) {
            int[] column = maze[i];
            for (int j = 0; j < mazeWidth; j++) {
                if (j != 0) {
                    sb.append(" ");
                }
                sb.append(column[j]);
            }
            if (i < mazeHeight - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    @Override @Nonnull
    public Iterator<Integer> iterator() {
        return new MazeIterator();
    }

    /**
     * Iterator that iterates through this maze by traversing through a row and then moving to the next one
     */
    private class MazeIterator implements Iterator<Integer> {
        private int yCursor = 0;
        private int xCursor = 0;

        @Override
        public boolean hasNext() {
            return yCursor < maze.length && xCursor < maze.length;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Integer nextInteger = maze[yCursor][xCursor];

            xCursor++;
            if (xCursor >= mazeWidth) {
                xCursor = 0;
                yCursor++;
            }

            return nextInteger;
        }
    }
}
