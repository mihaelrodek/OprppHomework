package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.math.Vector2D;

import java.awt.*;

/**
 * Class that represents a turtle in space.
 *
 * @author Mihael Rodek
 */
public class TurtleState {

    /**
     * Current position of turtle
     */
    Vector2D currentPosition;

    /**
     * Current direction in which turtle "looks"
     */
    Vector2D turtleDirection;

    /**
     * Color with which turtle is painting
     */
    Color turtleColor;

    /**
     * Current effective shift length
     */
    double shiftLength;

    /**
     * Default constructor for turtle
     *
     * @param currentPosition turtle position
     * @param turtleDirection turtle direction
     * @param turtleColor     turtle color
     * @param shiftLength     turtle shift length
     */
    public TurtleState(Vector2D currentPosition, Vector2D turtleDirection, Color turtleColor, double shiftLength) {
        this.currentPosition = currentPosition;
        this.turtleDirection = turtleDirection;
        this.turtleColor = turtleColor;
        this.shiftLength = shiftLength;
    }

    public TurtleState copy() {
        return new TurtleState(currentPosition.copy(), turtleDirection.copy(), new Color(turtleColor.getRGB()), shiftLength);
    }

    /**
     * Getter for currentPosition
     *
     * @return currentPosition
     */
    public Vector2D getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Getter for turtleDirection
     *
     * @return turtleDirection
     */
    public Vector2D getTurtleDirection() {
        return turtleDirection;
    }

    /**
     * Getter for turtleColor
     *
     * @return turtleColor
     */
    public Color getTurtleColor() {
        return turtleColor;
    }

    /**
     * Getter for shiftLength
     *
     * @return shiftLength
     */
    public double getShiftLength() {
        return shiftLength;
    }

    /**
     * Setter for currentPosition
     *
     * @param currentPosition turtle position
     */
    public void setCurrentPosition(Vector2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Setter for currentPosition
     *
     * @param turtleDirection turtle direction
     */
    public void setTurtleDirection(Vector2D turtleDirection) {
        this.turtleDirection = turtleDirection;
    }

    /**
     * Setter for currentPosition
     *
     * @param turtleColor turtle color
     */
    public void setTurtleColor(Color turtleColor) {
        this.turtleColor = turtleColor;
    }

    /**
     * Setter for currentPosition
     *
     * @param shiftLength turtle shift length
     */
    public void setShiftLength(double shiftLength) {
        this.shiftLength = shiftLength;
    }

}
