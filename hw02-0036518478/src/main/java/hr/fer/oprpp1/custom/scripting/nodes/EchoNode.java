package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.scripting.elems.Element;


/**
 * Class that represents echo node. This node cannot have any children.
 *
 * @author Mihael Rodek
 */
public class EchoNode extends Node {

    /**
     * A collection of elements
     */

    private Element[] elements;

    /**
     * Default constructor
     *
     * @param elements
     */
    public EchoNode(Element[] elements) {
        Objects.requireNonNull(elements, "Elements cannot be null");
        this.elements = elements;
    }
/*

    @Override
    public String toString() {
        return "EchoNode [elements=" + Arrays.toString(elements) + "]";
    }*/

    @Override
    public void addChildNode(Node child) {
        throw new RuntimeException("EchoNode cannot have child");
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

    @Override
    public Node getChild(int index) {
        throw new RuntimeException("EchoNode doesn't have child");
    }

    /**
     * Getter for elements
     *
     * @return elements
     */
    public Element[] getElements() {
        return elements;
    }
}
