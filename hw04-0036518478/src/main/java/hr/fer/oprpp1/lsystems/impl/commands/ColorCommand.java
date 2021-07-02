package hr.fer.oprpp1.lsystems.impl.commands;

import hr.fer.oprpp1.lsystems.impl.Command;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.zemris.lsystems.Painter;

import java.awt.*;

/**
 * Class that represents change of color used to paint with
 *
 * @author Mihael Rodek
 */
public class ColorCommand implements Command {

    /**
     * Color used to paint with
     */
    private Color color;

    /**
     * Default constructor
     *
     * @param color color used to paint with
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setTurtleColor(getColor());
    }

    /**
     * Getter for color
     *
     * @return color
     */
    public Color getColor() {
        return color;
    }
}
