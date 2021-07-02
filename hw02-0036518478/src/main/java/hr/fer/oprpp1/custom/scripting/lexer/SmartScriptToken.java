package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.Objects;

/**
 * Class that represents a single SmartScriptToken.
 *
 * @author Mihael Rodek
 */
public class SmartScriptToken {
    /**
     * Type of token
     */
    private SmartScriptTokenType type;
    /**
     * Value of token
     */
    private Object value;

    /**
     * Constructor that creates new SmartScriptToken and sets its type and value
     *
     * @param type  type of token, mustn't be null
     * @param value value of token
     * @throws NullPointerException if type is null
     */

    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        Objects.requireNonNull(type);
        this.type = type;
        this.value = value;
    }

    /**
     * Overridden toString method that displays {@link SmartScriptToken} as (TokenType, Token)
     *
     * @return Token displayed in proper format
     */
    @Override
    public String toString() {
        return "(" + getType() + ", " + getValue() + ")";
    }

    /**
     * Getter for value of {@link SmartScriptToken}
     *
     * @return value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Getter for type of {@link SmartScriptToken}
     *
     * @return token type
     */
    public SmartScriptTokenType getType() {
        return type;
    }
}
