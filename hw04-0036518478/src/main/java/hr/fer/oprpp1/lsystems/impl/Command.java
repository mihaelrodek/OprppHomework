package hr.fer.oprpp1.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface that represents command.
 */
@FunctionalInterface
public
interface Command {
    /**
     * Method that executes action usin given context and painter
     *
     * @param ctx     context
     * @param painter painter
     */
    void execute(Context ctx, Painter painter);
}
