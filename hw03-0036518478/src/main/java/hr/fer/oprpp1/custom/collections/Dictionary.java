package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Class that represents dictionary that stores key-value pairs as map. It is implemented as an adapter around {@link ArrayIndexedCollection}
 *
 * @param <K> key type
 * @param <V> value type
 * @author Mihael Rodek
 */
public class Dictionary<K, V> {

    /**
     * List that stores key-value pairs
     */

    private List<KeyValue<K, V>> list;

    /**
     * Default constructor for {@link Dictionary}, initializes list as {@link ArrayIndexedCollection}
     */
    public Dictionary() {
        this.list = new ArrayIndexedCollection<>();
    }

    /**
     * Class that represents key-value pair
     *
     * @param <K> key type
     * @param <V> value type
     */
    private static class KeyValue<K, V> {
        /**
         * Key of pair, cannot be null
         */
        private final K key;

        /**
         * Value of pair
         */
        private V value;

        /**
         * Default constructor for key-value pair
         *
         * @param key   key of pair, mustn't be null
         * @param value value of pair
         */
        private KeyValue(K key, V value) {
            Objects.requireNonNull(key, "Key musn't be null");
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof KeyValue)) {
                return false;
            }
            KeyValue<?, ?> other = (KeyValue<?, ?>) obj;
            return Objects.equals(key, other.key) && Objects.equals(value, other.value);
        }
    }

    /**
     * Method that checks if map is empty
     *
     * @return true if there are no pairs, otherwise false
     */

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Method that returns size of map - number of all key-value pairs
     *
     * @return size of map
     */
    public int size() {
        return list.size();
    }

    /**
     * Method that removes all key-value pairs from map
     */
    public void clear() {
        list.clear();
    }

    /**
     * Method that adds key-value pair into this map-dictionary. If there is pair that already contains given key, method overrides it, otherwise it adds par into the map
     *
     * @param key   key of pair to be added
     * @param value value of pair to be added
     * @return null if map doesn't contain given key, otherwise returns value of overriden key
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "Key mustn't be null");

        V val = get(key);
        if (val == null) {
            list.add(new KeyValue(key, value));
            return null;
        } else {
            list.remove(new KeyValue(key, val));
            list.add(new KeyValue(key, value));
            return val;
        }
    }

    /**
     * Method that gets value of key-pair for given key
     *
     * @param key key of pair to be searched for
     * @return null if map doesn't contain given key, otherwise returns value of found pair
     */

    public V get(Object key) {

        ElementsGetter<KeyValue<K, V>> getter = list.createElementsGetter();
        while (getter.hasNextElement()) {
            KeyValue<K, V> element = getter.getNextElement();
            if (key.equals(element.key)) {
                return element.value;
            }
        }
        return null;
    }

    /**
     * Method that removes pair from map if given key exists
     *
     * @param key key of pair to be removed
     * @return null if map doesn't contain given key, otherwise returns value of removed pair
     */

    public V remove(K key) {
        V val = get(key);
        if (val == null) {
            return null;
        } else {
            list.remove(new KeyValue(key, val));
            return val;
        }
    }


}
