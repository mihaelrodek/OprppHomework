package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

/**
 * Class used to pop one state from stack
 *
 * @author Mihael Rodek
 */
public class PopCommand implements Command {
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
