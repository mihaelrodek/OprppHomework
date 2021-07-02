package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptToken;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Parses an input document and creates document model tree for it.
 *
 * @author Mihael Rodek
 */
public class SmartScriptParser {

    /**
     * Lexer used to generate tokens from the input document
     */
    private SmartScriptLexer lexer;

    /**
     * Root of the tree.
     */
    private DocumentNode root;

    /**
     * Stack used when parsing nodes with children
     */
    ObjectStack stack;

    /**
     * Constructor that creates a new parser and begins parsing the document.
     *
     * @param document document that is parsed
     * @throws SmartScriptParserException if an error occurs while parsing the document
     */
    public SmartScriptParser(String document) {
        Objects.requireNonNull(document);

        stack = new ObjectStack();
        lexer = new SmartScriptLexer(document);
        root = new DocumentNode();

        try {
            parse();
        } catch (Exception ex) {
            throw new SmartScriptParserException(ex);
        }
    }

    /**
     * Method used for parsing given text into nodes
     *
     * @throws SmartScriptParserException if an error occurs while parsing the document
     */
    private void parse() {

        stack.push(root);

        while (true) {
            SmartScriptToken token = lexer.nextToken();

            if (token.getType() == SmartScriptTokenType.EOF) {
                break;
            } else if (token.getType() == SmartScriptTokenType.TEXT) {
                lexer.setState(SmartScriptLexerState.TEXT);
                TextNode textNode = parseText();

                Node stackNode = (Node) stack.peek();
                stackNode.addChildNode(textNode);

            } else if (token.getType() == SmartScriptTokenType.TAG_START) {

                parseTag(token);
                lexer.setState(SmartScriptLexerState.TEXT);
            } else {
                throw new SmartScriptParserException();
            }
        }


    }

    private void parseTag(SmartScriptToken token) {
        lexer.setState(SmartScriptLexerState.TAG);

        token = lexer.nextToken();
        if (token.getType() == SmartScriptTokenType.TAG_NAME || token.getType() == SmartScriptTokenType.VARIABLE
                || token.getType() == SmartScriptTokenType.OPERATOR) {

            String tokenValue = token.getValue().toString();

            if ("for".equalsIgnoreCase(tokenValue)) {
                parseForTag();

            } else if ("=".equalsIgnoreCase(tokenValue)) {
                parseEchoTag();

            } else if ("end".equalsIgnoreCase(tokenValue)) {
                token = lexer.nextToken();

                if (token.getType() != SmartScriptTokenType.TAG_END) {
                    throw new SmartScriptParserException();
                }
                stack.pop();
            }
        } else {
            throw new SmartScriptParserException();
        }
    }

    /**
     * Method that parses text
     *
     * @return parsed TextNode
     */
    private TextNode parseText() {
        SmartScriptToken token = lexer.getToken();
        String text = token.getValue().toString();

        return new TextNode(text);
    }

    /**
     * Method for parsing the for-loop tag by its parts
     *
     * @return node parsed as {@link ForLoopNode}
     * @throws SmartScriptParserException if an error occurs while parsing into {@link ForLoopNode}
     */
    private ForLoopNode parseForTag() {
        SmartScriptToken token = lexer.nextToken();
        Element startExpression, endExpression, stepExpression;

        if (token.getType() != SmartScriptTokenType.VARIABLE)
            throw new SmartScriptParserException();

        ElementVariable variableName = new ElementVariable(token.getValue().toString());

        token = lexer.nextToken();
        startExpression = checkTokenType(token);
        token = lexer.nextToken();
        endExpression = checkTokenType(token);
        token = lexer.nextToken();
        stepExpression = null;

        if (token.getType() == SmartScriptTokenType.TAG_END)
            return new ForLoopNode(variableName, startExpression, endExpression, stepExpression);

        stepExpression = checkTokenType(token);

        token = lexer.nextToken();

        if (token.getType() != SmartScriptTokenType.TAG_END)
            throw new SmartScriptParserException("Too many arguments in for loop");


        ForLoopNode help = new ForLoopNode(variableName, startExpression, endExpression, stepExpression);
        ((Node) stack.peek()).addChildNode(help);
        stack.push(help);
        return help;
    }

    /**
     * Method that checks type of a for-loop token and returns element for it, type can be variable,
     * integer or string.
     *
     * @param token token to be checked
     * @return Element that matches passed token
     * @throws SmartScriptParserException if an error occurs while getting token type
     */
    private Element checkTokenType(SmartScriptToken token) {
        Element el;
        switch (token.getType()) {
            case VARIABLE -> el = new ElementVariable(token.getValue().toString());
            case INTEGER -> el = new ElementConstantInteger((int) token.getValue());
            case STRING -> el = new ElementString(token.getValue().toString());
            default -> throw new SmartScriptParserException();
        }
        return el;
    }

    /**
     * Method that parses the echo tag
     *
     * @return node parsed as {@link EchoNode}
     * @throws SmartScriptParserException if an error occurs while parsing into {@link EchoNode}
     */
    private EchoNode parseEchoTag() {
        SmartScriptToken token = lexer.nextToken();

        ArrayIndexedCollection arrayElements = new ArrayIndexedCollection();

        while (true) {
            if (token.getType() == SmartScriptTokenType.TAG_END) {
                if (arrayElements.size() == 0)
                    throw new SmartScriptParserException("Echo cannot be empty");
                else
                    break;
            }
            switch (token.getType()) {
                case DOUBLE -> arrayElements.add(new ElementConstantDouble((double) token.getValue()));
                case INTEGER -> arrayElements.add(new ElementConstantInteger((int) token.getValue()));
                case FUNCTION -> arrayElements.add(new ElementFunction(token.getValue().toString()));
                case OPERATOR -> arrayElements.add(new ElementOperator(token.getValue().toString()));
                case STRING -> arrayElements.add(new ElementString(token.getValue().toString()));
                case VARIABLE -> arrayElements.add(new ElementVariable(token.getValue().toString()));
                default -> throw new SmartScriptParserException();
            }
            token = lexer.nextToken();
        }
        Element[] echoElements = Arrays.copyOf(arrayElements.toArray(), arrayElements
                .size(), Element[].class);

        EchoNode help = new EchoNode(echoElements);
        Node stackNode = (Node) stack.peek();
        stackNode.addChildNode(help);
        return help;
    }

    /**
     * Getter for root node
     *
     * @return root node of Document
     */
    public DocumentNode getDocumentNode() {
        return root;
    }
}