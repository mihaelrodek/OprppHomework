package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Interface used to represent collection of objects.
 *
 * @author Mihael Rodek
 */

public interface Collection {

    /**
     * Checks if collection is empty
     *
     * @return true if collection is empty, false otherwise
     */

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method that creates and returns an instance of ElementsGetter
     *
     * @return ElementsGetter for iterating over a collection
     */

    ElementsGetter createElementsGetter();

    /**
     * Adds into the current collection all elements from the given collection using
     * local class Processor.
     *
     * @param other Collection which elements are added into the current one
     */

    default void addAll(Collection other) {
        other.forEach(this::add);
    }

    /**
     * Method that adds all elements from collection that tester approves into this collection
     *
     * @param col    Collection whose elements are to be tested
     * @param tester used to test elements
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        Objects.requireNonNull(col, "Collection mustn't be null");
        Objects.requireNonNull(tester, "Tester mustn't be null");
        col.createElementsGetter().processRemaining(tester::test);
    }

    /**
     * Method calls processor.process(.) for each element of this collection.
     *
     * @param processor
     */
    default void forEach(Processor processor) {
        Objects.requireNonNull(processor, "Processor mustn't be null");
        this.createElementsGetter().processRemaining(processor::process);
    }

    /**
     * Returns the size of collection
     *
     * @return Set here to return 0
     */

    int size();

    /**
     * Adds the value into the collection
     *
     * @param value Object that needs to be added
     */

    void add(Object value);

    /**
     * Checks if collection contains a given value
     *
     * @param value Value to be checked
     * @return True if collection contains given value, otherwise returns false
     */

    boolean contains(Object value);

    /**
     * Removes given value from collection
     *
     * @param value Value to be removed
     * @return true if value was removed, otherwise false
     */

    boolean remove(Object value);

    /**
     * Method that allocates new array with size equals to the size of this
     * collections, fills it with collection content and returns the array. Here
     * implemented to throw {@link UnsupportedOperationException}
     *
     * @return
     */

    Object[] toArray();

    /**
     * Removes all elements from this collection.
     */

    void clear();

}
