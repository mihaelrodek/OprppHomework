package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents an element with integer value.
 *
 * @author Mihael Rodek
 */
public class ElementConstantInteger extends Element {

    /**
     * Integer element value
     */
    private int value;

    /**
     * Default constructor.
     *
     * @param value value of this element
     */

    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Getter for value
     *
     * @return value
     */

    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Integer.toString(value);
    }

}
