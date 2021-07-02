package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class that represents rotating a turtel for given angle
 *
 * @author Mihael Rodek
 */
public class RotateCommand implements Command {
    /**
     * Angle of turtle
     */
    private double angle;

    /**
     * Default constructor.
     *
     * @param angle angle to be rotated for
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        currentState.setTurtleDirection(currentState.getTurtleDirection().rotated(angle).normalized());
    }

    /**
     * Getter for angle
     *
     * @return angle
     */
    public double getAngle() {
        return angle;
    }

}
