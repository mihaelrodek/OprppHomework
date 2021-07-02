package hr.fer.oprpp1.custom.scripting.elems;

import java.util.Objects;

/**
 * Class that represents an element operator.
 *
 * @author Mihael Rodek
 */
public class ElementOperator extends Element {

    /**
     * Operator symbol
     */
    private String symbol;

    /**
     * Default constructor
     *
     * @param symbol symbol of operator
     * @throws NullPointerException if symbol is null
     */
    public ElementOperator(String symbol) {
        Objects.requireNonNull(symbol, "Symbol mustn't be null");
        this.symbol = symbol;
    }

    /**
     * Getter for symbol
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }

}
