package hr.fer.oprpp1.custom.collections;

/**
 * Functional interface used for representing processor
 *
 * @param <T> type of processor
 * @author Mihael Rodek
 */
@FunctionalInterface
public interface Processor<T> {

    /**
     * This method is used for performing some action on given object.
     *
     * @param value Value that needs to be processed
     */

    void process(T value);

}
