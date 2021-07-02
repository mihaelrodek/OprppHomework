package hr.fer.oprpp1.custom.collections;

/**
 * Implementation of exception class used to represent exception when stack is
 * empty.
 * 
 * @author Mihael Rodek
 *
 */

public class EmptyStackException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3361567786239642550L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor with a message that will be shown when exception is thrown.
	 * 
	 * @param message To be shown
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructor with a message that will be shown when exception is thrown and
	 * cause of exception.
	 * 
	 * @param message To be shown
	 * @param cause   Cause that lead to exception
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with a cause of exception.
	 * 
	 * @param cause Cause that lead to exception
	 */

	public EmptyStackException(Throwable cause) {
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

	protected EmptyStackException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
