package ui.mazeCreator;

import model.Maze;
import model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutomataMazeGenerator {
    private int[][] cells;
    private int width;
    private int height;

    public int[][] generateMaze(int width, int height) {
        cells = new int[height][width];
        this.width = width;
        this.height = height;

        int iterations = 1000;

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                boolean isWall = new Random().nextBoolean();
                if (isWall) {
                    cells[y][x] = Maze.WALL;
                }
            }
        }

        for (int i = 0; i < iterations; i++) {
            tick();
        }

        Position start = new Position(randomRange(1, width - 1), randomRange(1, height - 1));
        List<Position> path = takeRandomNonRepeatingWalk((width * height / 10), start);
        for (Position walked : path) {
            cells[walked.y()][walked.x()] = Maze.EMPTY;
        }
        cells[path.get(0).y()][path.get(0).x()] = Maze.START;
        cells[path.get(path.size() - 1).y()][path.get(path.size() - 1).x()] = Maze.TARGET;

        return cells;
    }

    private void tick() {
        int[][] nextCells = new int[height][width];
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[0].length; x++) {
                int neighbours = getNeighbourCount(new Position(x, y));
                if (cells[y][x] == Maze.WALL && 1 <= neighbours && neighbours <= 5) {
                    nextCells[y][x] = Maze.WALL;
                } else if (cells[y][x] == Maze.WALL) {
                    nextCells[y][x] = Maze.EMPTY;
                } else if (neighbours == 3 || neighbours == 7) {
                    nextCells[y][x] = Maze.WALL;
                }
            }
        }

        cells = nextCells;
    }

    private int getNeighbourCount(Position location) {
        int neighbourCount = 0;

        Position[] directions = new Position[]{
                new Position(1, 0), new Position(-1, 0),
                new Position(0, 1), new Position(0, -1),
                new Position(1, 1), new Position(-1, -1),
                new Position(1, -1), new Position(-1, 1)
        };

        for (Position direction : directions) {
            Position neighbour = new Position(location.x() + direction.x(), location.y() + direction.y());
            if (isInBounds(neighbour) && cells[neighbour.y()][neighbour.x()] == Maze.WALL) {
                neighbourCount++;
            }
        }

        return neighbourCount;
    }

    private boolean isInBounds(Position position) {
        boolean yInBounds = (0 <= position.y() && position.y() < cells.length);
        boolean xInBounds = (0 <= position.x() && position.x() < cells[0].length);
        return yInBounds && xInBounds;
    }

    private static int randomRange(int min, int max) {
        return (int) ((Math.random() * (max - min + 1)) + min);
    }

    private List<Position> takeRandomNonRepeatingWalk(int steps, Position start) {
        Position prev = start;
        ArrayList<Position> visited = new ArrayList<>();

        for (int i = 0; i < steps; i++) {
            int stepLength = 2 * randomRange(1, 3);
            Position[] possiblePositions = {
                    getUp(prev, stepLength), getDown(prev, stepLength),
                    getLeft(prev, stepLength), getRight(prev, stepLength)
            };
            Position chosenPosition = possiblePositions[randomRange(0, 3)];
            for (Position pathPosition : getPositionsBetween(prev, chosenPosition)) {
                if (!visited.contains(pathPosition) && isInBounds(pathPosition)) {
                    visited.add(pathPosition);
                }
            }

            prev = chosenPosition;
        }

        return visited;
    }

    private List<Position> getPositionsBetween(Position p1, Position p2) {
        List<Position> positionsBetween = new ArrayList<>();
        int xPointer = p1.x();
        int yPointer = p1.y();
        positionsBetween.add(p1);

        while (xPointer != p2.x()) {
            if (p2.x() > xPointer) {
                xPointer++;
            } else {
                xPointer--;
            }
            positionsBetween.add(new Position(xPointer, yPointer));
        }

        while (yPointer != p2.y()) {
            if (p2.y() > yPointer) {
                yPointer++;
            } else {
                yPointer--;
            }
            positionsBetween.add(new Position(xPointer, yPointer));
        }

        positionsBetween.add(p2);
        return positionsBetween;
    }

    private Position getUp(Position p, int stepLength) {
        return new Position(p.x(), p.y() - stepLength);
    }

    private Position getDown(Position p, int stepLength) {
        return new Position(p.x(), p.y() + stepLength);
    }

    private Position getLeft(Position p, int stepLength) {
        return new Position(p.x() - stepLength, p.y());
    }

    private Position getRight(Position p, int stepLength) {
        return new Position(p.x() + stepLength, p.y());
    }
}
