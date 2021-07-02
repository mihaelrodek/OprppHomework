package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Class that represents main Node model.
 *
 * @author Mihael Rodek
 */
public abstract class Node {



    /**
     * Collection of childs
     */
    ArrayIndexedCollection collection;

    /**
     * Adds given child to an internally managed collection of children
     *
     * @param child child to be added in collection
     */
    public abstract void addChildNode(Node child);

    /**
     * Method that returns a number of direct children
     *
     * @return number of children
     */
    public abstract int numberOfChildren();

    /**
     * Method that returns selected child at given index
     *
     * @param index index of child
     * @return selected child
     */
    public abstract Node getChild(int index);

}
