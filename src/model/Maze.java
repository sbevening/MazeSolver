package model;

import java.awt.Color;

public class Maze {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int TARGET = 2;
    public static final int START = 3;

    private int[][] maze;

    public Maze(int[][] matrix) {
        this.maze = matrix;
        if (!isValid()) {
            throw new IllegalArgumentException();
        }
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
}
