package hr.fer.oprpp1.custom.collections;

/**
 * Interface used for representing processor. Has a single method.
 * 
 * @author Mihael Rodek
 *
 */
public interface Processor {

	/**
	 * Abstract method that is used for performing some action on given object.
	 *
	 * @param value Value that needs to be processed
	 */

	void process(Object value);

}
