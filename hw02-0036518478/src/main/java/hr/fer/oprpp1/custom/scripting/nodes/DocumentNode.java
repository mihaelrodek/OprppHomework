package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import java.util.Objects;

/**
 * Class that represents document node.
 */

public class DocumentNode extends Node {

    /**
     * @throws NullPointerException if child is null
     */
    @Override
    public void addChildNode(Node child) {
        Objects.requireNonNull(child, "Child cannot be null");
        if (collection == null)
            collection = new ArrayIndexedCollection();
        collection.add(child);
    }

    @Override
    public int numberOfChildren() {
        return collection == null ? 0 : collection.size();
    }

    /**
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    @Override
    public Node getChild(int index) {
        if (index < 0 || index > collection.size())
            throw new IndexOutOfBoundsException("Index is out of bounds");
        return (Node) collection.get(index);
    }

}
