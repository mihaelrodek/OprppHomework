package hr.fer.oprpp1.hw02.prob1;

/**
 * Enum that represents type of token
 *
 * @author Mihael Rodek
 */
public enum TokenType {
    /**
     * End Of File token
     */
    EOF,
    /**
     * Token that represents a word, token is considered a word if it contains sequence of one or more signs
     */
    WORD,
    /**
     * Token that represents a number, token is considered a number if it contains sequence of one or more digits that is parsable as type {@link Long}
     */
    NUMBER,
    /**
     * Token that represents a symbol, token is considered a symbol if it is not any other @{@link TokenType} or whitespace ('\r','\n','\t',' ')
     */
    SYMBOL
}
