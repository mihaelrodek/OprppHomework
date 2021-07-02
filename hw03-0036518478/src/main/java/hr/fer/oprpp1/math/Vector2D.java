package hr.fer.oprpp1.math;

/**
 * Class for modelling a 2D vector.
 */
public class Vector2D {
    /**
     * X coordinate of 2D vector.
     */
    private double x;

    /**
     * Y coordinate of 2D vector.
     */
    private double y;

    /**
     * Default constructor for 2D vector.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x coordinate.
     *
     * @return x-coordinate
     */

    public double getX() {
        return x;
    }

    /**
     * Getter for y coordinate.
     *
     * @return y-coordinate
     */

    public double getY() {
        return y;
    }

    /**
     * Method that adds given offset to 2D vector
     *
     * @param offset Offset vector
     */

    public void add(Vector2D offset) {
        this.x += offset.getX();
        this.y += offset.getY();
    }

    /**
     * Method that adds given offset to 2D vector but returns a newly created vector
     *
     * @param offset Offset vector
     * @return new 2D vector
     */

    public Vector2D added(Vector2D offset) {
        return new Vector2D(this.x + offset.getX(), this.y + offset.getY());
    }

    /**
     * Method used for rotating this vector by a given angle value
     *
     * @param angle Angle in radians
     */

    public void rotate(double angle) {
        double newX = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        double newY = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        x = newX;
        y = newY;
    }

    /**
     * Method used for rotating vector by a given angle value, but returns a newly created 2D vector
     *
     * @param angle Angle in radians
     * @return new 2D rotated vector
     */

    public Vector2D rotated(double angle) {
        return new Vector2D(this.x * Math.cos(angle) - this.y * Math.sin(angle), this.x * Math.sin(angle) + this.y * Math.cos(angle));
    }

    /**
     * Method that scales this vector by a given scaler
     *
     * @param scaler value used to scale the vector
     */

    public void scale(double scaler) {
        this.x *= scaler;
        this.y *= scaler;
    }

    /**
     * Method that scales vector by a given scaler but returns a newly created 2D vector
     *
     * @param scaler value used to scale the vector
     * @return new 2D scaled vector
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(this.x *= scaler, this.y *= scaler);
    }

    /**
     * Method that returns new exact copy of this vector
     *
     * @return new 2D copied vector
     */

    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }
}
