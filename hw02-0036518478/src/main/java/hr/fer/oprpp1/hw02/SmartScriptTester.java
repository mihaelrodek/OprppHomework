package hr.fer.oprpp1.hw02;


import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartScriptTester {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        SmartScriptParser parser = null, parser2 = null;
        String docBody = null;

        if (args.length != 1) {
            System.out.println("Invalid arguments!");
            System.exit(1);
        }


        try {
            docBody = new String(Files.readAllBytes(Paths.get(args[0])),
                    StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Invalid path to the file!");
            System.exit(1);
        }


        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Couldn't parse document!");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed " +
                    "this class!");
            System.exit(1);
        }

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = parseChildren(document);

   /*     try {
            parser2 = new SmartScriptParser(originalDocumentBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Couldn't parse document!");
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed " +
                    "this class!");
            System.exit(1);
        }
        DocumentNode document2 = parser2.getDocumentNode();
        boolean same = document.equals(document2);
        System.out.println("Are documents same? " + same);*/

        System.out.println(originalDocumentBody);
    }


    /**
     * This method is used for creating original for loop from ForLoopNode.
     *
     * @param forLoop ForLoopNode
     * @return Original for loop
     */
    private static String parseForLoopNode(ForLoopNode forLoop) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$ FOR ");
        sb.append(forLoop.getVariable().asText());
        sb.append(" ");
        sb.append(forLoop.getStartExpression().asText());
        sb.append(" ");
        sb.append(forLoop.getEndExpression().asText());
        sb.append(" ");
        if (forLoop.getStepExpression() != null) {
            sb.append(forLoop.getStepExpression().asText());
            sb.append(" ");
        }
        sb.append("$}");

        sb.append(parseChildren(forLoop));

        sb.append("{$ END $}");

        return sb.toString();
    }

    /**
     * This method is used for creating original children of given Node.
     *
     * @param node Parent node
     * @return Original string representation of children
     */
    public static String parseChildren(Node node) {
        StringBuilder sb = new StringBuilder();

        if (!(node instanceof DocumentNode || node instanceof ForLoopNode)) {
            System.out.println("Document is not parsable");
            System.exit(1);
        }

        for (int i = 0; i < node.numberOfChildren(); i++) {
            Node child = node.getChild(i);
            if (child instanceof ForLoopNode) {
                sb.append(parseForLoopNode((ForLoopNode) child));
            } else if (child instanceof TextNode) {
                sb.append(((TextNode) child).getText());
            } else if (child instanceof EchoNode) {
                sb.append(parseEchoNode((EchoNode) child));
            } else {
                System.out.println("Document is not parsable!");
                System.exit(1);
            }

        }

        return sb.toString();
    }

    /**
     * This method is used for creating original echo from EchoNode.
     *
     * @param echo EchoNode
     * @return Original String representation of echo Node
     */
    private static String parseEchoNode(EchoNode echo) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$= ");

        Element[] echoNodeElements = echo.getElements();

        for (Element echoNodeElement : echoNodeElements) {
            sb.append(echoNodeElement.asText());
            sb.append(" ");
        }

        sb.append("$}");
        return sb.toString();
    }

}
