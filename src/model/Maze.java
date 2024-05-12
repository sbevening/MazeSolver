package model;

import java.util.*;

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
    private final int size;
    private Position startPosition;

    private MazeSolverStepTracker stepTracker;

    /**
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
        size = mazeHeight * mazeWidth;
    }

    /**
     * @return The value of the cell at the nth overall position in the maze
     */
    public int getCellByPosition(Position pos) {
        if (pos.x() >= mazeWidth || pos.y() >= mazeHeight) {
            throw new IndexOutOfBoundsException();
        }
        return maze[pos.y()][pos.x()];
    }

    /**
     * @return The value of the cell at the specified index in the maze, with indexes ascending from left to right
     * then top to bottom
     */
    public int getCellByIndex(int pos) {
        if (pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        int columnIndex = pos / mazeWidth; // floor division by default
        int rowIndex = pos % mazeWidth;
        return maze[columnIndex][rowIndex];
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    /**
     * @return Returns true if the maze contains <b>exactly one target square</b> and <b>exactly one start square</b>,
     * and only numbers that correspond to cells.
     */
    private Boolean isValid() {
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

    /**
     * Assigns startPosition to appropriate position.
     */
    private void locateStartSquare() {
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                if (maze[y][x] == START) {
                    startPosition = new Position(x, y);
                    return;
                }
            }
        }
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

    @Override
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

    /**
     * @return Using a depth first search, attempts to find the solution square starting from startPosition. Returns
     * the fastest path to the node if the maze is solvable and null otherwise.
     */
    public List<Position> solveMaze() {
        locateStartSquare();
        Set<MazeNode> visited = new HashSet<>();
        LinkedList<MazeNode> toVisit = new LinkedList<>();
        toVisit.add(new MazeNode(new Position(startPosition.x(), startPosition.y()), null));

        while (!toVisit.isEmpty()) {
            MazeNode currNode = toVisit.poll();
            Position currPosition = currNode.position();
            if (getCellByPosition(currPosition) == TARGET) {
                return generatePath(currNode);
            }

            for (Position nextPosition : generateNextPositions(currPosition)) {
                MazeNode nextNode = new MazeNode(nextPosition, currNode);
                if (getCellByPosition(nextPosition) != WALL
                        && !(visited.contains(nextNode))
                        && !(toVisit.contains(nextNode))) {
                    toVisit.add(nextNode);
                }
            }

            if (!visited.contains(currNode)) {
                visited.add(currNode);
                if (stepTracker != null) {
                    stepTracker.recordStep(this, new HashSet<>(visited), new LinkedList<>(toVisit));
                }
            }
        }

        return null;
    }

    /**
     * @param currPosition The position that will be used to find the locations of the next positions.
     * @return An array of the next, neighbouring positions that are within the maze.
     */
    private Position[] generateNextPositions(Position currPosition) {
        int currX = currPosition.x();
        int currY = currPosition.y();

        Position up = new Position(currX, currY - 1);
        Position right = new Position(currX + 1, currY);
        Position down = new Position(currX, currY + 1);
        Position left = new Position(currX - 1, currY);

        Position[] nextPositions = new Position[]{up, right, down, left};
        ArrayList<Position> inBoundsNextPositions = new ArrayList<>();
        for (Position p : nextPositions) {
            if (isValidPosition(p)) {
                inBoundsNextPositions.add(p);
            }
        }
        return inBoundsNextPositions.toArray(new Position[0]);
    }

    /**
     * @param p A given position
     * @return True if position is in bounds with respect to the maze and false otherwise.
     */
    private boolean isValidPosition(Position p) {
        boolean yInBounds = (0 <= p.y() && p.y() < mazeHeight);
        boolean xInBounds = (0 <= p.x() && p.x() < mazeWidth);
        return yInBounds && xInBounds;
    }

    public void assignStepTracker(MazeSolverStepTracker stepTracker) {
        this.stepTracker = stepTracker;
    }

    private ArrayList<Position> generatePath(MazeNode targetNode) {
        ArrayList<Position> path = new ArrayList<>();
        while (targetNode != null) {
            path.add(0, targetNode.position());
            targetNode = targetNode.parent();
        }
        return path;
    }
}
