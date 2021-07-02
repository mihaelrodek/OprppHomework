package hr.fer.zemris.java.gui.layouts;

/**
 * Class that represents exception thrown while working with calculator layout.
 */
public class CalcLayoutException extends RuntimeException{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3361567786239642550L;

    /**
     * Default constructor.
     */
    public CalcLayoutException() {
        super();
    }

    /**
     * Constructor with a message that will be shown when exception is thrown.
     *
     * @param message To be shown
     */
    public CalcLayoutException(String message) {
        super(message);
    }

    /**
     * Constructor with a message that will be shown when exception is thrown and
     * cause of exception.
     *
     * @param message To be shown
     * @param cause   Cause that lead to exception
     */
    public CalcLayoutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause of exception.
     *
     * @param cause Cause that lead to exception
     */

    public CalcLayoutException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * Constructs a new runtime exception with message, cause, suppression enabled
     * or disabled, and writableStackTrace enabled or disabled.
     *
     * @param message            To be shown
     * @param cause              Cause that lead to exception
     * @param enableSuppression  Flag for suppression
     * @param writableStackTrace Flag for stack trace
     */

    protected CalcLayoutException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
