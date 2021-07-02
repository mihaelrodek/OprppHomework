package hr.fer.oprpp1.custom.collections;

/**
 * Class used to represent collection of objects.
 *
 * @author Mihael Rodek
 *
 */
public class Collection {

	/**
	 * Default constructor
	 */

	protected Collection() {
	}

	/**
	 * Checks if collection is empty
	 *
	 * @return Corresponding result
	 */

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the size of collection
	 *
	 * @return Set here to return 0
	 */

	public int size() {
		return 0;
	}

	/**
	 * Adds the value into the collection
	 *
	 * @param value Object that needs to be added
	 */
	public void add(Object value) {
	}

	/**
	 * Checks if collection contains a given value
	 *
	 * @param value Value to be checked
	 * @return True if collection contains given value, otherwise returns false
	 */

	boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes given value from collection
	 *
	 * @param value Value to be removed
	 * @return true if value was removed, otherwise false
	 */

	boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that allocates new array with size equals to the size of this
	 * collections, fills it with collection content and returns the array. Here
	 * implemented to throw {@link UnsupportedOperationException}
	 *
	 * @return
	 */

	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process(.) for each element of this collection. Here
	 * implemented as an empty method
	 *
	 * @param processor Processor on which action is performed for each element of collection
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Adds into the current collection all elements from the given collection using
	 * local class Processor.
	 *
	 * @param other Collection which elements are added into the current one
	 */

	public void addAll(Collection other) {
		Processor proc = new Processor() {
			@Override
			public void process(Object value) {
				add(value);
			}

		};
		other.forEach(proc);
	}

	/**
	 * Removes all elements from this collection.Here implemented as an empty method
	 */

	public void clear() {
	}

}
