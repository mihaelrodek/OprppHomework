package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;

import java.util.Objects;

/**
 * Class that represents turtle skipping a single line by not drawing t.
 *
 * @author Mihael Rodek
 */
public class SkipCommand implements Command {
    /**
     * Step length of line which will be skipped
     */
    private double step;

    /**
     * Default constructor.
     *
     * @param step length of line
     */

    public SkipCommand(double step) {
        this.step = step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        Vector2D currentPosition = currentState.getCurrentPosition();
        Vector2D direction = currentState.getTurtleDirection();
        double moveLength = currentState.getShiftLength();
        double scaledStep = moveLength * step;

        Vector2D position = currentPosition.added(direction.scaled(scaledStep));

        currentState.setCurrentPosition(position);
    }

    /**
     * Getter for step
     *
     * @return step
     */
    public double getStep() {
        return step;
    }
}
