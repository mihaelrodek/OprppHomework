package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class that copies current state on top of the stack and pushes it again on stack
 *
 * @author Mihael Rodek
 */
public class PushCommand implements Command {
    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState state = ctx.getCurrentState();
        ctx.pushState(state.copy());
    }
}
