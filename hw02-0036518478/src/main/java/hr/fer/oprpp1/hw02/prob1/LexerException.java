package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of exception class used to represent exception when tokenizing a string.
 *
 * @author Mihael Rodek
 */
public class LexerException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4839091387079137230L;

    /**
     * Default constructor.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructor with a message that will be shown when exception is thrown.
     *
     * @param message to be shown
     */
    public LexerException(String message) {
        super(message);
    }

    /**
     * Constructor with a message that will be shown when exception is thrown and cause of exception.
     *
     * @param message to be shown
     * @param cause   Cause that lead to exception
     */
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause of exception.
     *
     * @param cause Cause that lead to exception
     */
    public LexerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with message, cause, suppression enabled or disabled, and writableStackTrace enabled or disabled.
     *
     * @param message            to be shown
     * @param cause              cause that lead to exception
     * @param enableSuppression  flag for suppression
     * @param writableStackTrace Flag for stack trace
     */
    protected LexerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
