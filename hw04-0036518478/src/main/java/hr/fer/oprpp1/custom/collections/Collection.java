package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Interface used to represent collection of objects.
 *
 * @param <T> element type stored in this collection
 * @author Mihael Rodek
 */

public interface Collection<T> {

    /**
     * Checks if collection is empty
     *
     * @return Corresponding result
     */

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Creates an instance of {@link ElementsGetter} that can be used to
     * iterate over a list
     *
     * @return {@link ElementsGetter} instance used for iteration
     */

    ElementsGetter<T> createElementsGetter();

    /**
     * Adds into the current collection all elements from the given collection using
     * local class Processor.
     *
     * @param other Collection which elements are added into the current one
     */

    default void addAll(Collection<? extends T> other) {

        Processor proc = value -> add((T) value);

        other.forEach(proc);
    }

    default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
        Objects.requireNonNull(col, "Collection mustn't be null");
        Objects.requireNonNull(tester, "Tester mustn't be null");

        col.createElementsGetter().processRemaining(v -> {
            if (tester.test(v)) {
                this.add(v);
            }
        });
    }

    /**
     * Method calls processor.process(.) for each element of this collection.
     *
     * @param processor used for process elements
     */
    default void forEach(Processor<? super T> processor) {
        Objects.requireNonNull(processor, "Processor mustn't be null");
        this.createElementsGetter().processRemaining(processor);

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

    void add(T value);

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
     * collections, fills it with collection content and returns the array.
     *
     * @return array of collection
     */

    Object[] toArray();

    /**
     * Removes all elements from this collection.
     */

    void clear();

}
