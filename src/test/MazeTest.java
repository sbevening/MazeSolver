package test;

import model.Maze;
import model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MazeTest {
    Maze solvableMaze1;
    Maze solvableMaze2;
    Maze unsolvableMaze1;
    Maze unsolvableMaze2;

    @Before
    public void runBefore() {
        solvableMaze1 = new Maze(new int[][]{{1, 0, 2, 1}, {1, 0, 0, 1}, {1, 3, 1, 1}});
        solvableMaze2 = new Maze(new int[][]{{1, 1}, {3, 2}});
        unsolvableMaze1 = new Maze(new int[][]{{1, 1, 1, 1}, {1, 3, 1, 1}, {1, 1, 1, 0}, {2, 0, 0, 0}});
        unsolvableMaze2 = new Maze(new int[][]{{1, 1, 3}, {2, 1, 0}, {0, 0, 1}});
    }

    @Test
    public void testConstructorValidMaze() {
        try {
            new Maze(new int[][]{{0, 2}, {1, 3}});
            new Maze(new int[][]{{3, 2, 0}, {1, 0, 0}});
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testConstructorInvalidNumbers() {
        try {
            new Maze(new int[][]{{0, 0}, {1, 4}});
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testConstructorInvalidNumberSpecialCells() {
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

    @Test public void testConstructorEmptyMaze() {
        try {
            new Maze(new int[][]{{}});
            fail();
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    @Test
    public void testToString() {
        assertEquals("1 0 2 1\n1 0 0 1\n1 3 1 1", solvableMaze1.toString());
    }

    @Test
    public void testGetCellByPosition() {
        assertEquals(1, solvableMaze1.getCellByPosition(new Position(0, 0)));
        assertEquals(1, solvableMaze1.getCellByPosition(new Position(3, 2)));
        try {
            solvableMaze1.getCellByPosition(new Position(4, 4));
            fail();
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    public void testGetCellByIndex() {
        assertEquals(1, solvableMaze1.getCellByIndex(0));
        assertEquals(1, solvableMaze1.getCellByIndex(11));
        assertEquals(2, solvableMaze1.getCellByIndex(2));
        try {
            solvableMaze1.getCellByIndex(12);
            fail();
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    public void testSolveMaze() {
        assertTrue(solvableMaze1.solveMaze());
        assertTrue(solvableMaze2.solveMaze());
        assertFalse(unsolvableMaze1.solveMaze());
        assertFalse(unsolvableMaze2.solveMaze());
    }
}
