package ui;

import model.MazeStep;
import model.Position;

import java.awt.*;
import java.util.ArrayList;

public class MazeStepsPanel extends MazePanel {
    private static final Color TO_VISIT_COLOR = Color.CYAN;
    private static final Color VISITED_COLOR = Color.BLUE;

    private int stepNumber = 0;
    private ArrayList<MazeStep> steps;

    /**
     * @param steps Steps the panel can display.
     */
    public MazeStepsPanel(ArrayList<MazeStep> steps) {
        super(steps.get(0).maze());
        this.steps = steps;
    }

    public void nextStep() {
        if (stepNumber >= steps.size() - 1) {
            return;
        }
        stepNumber++;
        repaint();
    }

    public void lastStep() {
        if (stepNumber <= 0) {
            return;
        }
        stepNumber++;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paint(graphics);

        MazeStep currStep = steps.get(stepNumber);

        for (Position visitedPosition : currStep.visited()) {
            graphics.setColor(VISITED_COLOR);
            graphics.fillRect(visitedPosition.x() * SCALE, visitedPosition.y() * SCALE, SCALE, SCALE);
        }

        for (Position toVisitPosition : currStep.toVisit()) {
            graphics.setColor(TO_VISIT_COLOR);
            graphics.fillRect(toVisitPosition.x() * SCALE, toVisitPosition.y() * SCALE, SCALE, SCALE);
        }
    }
}
