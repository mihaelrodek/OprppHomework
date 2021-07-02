package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class that represents scaling current shift length by a given factor.
 *
 * @author Mihael Rodek
 */
public class ScaleCommand implements Command {


    /**
     * Factor to scale with
     */
    private double factor;

    /**
     * Default constructor
     *
     * @param factor factor to scale with
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState state = ctx.getCurrentState();
        state.setShiftLength(state.getShiftLength() * factor);
    }

    /**
     * Getter for factor
     *
     * @return factor
     */
    public double getFactor() {
        return factor;
    }


}
