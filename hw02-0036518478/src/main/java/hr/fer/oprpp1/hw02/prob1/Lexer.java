package hr.fer.oprpp1.hw02.prob1;

import java.util.Objects;

/**
 * Class used to perform lexical analysis on input data by tokenizing it in tokens.
 *
 * @author Mihael Rodek
 */
public class Lexer {

    /**
     * Char array of input text
     */
    private final char[] data;

    /**
     * Current token
     */
    private Token token;

    /**
     * Current index
     */
    private int currentIndex = 0;

    /**
     * Current state
     */
    LexerState currentState = LexerState.BASIC;

    /**
     * Char that represents change of state
     */
    private static final Character changeState = '#';

    /**
     * Constructor that accepts text which is then tokenized
     *
     * @param text input text to be tokenized, mustn't be null
     * @throws NullPointerException if text is null
     */

    public Lexer(String text) {
        Objects.requireNonNull(text, "Text mustn't be null");
        data = text.toCharArray();
        token = null;
    }

    /**
     * Setter for state
     *
     * @param state state to be set to, mustn't be null
     * @throws NullPointerException if state is null
     */

    public void setState(LexerState state) {
        Objects.requireNonNull(state);
        this.currentState = state;
    }

    /**
     * Getter for token
     *
     * @return current token
     * @throws LexerException if token is null
     */
    public Token getToken() {
        if (token.equals(null)) {
            throw new LexerException("Token is null.");
        } else
            return token;
    }

    /**
     * Method that generates next token. Generated token depends on state that is set.
     *
     * @return generated token
     * @throws LexerException if there are no tokens available or any similar error occurred
     */

    public Token nextToken() {
        if (token != null && token.getType() == TokenType.EOF)
            throw new LexerException("Cannot get next token. No tokens available.");

        skipWhitespacesAndTabulators();

        if (currentIndex >= data.length)
            return token = new Token(TokenType.EOF, null);


        try {
            switch (currentState) {
                case BASIC -> basicState();
                case EXTENDED -> extendedState();
                default -> throw new LexerException("Not a valid state for a lexer.");
            }
        } catch (NumberFormatException ex) {
            throw new LexerException("Exception thrown due to number format");
        }

        return token;
    }

    /**
     * Method that skips all ' ', '\r', '\n' and '\t'
     */

    private void skipWhitespacesAndTabulators() {
        while (currentIndex < data.length) {
            if (data[currentIndex] == ' ' || data[currentIndex] == '\r' || data[currentIndex] == '\n'
                    || data[currentIndex] == '\t')
                currentIndex++;
            else
                break;
        }
    }

    /**
     * Method used for dealing with basic state of lexer. It can tokenize numbers of type long, words and symbols.
     * When tokenizing a word multiple escaping options are available. If \ is followed by another \, it counts as
     * escaping \ and it is added as word. If \ is followed by a digit, it counts as word instead of number.
     *
     * @throws LexerException if Lexer is not in acceptable format or other error occurred
     */

    private void basicState() {

        if (Character.isDigit(data[currentIndex])) {

            int start = currentIndex;
            for (; (currentIndex < data.length) && Character.isDigit(data[currentIndex]); currentIndex++) {
            }
            String numberString = new String(data);
            long number = Long.parseLong(numberString.substring(start, currentIndex));
            token = new Token(TokenType.NUMBER, number);

        } else if (data[currentIndex] == '\\' || Character.isAlphabetic(data[currentIndex])) {

            String string = "";
            while (currentIndex < data.length) {
                if (data[currentIndex] == '\\') {
                    currentIndex++;
                    if (currentIndex >= data.length) {
                        throw new LexerException("Index is out of bounds.");
                    }
                    if (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])) {
                        string += data[currentIndex++];
                    } else
                        throw new LexerException("Cannot escape character '" + data[currentIndex] + "'.");

                } else if (Character.isAlphabetic(data[currentIndex]))
                    string += data[currentIndex++];
                else
                    break;
            }

            token = new Token(TokenType.WORD, string);
        } else
            token = new Token(TokenType.SYMBOL, data[currentIndex++]);

    }

    /**
     * Method used for dealing with extended state of lexer. Method is parsing input as a whole word until
     * the next occurrence of '#'.
     */

    private void extendedState() {

        if (data[currentIndex] == changeState) {
            token = new Token(TokenType.SYMBOL, data[currentIndex++]);
            return;
        }

        int brp = 0;
        for (; currentIndex < data.length && data[currentIndex] != changeState
                && !Character.isWhitespace(data[currentIndex]); currentIndex++, brp++)
            ;

        if (brp == 0)
            throw new LexerException("Could not extract next token.");

        String string = new String(data);
        String word = string.substring(currentIndex - brp, currentIndex);
        token = new Token(TokenType.WORD, word);

    }

}

