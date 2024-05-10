package ui;

import java.awt.*;

public class RoundedSquareFactory {
    public static void fillRoundSquare(Graphics g, int x, int y, int scale) {
        g.fillRoundRect(x, y, scale, scale, scale / 4, scale / 4);
    }
}
