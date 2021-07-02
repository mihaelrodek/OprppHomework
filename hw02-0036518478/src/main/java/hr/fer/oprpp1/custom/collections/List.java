package hr.fer.oprpp1.custom.collections;

/**
 * Interface used to represent a collection of Objects
 *
 * @author Mihael Rodek
 */
public interface List extends Collection {

    /**
     * Method that gets the object that is stored in collection at position index.
     *
     * @param index Position of wanted element
     * @return value of element at given index
     */
    Object get(int index);

    /**
     * Method that inserts the given value at the given position in array.
     *
     * @param value    element to be inserted into collection
     * @param position Position where value is inserted
     */
    void insert(Object value, int position);

    /**
     * Method searches the collection and returns the index of the first occurrence of the given value.
     *
     * @param value value to be found in collection
     * @return position of given value if index was found, -1 if value is null or cannot be found
     */
    int indexOf(Object value);

    /**
     * Method that removes element that is stored at specified index position.
     *
     * @param index position of index to remove
     */
    void remove(int index);

}
