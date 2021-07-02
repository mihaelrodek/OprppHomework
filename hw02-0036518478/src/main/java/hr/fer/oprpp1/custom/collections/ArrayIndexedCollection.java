package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Program that stores elements in an array. It represents implementation of
 * resizable array in which duplicates are allowed but null references aren't.
 *
 * @author Mihael Rodek
 */

public class ArrayIndexedCollection implements List {

    private int size;
    private Object[] elements;
    int capacity;
    static final int CAPACITY = 16;

    private long modificationCount = 0;

    /**
     * Default constructor that delegates capacity to a more complexed one. Calling
     * this costructor capaacity is set to 16.
     */

    public ArrayIndexedCollection() {
        this(CAPACITY);
    }

    /**
     * Constructor that sets capacity to initialCapacity.
     *
     * @param initialCapacity that capacity should be set to
     * @throws IllegalArgumentException if given capacity is lower than 1
     */

    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("Capacity must not be lower than 1");
        this.size = 0;
        this.capacity = initialCapacity;
        elements = new Object[capacity];
    }

    /**
     * Constructor that copies given collection into a new one, it delegates given
     * collection to a more complexed constructor.
     *
     * @param collection reference to a collection whose elements are copied into
     *                   the new one
     */
    public ArrayIndexedCollection(Collection collection) {
        this(collection, CAPACITY);
    }

    /**
     * Constructor that copies given collection into a new one. If given
     * initialCapacity is lower than capacity of given collection, new capacity is
     * set to collection's capacity, otherwise it sets it to initialCapacity.
     * Copying elements is done using addAll method.
     *
     * @param collection      Collection whose elements are copied into a new one
     * @param initialCapacity Capacity of collection to be set, if it's lower than
     *                        size of given collection, the size of collection is
     *                        used instead
     * @throws NullPointerException if given collection is null
     */

    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        Objects.requireNonNull(collection, "Collection mustn't be null");

        if (initialCapacity < 1)
            throw new IllegalArgumentException("Capacity must not be lower than 1");
        else this.capacity = Math.max(initialCapacity, collection.size());

        elements = new Object[capacity];
        addAll(collection);
    }

    /**
     * Returns the size of given collection.
     */

    @Override
    public int size() {
        return size;
    }

    /**
     * Adds given value into a collection.Method checks if array is full, if so it
     * calls the function to reallocate array by doubling its size.
     *
     * @param value value that needs to be added
     * @throws NullPointerException if given value is null
     */

    @Override
    public void add(Object value) {
        Objects.requireNonNull(value, "Cannot add null value");
        if (size == capacity) {
            elements = doubleSize(elements);
        }
        elements[size] = value;

        modificationCount++;
        size++;
    }

    /**
     * Checks if collection contains given value.
     *
     * @param value Value to be checked
     * @return true if contains given value, otherwise false
     */

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Removes given value from a collection.
     *
     * @param value Value that needs to be removed
     * @return false if couldn't remove value<br>
     * true if value was succesfully removed
     */
    @Override
    public boolean remove(Object value) {
        if (indexOf(value) == -1)
            return false;
        remove(indexOf(value));
        return true;
    }

    /**
     * Allocates new array with size equals to the size of this collections, fills
     * it with collection content and returns the array.
     */

    @Override
    public Object[] toArray() {
        Object[] tmp = new Object[size];
        int count = 0, counter = 0;
        while (counter < size) {
            if (elements[counter] != null)
                tmp[count++] = elements[counter++];
        }
        return tmp;
    }

    /**
     * Method that removes elements from collection by writing null references into
     * collection.
     */

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        modificationCount++;
    }

    /**
     * @throws IndexOutOfBoundsException if index is lower than 0 and greater than size-1
     */
    @Override
    public Object get(int index) {
        if (index > (size - 1) || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");
        else
            return elements[index];
    }

    /**
     * Method that inserts the given value at the given position in array. Method
     * checks if array is full, if so it calls the function to reallocate array by
     * doubling its size.
     *
     * @throws IndexOutOfBoundsException if index is lower than 0 and greater than
     *                                   size
     * @throws NullPointerException      if given value is null reference
     */

    @Override
    public void insert(Object value, int position) {
        Objects.requireNonNull(value, "Cannot add null as an element.");
        if (position > size || position < 0)
            throw new IndexOutOfBoundsException("Position of index is out of range");
        if (size == capacity) {
            doubleSize(elements);
        }
        if (size - position >= 0) System.arraycopy(elements, position, elements, position + 1, size - position);
        elements[position] = value;
        modificationCount++;
        size++;
    }

    /**
     * Method that resizes capacity of array by doubling it
     *
     * @param elements Collection which needs to be resized
     * @return new resized collection
     */

    public Object[] doubleSize(Object[] elements) {
        capacity = capacity * 2;
        Object[] tmp = new Object[capacity];
        if (size >= 0) System.arraycopy(elements, 0, tmp, 0, size);
        modificationCount++;
        return tmp;

    }

    @Override
    public int indexOf(Object value) {
        if (value != null) {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(value))
                    return i;
            }
        }
        return -1;
    }

    /**
     * @throws IndexOutOfBoundsException if index is lower than 0 or greater than size-1
     */
    @Override
    public void remove(int index) {
        if (index > (size - 1) || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");

        if (size - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);

        modificationCount++;
        size--;
    }

    /**
     * Class representation of {@link ElementsGetter} for {@link ArrayIndexedCollection}
     */
    private static class ElementsGetterArrayImplementation implements ElementsGetter {

        /**
         * Index of current collection, used as counter of elements
         */
        int index = 0;

        /**
         * Collection to iterate over it
         */
        private final ArrayIndexedCollection collection;

        /**
         * Saved number of modifications, used to compare if any change was made after ElementsGetter was created
         */
        private final long savedModificationCount;

        /**
         * Default constructor for @{@link ElementsGetterArrayImplementation}
         *
         * @param col collection used for iterating
         * @throws NullPointerException if given collection is null
         */
        public ElementsGetterArrayImplementation(ArrayIndexedCollection col) {
            Objects.requireNonNull(col, "Collection musn't be null");
            this.collection = col;
            this.savedModificationCount = col.modificationCount;
        }

        /**
         * @throws ConcurrentModificationException if collection was changed after {@link ElementsGetter} was created
         */

        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has changed!");
            return collection.size > index;
        }

        /**
         * @throws @{@link NoSuchElementException} if there is no left elements in collection
         */

        @Override
        public Object getNextElement() {
            if (!hasNextElement()) {
                throw new NoSuchElementException("No more nodes");
            }
            return collection.elements[index++];
        }

    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new ElementsGetterArrayImplementation(this);
    }

}
