package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;


/**
 * Class that represents an variable element.
 *
 * @author Mihael Rodek
 */

public class ElementVariable extends Element {

    /**
     * Variable name
     */
    private String name;

    /**
     * Constructor that creates function of name
     *
     * @param name variable name
     * @throws NullPointerException if variable is null
     */

    public ElementVariable(String name) {
        Objects.requireNonNull(name, "Variable name mustn't be null");
        this.name = name;
    }

    /**
     * Getter for name
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }
}
