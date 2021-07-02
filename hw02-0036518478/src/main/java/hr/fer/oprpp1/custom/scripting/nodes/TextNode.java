package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

import java.util.Objects;

/**
 * Class that represents textual node.
 */
public class TextNode extends Node {

    /**
     * Text of node
     */
    private String text;

    /**
     * Default constructor
     *
     * @param text text value of node
     */

    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Getter for text
     *
     * @return text
     */

    public String getText() {
        return text;
    }

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
}
