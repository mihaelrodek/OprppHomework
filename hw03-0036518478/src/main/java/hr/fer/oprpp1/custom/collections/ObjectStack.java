package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * Class that represents object stack.
 *
 * @param <T> type of elements stored in this stack
 * @author Mihael Rodek
 */

public class ObjectStack<T> {

    private List<T> adaptee = new ArrayIndexedCollection();

    /**
     * Method that checks if stack is empty.s
     *
     * @return true if stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return adaptee.isEmpty();
    }

    /**
     * Method that returns the size of stack.
     *
     * @return Size of stack
     */

    /**
     * Default constructor
     */
    public ObjectStack() {
        super();
    }

    public int size() {
        return adaptee.size();
    }

    /**
     * Method that pushes give value to stack. If value is null exception is thrown
     *
     * @param value Element to be pushed on stack
     * @throws NullPointerException if given value of element is null
     */

    public void push(T value) {
        Objects.requireNonNull(value, "Value cannot be null");
        adaptee.add(value);
    }

    /**
     * Method that removes last value pushed on stack from stack and returns it.
     *
     * @return value that has been popped from stack
     * @throws EmptyStackException if stack is empty when pop method is called
     */
    public T pop() {
        if (size() == 0)
            throw new EmptyStackException("Stack is empty. Cannot pop element.");
        T peeker = peek();
        adaptee.remove(size() - 1);
        return peeker;
    }

    /**
     * Method that returns last value pushed on stack but doesn't remove it.
     *
     * @return Wanted element, last element pushed on stack
     * @throws EmptyStackException if stack is empty when pop method is calledF
     */

    public T peek() {
        if (size() == 0)
            throw new EmptyStackException("Stack is empty. Cannot peek element.");
        T get = adaptee.get(size() - 1);
        return get;
    }

    /**
     * Removes all elements from stack
     */

    public void clear() {
        adaptee.clear();
    }

}