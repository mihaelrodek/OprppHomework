package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Implementation of parser exception used to represent exception when error
 * occurs while performing parser tasks.
 *
 * @author Mihael Rodek
 */

public class SmartScriptParserException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7298940776722804518L;

    /**
     * Default constructor.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructor with a message that will be shown when exception is thrown.
     *
     * @param message To be shown
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructor with a message that will be shown when exception is thrown and
     * cause of exception.
     *
     * @param message To be shown
     * @param cause   Cause that lead to exception
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause of exception.
     *
     * @param cause Cause that lead to exception
     */
    public SmartScriptParserException(Throwable cause) {
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
    protected SmartScriptParserException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
