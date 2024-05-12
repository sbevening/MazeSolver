package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A set of buttons that can be used to control a MazeStepsPanel.
 */
public class MazeStepButtons extends JPanel {
    public MazeStepButtons(MazeStepsPanel mazeStepsPanel) {
        setLayout(new GridLayout(1, 3));
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 100));
        add(rewindButton(mazeStepsPanel));
        add(pauseButton(mazeStepsPanel));
        add(playButton(mazeStepsPanel));
    }

    private static JButton generateButtonWithListener(ActionListener listener, String label) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    /**
     * @param mazeStepsPanel The panel which this button will control/play.
     * @return JButton that can play the steps in solving a maze in mazeStepsPanel.
     */
    public static JButton playButton(MazeStepsPanel mazeStepsPanel) {
        return generateButtonWithListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeStepsPanel.playSteps();
            }
        }, "play");
    }

    /**
     * @param mazeStepsPanel The panel which this button will control/rewind.
     * @return JButton that can rewind the steps in solving a maze in mazeStepsPanel.
     */
    public static JButton rewindButton(MazeStepsPanel mazeStepsPanel) {
        return generateButtonWithListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeStepsPanel.rewindSteps();
            }
        }, "rewind");
    }

    /**
     * @param mazeStepsPanel The panel which this button will control/rewind.
     * @return JButton that can pause the timers going through steps in solving a maze in mazeStepsPanel.
     */
    public static JButton pauseButton(MazeStepsPanel mazeStepsPanel) {
        return generateButtonWithListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeStepsPanel.pauseSteps();
            }
        }, "pause");
    }
}
