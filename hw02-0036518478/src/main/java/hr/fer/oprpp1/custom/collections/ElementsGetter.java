package hr.fer.oprpp1.custom.collections;

import java.util.Objects;
import java.util.NoSuchElementException;

/**
 * Interface used as an element getter that iterates over collection. It has all methods needed for getting the element.
 *
 * @author Mihael Rodek
 */

public interface ElementsGetter {

    /**
     * Method that checks if there is an element in collection that hasn't been used yet.
     *
     * @return true if there is, false otherwise
     */
    boolean hasNextElement();

    /**
     * Method that returns next element in iteration if it exists.
     *
     * @return next element
     * @throws @{@link NoSuchElementException} if there is no left elements in collection
     */

    Object getNextElement();

    /**
     * Method that processes all not used elements using the {@link Processor}
     *
     * @param p Processor that is used to process elements
     */
    default void processRemaining(Processor p) {
        Objects.requireNonNull(p, "It mustn't be null");
        while (this.hasNextElement())
            p.process(this.getNextElement());
    }

}
