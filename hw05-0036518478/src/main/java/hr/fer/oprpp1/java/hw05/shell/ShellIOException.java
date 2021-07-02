package hr.fer.oprpp1.java.hw05.shell;


/**
 * Class that represents shell exception.
 */
public class ShellIOException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ShellIOException() {
        super();
    }

    /**
     * Constructor with a message that will be shown when exception is thrown.
     *
     * @param message To be shown
     */
    public ShellIOException(String message) {
        super(message);
    }

    /**
     * Constructor with a message that will be shown when exception is thrown and
     * cause of exception.
     *
     * @param message To be shown
     * @param cause   Cause that lead to exception
     */
    public ShellIOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause of exception.
     *
     * @param cause Cause that lead to exception
     */

    public ShellIOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with message, cause, suppression enabled
     * or disabled, and writableStackTrace enabled or disabled.
     *
     * @param message            To be shown
     * @param cause              Cause that lead to exception
     * @param enableSuppression  Flag for suppression
     * @param writableStackTrace Flag for stack trace
     */

    protected ShellIOException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
