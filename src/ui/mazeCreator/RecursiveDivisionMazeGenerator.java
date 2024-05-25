package ui.mazeCreator;

import model.Maze;
import model.Position;
import ui.MazePanel;

import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveDivisionMazeGenerator {
    private int[][] cells;

    public Maze generateMaze() {
        cells = new int[21][21];
        divide(new Position(0, 0), new Position(20, 20));

        cells[0][0] = Maze.START;
        cells[20][20] = Maze.TARGET;
        return new Maze(cells);
    }

    private void divide(Position upperLeft, Position lowerRight) {
        int height = lowerRight.y() - upperLeft.y() + 1;
        int width = lowerRight.x() - upperLeft.x() + 1;

        if (height <= 2 || width <= 2) {
            return;
        }

        int splitterY = randomRange(upperLeft.y() + 1, lowerRight.y() - 1);
        splitByHorizontalLine(upperLeft, lowerRight, splitterY);
        int splitterX = randomRange(upperLeft.x() + 1, lowerRight.x() - 1);
        splitByVerticalLine(upperLeft, lowerRight, splitterX);

        makeHoles(upperLeft, lowerRight, splitterX, splitterY);
    }

    private void splitByVerticalLine(Position upperLeft, Position lowerRight, int splitterX) {
        for (int i = upperLeft.y(); i <= lowerRight.y(); i++) {
            cells[i][splitterX] = Maze.WALL;
        }
        int holeY = randomRange(upperLeft.y() + 1, lowerRight.y() - 1);
        cells[holeY][splitterX] = Maze.EMPTY;

        divide(upperLeft, new Position(splitterX - 1, lowerRight.y()));
        divide(new Position(splitterX + 1, upperLeft.y()), lowerRight);
    }

    private void splitByHorizontalLine(Position upperLeft, Position lowerRight, int splitterY) {
        for (int i = upperLeft.x(); i <= lowerRight.x(); i++) {
            cells[splitterY][i] = Maze.WALL;
        }
        int holeX = randomRange(upperLeft.x() + 1, lowerRight.x() - 1);
        cells[splitterY][holeX] = Maze.EMPTY;

        divide(upperLeft, new Position(lowerRight.x(), splitterY - 1));
        divide(new Position(upperLeft.x(), splitterY + 1), lowerRight);
    }

    private void makeHoles(Position upperLeft, Position lowerRight, int splitterX, int splitterY) {
        Position leftHole = new Position(randomRange(upperLeft.x() + 1, splitterX - 1), splitterY);
        Position rightHole = new Position(randomRange(splitterX + 1, lowerRight.x() - 1), splitterY);
        Position upHole = new Position(splitterX, randomRange(upperLeft.y() + 1, splitterY - 1));
        Position downHole = new Position(splitterX, randomRange(splitterY + 1, lowerRight.y() - 1));
        Position[] possibleHoles = new Position[]{leftHole, rightHole, upHole, downHole};
        Position discludedHole = possibleHoles[randomRange(0, 3)];
        for (Position hole : possibleHoles) {
            cells[hole.y()][hole.x()] = Maze.EMPTY;
        }
        cells[discludedHole.y()][discludedHole.x()] = Maze.WALL;
    }

    private static int randomRange(int min, int max) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }
}