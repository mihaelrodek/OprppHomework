package hr.fer.zemris.java.gui.charts;

/**
 * Class that represent x and y value.
 */
public class XYValue {

    /**
     * X value.
     */
    private int x;

    /**
     * Y value.
     */
    private int y;

    /**
     * Default constructor.
     * @param x x value
     * @param y y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x
     * @return x
     */

    public int getX() {
        return x;
    }

    /**
     * getter for y
     * @return y
     */
    public int getY() {
        return y;
    }
}
