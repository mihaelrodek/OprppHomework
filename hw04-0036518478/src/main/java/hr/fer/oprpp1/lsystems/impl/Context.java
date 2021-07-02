package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class that represents stack of turtles.
 *
 * @author Mihael Rodek
 */
public class Context {

    /**
     * Stack for storing {@link TurtleState}
     */
    private ObjectStack stack;

    /**
     * Default constructor.
     */
    public Context() {
        this.stack = new ObjectStack();
    }

    /**
     * Method that peeks current state at top of the stack
     *
     * @return
     */
    public TurtleState getCurrentState() {
        return (TurtleState) stack.peek();
    }

    /**
     * Method that pushes the state to the stack.
     *
     * @param state
     */
    public void pushState(TurtleState state) {
        stack.push(state);
    }

    /**
     * Method that pops state from stack.
     */
    public void popState() {
        stack.pop();
    }

}
