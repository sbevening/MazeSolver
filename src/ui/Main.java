package ui;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Creator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        MazeCreatorPanel mcp = new MazeCreatorPanel();
        frame.add(mcp, BorderLayout.CENTER);
        frame.add(new MazeCreatorButtons(mcp), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}