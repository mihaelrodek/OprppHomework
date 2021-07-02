package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Program that stores elements in an array. It represents implementation of
 * resizable array in which duplicates are allowed but null references aren't.
 *
 * @param <T> element type stored in this list
 * @author Mihael Rodek
 */

public class ArrayIndexedCollection<T> implements List<T> {

    private int size;
    private T[] elements;
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
        elements = (T[]) new Object[capacity];
    }

    /**
     * Constructor that copies given collection into a new one, it delegates given
     * collection to a more complexed constructor.
     *
     * @param collection reference to a collection whose elements are copied into
     *                   the new one
     */
    public ArrayIndexedCollection(Collection<? extends T> collection) {
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

    public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
        Objects.requireNonNull(collection, "Collection mustn't be null");

        if (initialCapacity < 1)
            throw new IllegalArgumentException("Capacity must not be lower than 1");
        else this.capacity = Math.max(initialCapacity, collection.size());

        elements = (T[]) new Object[capacity];
        addAll(collection);
    }
    /*
     * Checks if collections is empty.
     */

    @Override
    public boolean isEmpty() {
        return size == 0;
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
    public void add(T value) {
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
    public T[] toArray() {
        T[] tmp = (T[]) new Object[size];
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
        Arrays.fill(elements, 0, size, null);
        size = 0;
        modificationCount++;
    }

    /**
     * Method that gets the object that is stored collection at position index.
     *
     * @param index Position of wanted element
     * @return Value of element at given index
     * @throws IndexOutOfBoundsException if index is lower than 0 and greater than
     *                                   size-1
     */

    public T get(int index) {
        if (index > (size - 1) || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");
        else
            return elements[index];
    }

    /**
     * Method that inserts the given value at the given position in array.Method
     * checks if array is full, if so it calls the function to reallocate array by
     * doubling its size.
     *
     * @param value    element to be inserted into collection
     * @param position Position where value is inserted
     * @throws IndexOutOfBoundsException if index is lower than 0 and greater than
     *                                   size
     * @throws NullPointerException      if given value is null reference
     */

    public void insert(T value, int position) {
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

    public T[] doubleSize(T[] elements) {
        capacity = capacity * 2;
        Object[] tmp = new Object[capacity];
        if (size >= 0) System.arraycopy(elements, 0, tmp, 0, size);
        modificationCount++;
        return (T[]) tmp;

    }

    /**
     * Searches the collection and returns the index of the first occurrence of the
     * given value. If value isn't found it returns -1. If value is null it returns
     * as element is not found.
     *
     * @param value Value to be found in collection
     * @return position of given value<br>
     * -1 if value is null or cannot be found
     */

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
     * Method that removes element that is stored at specified index position.
     *
     * @param index Position of index to remove
     * @throws IndexOutOfBoundsException if index is lower than 0 or greater than
     *                                   size-1
     */
    public void remove(int index) {
        if (index > (size - 1) || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");

        if (size - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);

        modificationCount++;
        size--;
    }


    @Override
    public ElementsGetter<T> createElementsGetter() {

        return new ElementsGetterArrayImplementation(this);
    }

    /**
     * Class that implements {@link ElementsGetter} for an instance of {@link ArrayIndexedCollection}
     */

    private class ElementsGetterArrayImplementation implements ElementsGetter<T> {

        /**
         * Index of current element
         */
        int index = 0;
        /**
         * Collection to iterate
         */
        private final ArrayIndexedCollection<T> collection;

        /**
         * Number of modifications when {@link ElementsGetterArrayImplementation} is created
         */
        private final long savedModificationCount;

        /**
         * Default constructor
         */

        public ElementsGetterArrayImplementation(ArrayIndexedCollection<T> col) {
            Objects.requireNonNull(col, "Collection musn't be null");
            this.collection = col;
            this.savedModificationCount = col.modificationCount;
        }

        /**
         * @throws ConcurrentModificationException if collection has changed between calling hasNextElement
         */

        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has changed!");
            return collection.size > index;
        }

        /**
         * @throws UnsupportedOperationException if collection doesn't have next element
         */

        @Override
        public T getNextElement() {
            if (!hasNextElement()) {
                throw new NoSuchElementException("No more nodes");
            }
            return collection.elements[index++];
        }

    }

}
