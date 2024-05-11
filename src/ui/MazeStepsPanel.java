package ui;

import model.MazeNode;
import model.MazeStep;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MazeStepsPanel extends MazePanel {
    private static final Color TO_VISIT_COLOR = Color.CYAN;
    private static final Color VISITED_COLOR = Color.BLUE;
    private static final Color PATH_COLOR = Color.YELLOW;

    private int stepNumber = 0;
    private ArrayList<MazeStep> steps;
    private List<Position> path;

    private RenderingHints qualityHints;

    /**
     * @param steps Steps the panel can display.
     * @param path  Shortest list of positions leading to target.
     */
    public MazeStepsPanel(ArrayList<MazeStep> steps, List<Position> path) {
        super(steps.get(0).maze());
        this.steps = steps;
        this.path = path;

        qualityHints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        qualityHints.put(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY );
    }

    public void nextStep() {
        Timer stepTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepNumber < steps.size() - 1) {
                    repaint();
                    stepNumber++;
                } else {
                    Timer thisTimer = (Timer) e.getSource();
                    thisTimer.stop();
                }
            }
        });
        stepTimer.start();
    }

    public void lastStep() {
        if (stepNumber <= 0) {
            return;
        }
        stepNumber--;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHints( qualityHints );
        super.paint(graphics);

        MazeStep currStep = steps.get(stepNumber);

        for (MazeNode toVisitNode : currStep.toVisit()) {
            Position toVisitPosition = toVisitNode.position();
            graphics.setColor(TO_VISIT_COLOR);
            RoundedSquareFactory
                    .fillRoundSquare(graphics, toVisitPosition.x() * SCALE, toVisitPosition.y() * 50, SCALE);
        }

        for (MazeNode visitedNode : currStep.visited()) {
            Position visitedPosition = visitedNode.position();
            graphics.setColor(VISITED_COLOR);
            RoundedSquareFactory
                    .fillRoundSquare(graphics, visitedPosition.x() * SCALE, visitedPosition.y() * 50, SCALE);
        }

        if (stepNumber >= steps.size() - 1) {
            drawPath(graphics);
        }
    }

    private void drawPath(Graphics2D graphics) {
        for (Position position : path) {
            graphics.setColor(PATH_COLOR);
            graphics.fillRect(position.x() * SCALE, position.y() * 50, SCALE, SCALE);
        }
    }
}
