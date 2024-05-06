package model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Maze implements Iterable<Integer> {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int TARGET = 2;
    public static final int START = 3;

    private final int[][] maze;
    private final int mazeHeight;
    private final int mazeWidth;

    public Maze(int[][] matrix) {
        this.maze = matrix;
        if (!isValid()) {
            throw new IllegalArgumentException();
        }
        mazeHeight = maze.length;
        mazeWidth = maze[0].length;
    }

    public int getCell(int x, int y) {
        return maze[y][x];
    }

    public int getNumColumns() {
        return maze.length;
    }

    public int getNumRows() {
        return maze[0].length;
    }

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

        for (int[] column : maze) {
            for (int i = 0; i < column.length; i++) {
                if (i != 0) {
                    sb.append(" ");
                }
                sb.append(column[i]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public Iterator<Integer> iterator() {
        return new MazeIterator();
    }

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
