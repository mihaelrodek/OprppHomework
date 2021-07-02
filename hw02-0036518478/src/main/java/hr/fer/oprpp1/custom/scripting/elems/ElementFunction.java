package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class that represents an function element.
 *
 * @author Mihael Rodek
 */

public class ElementFunction extends Element {

    /**
     * Function name
     */
    private String name;

    /**
     * Constructor that creates function of name
     *
     * @param name function name
     * @throws NullPointerException if name is null
     */
    public ElementFunction(String name) {
        Objects.requireNonNull(name, "Name mustn't be null");
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
        return "@" + name;
    }
}
