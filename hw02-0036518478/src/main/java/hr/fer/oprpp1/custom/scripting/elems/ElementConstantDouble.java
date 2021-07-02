package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that represents an element with double value.
 *
 * @author Mihael Rodek
 */
public class ElementConstantDouble extends Element {

    /**
     * Double element value
     */
    private double value;

    /**
     * Default constructor.
     *
     * @param value value of this element
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Getter for double
     *
     * @return value
     */

    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return Double.toString(value);
    }

}
