package hr.fer.oprpp1.custom.collections;

/**
 * Interface that represents collection
 *
 * @param <T> type of elements that are stored in list
 */
public interface List<T> extends Collection<T> {

    /**
     * Returns object stored at position index
     *
     * @param index index to be searched for
     * @return object stored at index position
     */
    T get(int index);

    /**
     * Inserts given value at given position in list
     *
     * @param value    object to be inserted
     * @param position position in which to insert
     */

    void insert(T value, int position);

    /**
     * Returns the first index where given value is stored
     *
     * @param value value to be searched for
     * @return index of position if it was found
     */
    int indexOf(Object value);

    /**
     * Removes element at given index from list
     *
     * @param index position to be removed
     */
    void remove(int index);

}
