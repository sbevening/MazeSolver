package test;

import model.Maze;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MazeTest {
    @Test
    public void testIsValidValidMaze() {
        try {
            new Maze(new int[][]{{0, 2}, {1, 3}});
            new Maze(new int[][]{{3, 2, 0}, {1, 0, 0}});
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIsValidInvalidNumbers() {
        try {
            new Maze(new int[][]{{0, 0}, {1, 4}});
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testIsValidInvalidNumberSpecialCells() {
        int caught = 0;
        try {
            new Maze(new int[][]{{2, 2, 0}, {1, 0, 0}});
        } catch (IllegalArgumentException e) {
            caught++;
        }
        try {
            new Maze(new int[][]{{3, 2, 0}, {3, 0, 0}});
        } catch (IllegalArgumentException e) {
            caught++;
        }
        try {
            new Maze(new int[][]{{0, 0}, {1, 1}});
        } catch (IllegalArgumentException e) {
            caught++;
        }
        try {
            new Maze(new int[][]{{3, 0}, {1, 1}});
        } catch (IllegalArgumentException e) {
            caught++;
        }

        assertEquals(4, caught);
    }

    @Test
    public void testToString() {
        Maze maze = new Maze(new int[][]{{1, 1, 2, 1}, {1, 0, 0, 1}, {1, 3, 1, 1}});
        assertEquals("1 1 2 1\n1 0 0 1\n1 3 1 1", maze.toString());
    }
}
