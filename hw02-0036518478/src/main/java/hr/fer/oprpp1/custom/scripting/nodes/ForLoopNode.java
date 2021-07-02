package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class that represents for loop. For loop contains of variable, startExpression, endExpression
 * and optionally stepExpression.
 *
 * @author Mihael Rodek
 */
public class ForLoopNode extends Node {

    /**
     * Variable in for loop
     */
    ElementVariable variable;
    /**
     * Start of expression in for loop
     */
    Element startExpression;
    /**
     * End of expression in for loop
     */
    Element endExpression;
    /**
     * Step for expression in for loop
     */
    Element stepExpression;

    /**
     * Default constructor that delegates arguments to super constructor
     *
     * @param variable        variable in for loop
     * @param startExpression start of expression in for loop
     * @param endExpression   end of expression in for loop
     */

    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
        this(variable, startExpression, endExpression, null);
    }

    /**
     * Constructor that constructs for loop
     *
     * @param variable        variable in for loop
     * @param startExpression start of expression in for loop
     * @param endExpression   end of expression in for loop
     * @param stepExpression  step for expression in for loop
     * @throws NullPointerException if variable or startExpression or endExpression are null
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
                       Element stepExpression) {
        Objects.requireNonNull(variable);
        Objects.requireNonNull(startExpression);
        Objects.requireNonNull(endExpression);
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Getter for variable
     *
     * @return
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Getter for startExpression
     *
     * @return startExpression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter for endExpression
     *
     * @return endExpression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter for stepExpression
     *
     * @return stepExpression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

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
