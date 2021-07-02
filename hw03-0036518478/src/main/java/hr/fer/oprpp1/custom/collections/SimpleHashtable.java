package hr.fer.oprpp1.custom.collections;

import java.util.*;

/**
 * Class that represents implementation of hashable map that contains key-value pairs
 *
 * @param <K> key type stored in hash table
 * @param <V> value type stored in hash table
 * @author Mihael Rodek
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Default capacity if capacity is not provided
     */
    private static final int CAPACITY = 16;
    /**
     * Factor for checking overloading of map
     */
    private static final double FACTOR = 0.75;
    /**
     * Number of pairs stored in table
     */
    private int size;
    /**
     * Array that contains slots for hash values of pairs
     */
    private TableEntry<K, V>[] table;
    /**
     * Counter for number of modifications done on hash table
     */
    private int modificationCount = 0;


    /**
     * Default constructor for {@link SimpleHashtable}, sets capacity to default CAPACITY
     */

    public SimpleHashtable() {
        this(CAPACITY);
    }

    /**
     * Constructor that sets capacity of hash table, if initialCapacity param is not power of 2,
     * capacity is set to first integer that is equal to power of 2
     *
     * @param initialCapacity capacity to be set
     * @throws IllegalArgumentException if initialCapacity is lower than 1
     */

    public SimpleHashtable(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity mustn't be lower than 1");
        }
        int counter = 0;
        while (initialCapacity > 1) {
            initialCapacity /= 2;
            counter++;
        }
        int capacity = (int) Math.pow(2, counter);
        this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
    }

    /**
     * Method that removes all elements from hash table by setting null references.
     * This method doesn't change capacity.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
        modificationCount++;
    }

    /**
     * Method that returns number of pairs stored in hash table
     *
     * @return number of key-value pairs
     */

    public int size() {
        return this.size;
    }

    /**
     * Method that checks if hash table is empty
     *
     * @return true is empty, false otherwise
     */

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Method that calculates slot in which pair is inserted
     *
     * @param object key of pair used to calculate the slot
     * @return slot position in hash table
     */
    private int calculateSlot(Object object) {
        return Math.abs(Objects.hashCode(object)) % table.length;
    }

    /**
     * Method that searches for value of key-value pair for given key
     *
     * @param key key of pair which value we are searching for
     * @return null if value is not found or key is null, otherwise returns value of pair
     */
    public V get(Object key) {

        int slot = calculateSlot(key);
        V value = null;
        for (TableEntry<K, V> entry = table[slot]; entry != null; entry = entry.next) {
            if (key.equals(entry.key)) {
                value = entry.value;
                break;
            }
        }
        return value;
    }

    /**
     * Checks if hash table contains given key
     *
     * @param key key to be checked
     * @return true if it contains key, otherwise false
     */

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Checks if hash table contains given value
     *
     * @param value value to be checked
     * @return if it contains key, otherwise false
     */

    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table)
            if (entry != null && Objects.equals(value, entry.value))
                return true;
        return false;
    }

    /**
     * Adds key-value pair into hash table. If key already exists, value is overriden and changed to new value,
     * otherwise a new pair is created and added
     *
     * @param key   key of pair to be added, mustn't be null
     * @param value value of pair to be added
     * @return null if hash table doesn't contain key-value pair with given key, otherwise returns overriden value
     * @throws NullPointerException if key is null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Key mustn't be null");
        checkOverpopulation();

        int slot = calculateSlot(key);
        V exValue;
        TableEntry<K, V> entry = table[slot];
        if (entry == null) {
            table[slot] = new TableEntry<>(key, value, null);
        } else {
            do {
                if (Objects.equals(key, entry.key)) {
                    exValue = entry.value;
                    entry.setValue(value);
                    return exValue;
                } else if (entry.next == null) {
                    break;
                }
                entry = entry.next;
            } while (true);
            entry.next = new TableEntry<>(key, value, null);
        }
        size++;
        modificationCount++;
        return null;
    }


    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Checks if table is overpopulated. If it is, method doubles its capacity
     */
    private void checkOverpopulation() {
        if (!(size / table.length >= FACTOR)) {
            return;
        }

        TableEntry<K, V>[] temp = table;
        int newCapacity = table.length * 2;
        table = (TableEntry<K, V>[]) new TableEntry[newCapacity];

        for (int i = 0; i < temp.length; i++) {
            TableEntry<K, V> singleEntry = temp[i];
            if (singleEntry != null) {
                temp[i] = null;

                while (singleEntry != null) {
                    TableEntry<K, V> next = singleEntry.next;
                    singleEntry.next = table[calculateSlot(singleEntry.key)];
                    table[calculateSlot(singleEntry.key)] = singleEntry;
                    singleEntry = next;
                }
            }

        }
        modificationCount++;
    }

    /**
     * Method that converts table in array of references
     *
     * @return array of references to key-value pairs
     */

    public TableEntry<K, V>[] toArray() {
        TableEntry<K, V>[] tmp = (TableEntry<K, V>[]) new TableEntry[size];
        int counter = 0;
        for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> singleEntry = table[i];
            if (singleEntry != null) {
                table[i] = null;
                while (singleEntry != null) {
                    tmp[counter++] = singleEntry;
                    singleEntry = singleEntry.next;
                }
            }

        }
        return tmp;
    }

    /**
     * Generates key-value pairs of hash tables as string using this template: "[key1=value1, key2=value2, ...]"
     *
     * @return converted string
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");
        int counter = 0;
        if (size() == 0) sb.append("]");
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (counter + 1 == size())
                    sb.append(entry.toString() + "]");
                else
                    sb.append(entry.toString() + ", ");
                counter++;
                entry = entry.next;
            }
        }
        return sb.toString();
    }


    /**
     * Removes key-value pair from hash table if it exists
     *
     * @param key key of key-value pair used to check if pair exists
     * @return null if par doesn't exist or key is null, otherwise returns value of removed pair
     */
    public V remove(Object key) {

        if (key == null) {
            return null;
        }

        V value = get(key);
        int slot = calculateSlot(key);
        TableEntry<K, V> entry = table[slot];

        if (value != null) {
            if (entry.key.equals(key)) {
                table[slot] = entry.next;
            } else {
                for (; entry != null; entry = entry.next) {
                    if (entry.next.key.equals(key)) {
                        entry.next = entry.next.next;
                        break;
                    }
                }
            }
            size--;
            modificationCount++;
        }

        return value;
    }

    /**
     * Class that represents a single key-value pair
     *
     * @param <K> key type
     * @param <V> value type
     */
    public static class TableEntry<K, V> {
        /**
         * Key of pair, cannot be null
         */
        private final K key;
        /**
         * Value of pair
         */
        private V value;
        /**
         * Reference to the next entry in the hash table
         */
        private TableEntry<K, V> next;

        /**
         * Default constructor for key-value pair
         *
         * @param key   key of pair, mustn't be null
         * @param value value of pair
         * @param next  next entry in same slot of table
         * @throws NullPointerException if key is null
         */

        public TableEntry(K key, V value, TableEntry<K, V> next) {
            Objects.requireNonNull(key, "Key mustn't be null");
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Getter for key
         *
         * @return key of key-value pair
         */

        public K getKey() {
            return key;
        }

        /**
         * Getter for value
         *
         * @return value of key-value pair
         */

        public V getValue() {
            return value;
        }

        /**
         * Setter for value
         *
         * @param value value of key-value pair to be set
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Generates key-value pairs of hash tables as string using this template: "key=value"
         *
         * @return converted string
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> that = (TableEntry<?, ?>) o;
            return key.equals(that.key) &&
                    Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    /**
     * Class that represents key-value pairs in order, from first to last table slot
     */

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Saved count of modifcatons in hash table, used to compare if any modification has been done
         */
        private long savedModificationCount;

        /**
         * Index of current slot
         */
        private int currentIndex = 0;
        /**
         * Current entry
         */
        private TableEntry<K, V> entry;
        /**
         * Next entry
         */
        private TableEntry<K, V> entryNext;

        /**
         * Default constructor
         */
        public IteratorImpl() {
            this.savedModificationCount = modificationCount;
            for (; currentIndex < table.length && table[currentIndex] == null;
                 currentIndex++)
                ;
            checkIndex();
        }


        /**
         * Checks if there is next element in table
         *
         * @return true if there is next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            checkModification();
            return entryNext != null;
        }

        /**
         * Method that gets next key-value pair
         *
         * @throws NoSuchElementException if there is no more key-value pairs in table
         */

        @Override
        public SimpleHashtable.TableEntry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException("No more pairs");

            entry = entryNext;
            if (entryNext.next != null) {
                entryNext = entryNext.next;
            } else {
                currentIndex++;
                for (; currentIndex < table.length && table[currentIndex] == null;
                     currentIndex++)
                    ;
                checkIndex();
            }
            return entry;
        }

        /**
         * @throws IllegalStateException if there is no element to remove
         */
        @Override
        public void remove() {
            checkModification();
            if (entry == null) {
                throw new IllegalStateException("Cannot remove element");
            } else {
                SimpleHashtable.this.remove(entry.key);
                savedModificationCount = modificationCount;
            }
        }

        /**
         * Method that checks if there was any modification that wasn't allowed
         *
         * @throws ConcurrentModificationException if table was changed
         */

        private void checkModification() {
            if (savedModificationCount != modificationCount)
                throw new ConcurrentModificationException("Hash table was changed.");
        }

        /**
         * Checks if index is less than length or not
         */

        private void checkIndex() {
            if (currentIndex < table.length)
                entryNext = table[currentIndex];
            else
                entryNext = null;
        }
    }
}
