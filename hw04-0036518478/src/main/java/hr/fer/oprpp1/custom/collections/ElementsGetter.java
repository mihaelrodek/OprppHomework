package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Interface used to iterate over elements in given list
 *
 * @param <T> type of elements in list
 */
public interface ElementsGetter<T> {

    /**
     * Method that checks if list has next element that haven't yet be used, but doesn't return it
     *
     * @return true if it contains an element that wasn't used, otherwise false
     */
    boolean hasNextElement();

    /**
     * Method that returns next element in list if it exists
     *
     * @return element in list that haven't yet be used
     */
    T getNextElement();

    /**
     * Processes all remaining elements in list using the Processor
     *
     * @param p {@link Processor} for processing elements
     */

    default void processRemaining(Processor<? super T> p) {
        Objects.requireNonNull(p, "It mustn't be null");
        do {
            p.process(this.getNextElement());
        } while (this.hasNextElement());
    }

}
