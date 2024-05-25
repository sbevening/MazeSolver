package ui.styling;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StyleUtilities {
    public static void styleFlatBordered(JButton button) {
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }
}
