package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Implementation of lexer exception used to represent exception when error
 * occurs while performing lexical analysis.
 *
 * @author Mihael Rodek
 */

public class SmartScriptLexerException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5121010926046699890L;

    /**
     * Default constructor.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Constructor with a message that will be shown when exception is thrown.
     *
     * @param message To be shown
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }

    /**
     * Constructor with a message that will be shown when exception is thrown and
     * cause of exception.
     *
     * @param message To be shown
     * @param cause   Cause that lead to exception
     */
    public SmartScriptLexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause of exception.
     *
     * @param cause Cause that lead to exception
     */
    public SmartScriptLexerException(Throwable cause) {
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
    protected SmartScriptLexerException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
