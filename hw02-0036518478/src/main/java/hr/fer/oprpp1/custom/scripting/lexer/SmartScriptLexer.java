package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;

import java.util.Objects;

public class SmartScriptLexer {
    /**
     * Char array of input text
     */
    private final char[] data;
    /**
     * Current token
     */
    private SmartScriptToken token;
    /**
     * Current index
     */
    private int currentIndex = 0;
    /**
     * Current state
     */
    private SmartScriptLexerState currentState;

    /**
     * Constructor that accepts text which is tokenized doing lexical analysis
     *
     * @param text input text to be tokenized, mustn't be null
     * @throws NullPointerException if text is null
     */
    public SmartScriptLexer(String text) {
        Objects.requireNonNull(text, "Text mustn't be null");
        this.data = text.toCharArray();
        this.currentState = SmartScriptLexerState.TEXT;
        token = null;
    }

    /**
     * Method that generates next token and returns it
     *
     * @return next token tokenized from input data
     * @throws SmartScriptLexerException if token is invalid
     */
    public SmartScriptToken nextToken() {

        if (token != null && token.getType() == SmartScriptTokenType.EOF)
            throw new SmartScriptLexerException("Cannot get next token. No tokens available.");

        if (currentIndex >= data.length)
            return token = new SmartScriptToken(SmartScriptTokenType.EOF, null);

        try {
            switch (currentState) {
                case TAG -> token = tokenAsTag();
                case TEXT -> token = tokenAsText();
                default -> throw new SmartScriptLexerException("Not a valid state for a lexer.");
            }
        } catch (Exception ex) {
            throw new SmartScriptLexerException();
        }

        return token;
    }

    /**
     * Method that parses given text
     *
     * @return token parsed from input
     */

    private SmartScriptToken tokenAsText() {

        StringBuilder sb = new StringBuilder();

        if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
            this.setState(SmartScriptLexerState.TAG);
            currentIndex += 2;
            token = new SmartScriptToken(SmartScriptTokenType.TAG_START, null);
        } else {
            while (currentIndex < data.length) {
                if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
                    break;
                }

                if (data[currentIndex] == '\\') {
                    if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {
                        sb.append(data[currentIndex + 1]);
                        currentIndex += 2;
                        continue;
                    } else
                        throw new SmartScriptLexerException("Invalid escape sequence");
                }
                sb.append(data[currentIndex++]);
            }
            token = new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());
        }
        return token;
    }

    /**
     * Method that sets state of token, it can be in TEXT or TAG state
     *
     * @param state state of token
     */

    public void setState(SmartScriptLexerState state) {
        Objects.requireNonNull(state);
        this.currentState = state;
    }

    /**
     * Method that parses given text
     *
     * @return token parsed from input
     */

    private SmartScriptToken tokenAsTag() {
        skipWhitespaces();

        if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
            this.setState(SmartScriptLexerState.TEXT);
            currentIndex += 2;
            return token = new SmartScriptToken(SmartScriptTokenType.TAG_END, null);
        } else if (data[currentIndex] != '$' || data[currentIndex + 1] != '}') {
            skipWhitespaces();

            if (Character.isLetter(data[currentIndex]))
                token = checkVariable();
            else if (data[currentIndex] == '"')
                token = checkString();
            else if (checkOperators())
                token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex++]);
            else if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-')
                token = checkNumber();
            else if (data[currentIndex] == '@') {
                currentIndex++;
                token = checkVariable();
            } else if (data[currentIndex] == '=') {
                token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, data[currentIndex++]);
            }
        }
        return token;
    }

    /**
     * Method that checks if current index contains an operator
     *
     * @return true if it does, false otherwise
     */

    private boolean checkOperators() {
        return data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
                || data[currentIndex] == '/' || data[currentIndex] == '=';
    }

    /**
     * Method that skips whitespaces
     */
    private void skipWhitespaces() {
        while (currentIndex < data.length) {
            if (data[currentIndex] == ' ' || data[currentIndex] == '\r' || data[currentIndex] == '\n'
                    || data[currentIndex] == '\t')
                currentIndex++;
            else
                break;
        }
    }

    /**
     * Method that checks if input is number and parses it if it is possible
     *
     * @return tokenized number
     * @throws SmartScriptLexerException if number isn't parsable
     */
    private SmartScriptToken checkNumber() {
        StringBuilder sb = new StringBuilder();
        if (data[currentIndex] == '-') {
            sb.append(data[currentIndex++]);
        }

        int counter = 0;
        while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {

            if (data[currentIndex] == '.')
                counter++;

            if (data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
                    || data[currentIndex] == ' ' || data[currentIndex] == '$' && data[currentIndex] == '}')
                break;

            sb.append(data[currentIndex++]);
        }

        if (counter > 1)
            throw new SmartScriptLexerException("Too many dots");

        try {
            if (sb.toString().contains(".")) {
                double num = Double.parseDouble(sb.toString());
                token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, num);
            } else {
                int num = Integer.parseInt(sb.toString());
                token = new SmartScriptToken(SmartScriptTokenType.INTEGER, num);
            }
        } catch (NumberFormatException ex) {
            throw new SmartScriptLexerException();
        }
        return token;
    }

    /**
     * Method that checks for name of variable
     *
     * @return tokenized variable
     */
    private SmartScriptToken checkVariable() {
        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length) {
            if (data[currentIndex] == '\r' || data[currentIndex] == '\n' || data[currentIndex] == '\t'
                    || data[currentIndex] == ' ' || data[currentIndex] == '$' && data[currentIndex + 1] == '}')
                break;
            else if (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
                    || data[currentIndex] == '_')
                sb.append(data[currentIndex++]);
            else
                throw new SmartScriptLexerException();
        }
        char[] str = sb.toString().toCharArray();

        if (!Character.isLetter(str[0])) {
            throw new SmartScriptLexerException();
        } else
            return new SmartScriptToken(SmartScriptTokenType.VARIABLE, new String(sb));
    }

    /**
     * Method that searches for string in input
     *
     * @return tokenized string
     */

    private SmartScriptToken checkString() {
        StringBuilder sb = new StringBuilder();

        currentIndex++;
        while (currentIndex < data.length) {
            if (data[currentIndex] == '"') {
                currentIndex++;
                break;
            }
            checkEscapeWhitespace(sb);
            sb.append(data[currentIndex++]);
        }

        return new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString());
    }

    /**
     * Method that checks if escape was invoked
     *
     * @param sb String Builder element on which check needs to be done
     * @return appended escape sequence if possible
     * @throws SmartScriptLexerException if escape sequence isn't valid
     */

    private StringBuilder checkEscapeWhitespace(StringBuilder sb) {
        if (data[currentIndex] == '\\') {
            if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\r' || data[currentIndex + 1] == '\n'
                    || data[currentIndex + 1] == '\t' || data[currentIndex + 1] == '"') {
                sb.append(data[currentIndex + 1]);
                currentIndex += 2;
            } else
                throw new SmartScriptLexerException();
        }
        return sb;
    }

    /**
     * Getter for state
     *
     * @return current state
     */

    public SmartScriptLexerState getState() {
        return currentState;
    }

    /**
     * Getter for token
     *
     * @return current token
     */
    public SmartScriptToken getToken() {
        return token;
    }

}
