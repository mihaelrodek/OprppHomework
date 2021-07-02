package hr.fer.oprpp1.hw02.prob1;

import java.util.Objects;

/**
 * Class that represents a single Token.
 *
 * @author Mihael Rodek
 */

public class Token {
    /**
     * Type of token
     */
    private TokenType type;
    /**
     * Value of token
     */
    private Object value;

    /**
     * Constructor that creates new Token and sets its type and value
     *
     * @param type  type of token, mustn't be null
     * @param value value of token
     * @throws NullPointerException if type is null
     */

    public Token(TokenType type, Object value) {
        Objects.requireNonNull(type);
        this.type = type;
        this.value = value;
    }

    /**
     * Getter for value of {@link Token}
     *
     * @return value
     */

    public Object getValue() {
        return value;
    }

    /**
     * Getter for type of {@link Token}
     *
     * @return token type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Overridden toString method that displays {@link Token} as (TokenType, Token)
     *
     * @return Token displayed in proper format
     */
    @Override
    public String toString() {
        return "(" + getType() + ", " + getValue() + ")";
    }


}
