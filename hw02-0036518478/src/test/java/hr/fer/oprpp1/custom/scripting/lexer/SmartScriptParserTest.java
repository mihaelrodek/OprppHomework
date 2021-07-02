package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static hr.fer.oprpp1.hw02.SmartScriptTester.parseChildren;
import static org.junit.jupiter.api.Assertions.*;

class SmartScriptParserTest {
    @Disabled
    @Test
    public void nullTextTest() {
        checkIfGood(null);
    }

    @Disabled
    @Test
    public void primjer7() {
        String primjer7 = loader("primjer7.txt");
        assertTrue(checkIfGood(primjer7));
    }

    @Test
    public void primjer6() {
        String primjer6 = loader("primjer6.txt");
        checkIfGood(primjer6);
    }

    @Disabled
    @Test
    public void primjer5() {
        assertThrows(SmartScriptLexerException.class, () -> loader("primjer5.txt"));
    }

    @Disabled
    @Test
    public void primjer4() {
        String primjer4 = loader("primjer4.txt");
        checkIfGood(primjer4);
    }

    @Disabled
    @Test
    public void primjer3() {
        String primjer3 = loader("primjer3.txt");
        assertTrue(checkIfGood(primjer3));
    }

    @Test
    public void primjer2() {
        String primjer2 = loader("primjer2.txt");
        assertFalse(checkIfGood(primjer2));
    }

    @Test
    public void primjer1() {
        String pr1 = loader("primjer1.txt");
        assertTrue(checkIfGood(pr1));
    }

    public boolean checkIfGood(String text) {
        SmartScriptParser parser = new SmartScriptParser(text);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        String originalDocumentBody2 = createOriginalDocumentBody(document2);

        return originalDocumentBody.equals(originalDocumentBody2);
    }

    public static String createOriginalDocumentBody(DocumentNode document) {
        return parseChildren(document);
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (InputStream is =
                     this.getClass().getClassLoader().getResourceAsStream
                             (filename)) {
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                if (read < 1) break;
                bos.write(buffer, 0, read);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return null;
        }
    }

}
