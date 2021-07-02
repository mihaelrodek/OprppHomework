package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class that represents an element with string value.
 *
 * @author Mihael Rodek
 */
public class ElementString extends Element {

    /**
     * String value
     */
    private String value;

    /**
     * Default constructor
     *
     * @param value string value
     * @throws NullPointerException if value is null
     */

    public ElementString(String value) {
        Objects.requireNonNull(value, "String mustn't be null");
        this.value = value;
    }

    /**
     * Getter for value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return value;
    }

}
