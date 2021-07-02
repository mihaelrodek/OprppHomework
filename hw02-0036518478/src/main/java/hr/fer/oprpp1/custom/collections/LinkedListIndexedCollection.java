package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Program that stores elements in an array in a list. List is implemented with
 * node that has "pointer" to previous and next Node and also its value.
 * Duplicates are allowed but null references are not.
 *
 * @author Mihael Rodek
 */
public class LinkedListIndexedCollection implements List {

    private int size;
    private ListNode first;
    private ListNode last;
    private long modificationCount = 0;

    /**
     * Class that represents list node. It has pointers to the previous node, next
     * node and also value of that node.
     */
    private static class ListNode {
        ListNode previous;
        ListNode next;
        Object value;
    }

    /**
     * Default constructor that creates an empty collection. Size is initially set
     * to 0.
     */
    public LinkedListIndexedCollection() {
        size = 0;
        first = last = null;
    }

    /**
     * Constructor that copies elements from other collection into the new one.
     *
     * @param collection Collection which elements we are copying
     * @throws NullPointerException checks if collection is null, if so it throws
     *                              exception with appropriate message
     */

    public LinkedListIndexedCollection(Collection collection) {
        Objects.requireNonNull(collection, "Error. Collection cannot be null.");
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
     * Method that adds the given value into this collection. Object is added at the
     * end of collection.
     *
     * @param value value that needs to be added
     * @throws NullPointerException if object is null exception is thrown with
     *                              appropriate message
     */

    @Override
    public void add(Object value) {
        Objects.requireNonNull(value, "Cannot add null as an element");
        ListNode node = new ListNode();
        node.value = value;
        if (first == null) {
            first = last = node;
        } else {
            node.previous = last;
            last.next = node;
            last = last.next;
        }
        size++;
        modificationCount++;
    }

    /**
     * Returns the object that is stored in linked list at position index. Method
     * has never complexity greater than n/2+1. It first checks in which half of
     * list index is and then searches for index.
     *
     * @param index Position of element to be found
     * @return Value of object on given index
     * @throws IndexOutOfBoundsException if index is lower than 0 or greater than size-1
     */
    @Override
    public Object get(int index) {
        if (index > (size - 1) || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");
        int counter = 0, counterBackwards = size - 1;
        ListNode start;
        if (index <= size / 2) {
            start = first;
            while (start != null) {
                if (counter == index)
                    return start.value;
                counter++;
                start = start.next;
            }
        } else {
            start = last;
            while (start != null) {
                if (counterBackwards == index)
                    return start.value;
                counterBackwards--;
                start = start.previous;
            }
        }
        return start;
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
     * Removes from a collection at specified index from collection.
     *
     * @param index Position of element we want to remove
     * @throws IndexOutOfBoundsException thrown if index is out of bounds(lower than
     *                                   0 or greater than size-1)
     */

    public void remove(int index) {
        if (index > size - 1 || index < 0)
            throw new IndexOutOfBoundsException("Index is out of range");
        ListNode start = first;
        for (int i = 0; i < index && start != null; i++, start = start.next)
            ;

        if (start.previous == null) {
            first = start.next;
        } else {
            start.previous.next = start.next;
            start.previous = null;
        }
        if (start.next == null) {
            last = start.previous;
        } else {
            start.next.previous = start.previous;
            start.next = null;
        }
        start.value = 0;

        modificationCount++;
        size--;
    }

    /**
     * Allocates new array with size equals to the size of this collections, fills
     * it with values and returns the newly created array.
     */

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int count = 0;
        for (ListNode node = first; node != null; node = node.next) {
            array[count] = node.value;
            count++;
        }
        return array;
    }


    /**
     * Method that removes elements from list by "forgetting" about current linked
     * list. It sets null references for first and last node. Also it sets size to 0.
     */
    @Override
    public void clear() {
        first = last = null;
        size = 0;
        modificationCount++;
    }

    @Override
    public int indexOf(Object value) {

        int br = 0;
        ListNode search = first;
        while (br < size) {
            if (search.value.equals(value)) {
                return br;
            }
            search = search.next;
            br++;
        }
        return -1;
    }

    /**
     * Method that inserts the given value at the given position. Method searches
     * the list until the right position is found. Then it adds element into that
     * position and shifts all other elements.
     *
     * @param value    element to be inserted into collection
     * @param position Position where value is inserted
     * @throws IndexOutOfBoundsException if index is lower than 0 or greater than
     *                                   size
     */
    @Override
    public void insert(Object value, int position) {
        if (position > size || position < 0)
            throw new IndexOutOfBoundsException("Position is out of range");

        if (size == 0 || position == size) {
            add(value);
            return;
        }

        ListNode newNode = new ListNode();
        newNode.value = value;
        ListNode tmp = first;

        if (position == 0) {
            if (tmp != null) {
                first = newNode;
                first.next = tmp;
                tmp.previous = first;
            }
            if (last == null) {
                first = last = newNode;
                first.next = tmp;
            }
        } else {
            for (int i = 0; i < position; i++, tmp = tmp.next)
                ;
            newNode.next = tmp;
            newNode.previous = tmp.previous;
            tmp.previous.next = newNode;
            tmp.previous = newNode;
        }
        modificationCount++;
        size++;
    }

    /**
     * Class representation of {@link ElementsGetter} for {@link LinkedListIndexedCollection}
     */

    private static class ElementsGetterLinkedImplementation implements ElementsGetter {

        /**
         * Current node used
         */
        private ListNode node;

        /**
         * Collection to iterate over it
         */
        private final LinkedListIndexedCollection collection;

        /**
         * Saved number of modifications, used to compare if any change was made after ElementsGetter was created
         */
        private final long savedModificationCount;

        /**
         * Default constructor for {@link ElementsGetterLinkedImplementation}
         *
         * @param col collection used for iterating
         * @throws NullPointerException if given collection is null
         */

        public ElementsGetterLinkedImplementation(LinkedListIndexedCollection col) {
            Objects.requireNonNull(col, "Collection mustn't be null");
            this.node = col.first;
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
            return node != null;
        }

        /**
         * @throws @{@link NoSuchElementException} if there is no left elements in collection
         */
        @Override
        public Object getNextElement() {
            if (!hasNextElement()) {
                throw new NoSuchElementException("No more nodes");
            }
            Object value = node.value;
            node = node.next;
            return value;
        }
    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new ElementsGetterLinkedImplementation(this);
    }
}
