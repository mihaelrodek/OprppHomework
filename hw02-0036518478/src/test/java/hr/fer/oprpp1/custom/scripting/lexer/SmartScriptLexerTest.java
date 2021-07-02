package hr.fer.oprpp1.custom.scripting.lexer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptLexerTest {

    @Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNotNull(lexer.nextToken());
    }

    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
        ;
    }

    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testGetReturnsLastNext() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        SmartScriptToken token = lexer.nextToken();
        assertEquals(token, lexer.getToken());
        assertEquals(token, lexer.getToken());
    }

    @Test
    public void testRadAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        lexer.nextToken();
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

    }

    @Test
    public void testTextBasic() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    Upregnimo" +
                " kočije blještavih snova");

        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t   " +
                        " Upregnimo kočije blještavih snova"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Test
    public void testTextWithEscaping() {
        SmartScriptLexer lexer = new SmartScriptLexer("\\\\ \\{ \\\\ Nek " +
                "započne pomamni trk");


        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "\\ { \\ Nek " +
                        "započne pomamni trk"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Disabled
    @Test
    public void testTextWithBeginTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("Brže od snježne " +
                "vijavice bile   {$");

        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Brže od " +
                        "snježne vijavice bile   "),
                new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$"),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkSmartScriptTokenStream(lexer, correctData);
    }

    @Test
    public void testInvalidEscapeEnding() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\");  // this is
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

    }

    @Test
    public void testInvalidEscape() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testInvalidBeginTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("{  ");

        lexer.nextToken();
    }

    @Test
    public void testNullState() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
    }

    @Test
    public void testEmptyTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        lexer.setState(SmartScriptLexerState.TAG);

        lexer.nextToken();

    }

    @Test
    public void testGetReturnsLastNextInExtended() {
        SmartScriptLexer lexer = new SmartScriptLexer("FOR i -1 10 1 $}");
        lexer.setState(SmartScriptLexerState.TAG);

        SmartScriptToken token = lexer.nextToken();
        assertEquals(token, lexer.getToken());
        assertEquals(token, lexer.getToken());
    }

    @Test
    public void testNoActualContentInExtended() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    $}");
        lexer.setState(SmartScriptLexerState.TAG);

        assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
    }

    @Disabled
    @Test
    public void testMultipartInput() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text" +
                ".\r\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "\tThis is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" +
                "{$FOR i 0 10 2 $}\r\n" +
                "\tsin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" +
                "{$END$}");

        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "This is sample " +
                        "text.\r\n"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_START, "{$"));
        lexer.setState(SmartScriptLexerState.TAG);
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "FOR"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "i"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 1));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 10));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.INTEGER, 1));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_END, "$}"));
        lexer.setState(SmartScriptLexerState.TEXT);
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "\r\n\tThis is "));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_START, "{$"));
        lexer.setState(SmartScriptLexerState.TAG);
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "="));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "i"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_END, "$}"));
        lexer.setState(SmartScriptLexerState.TEXT);
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "-th time this " +
                        "message is generated.\r\n"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_START, "{$"));
        lexer.setState(SmartScriptLexerState.TAG);
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TEXT, "END"));
        checkSmartScriptToken(lexer.nextToken(), new SmartScriptToken
                (SmartScriptTokenType.TAG_END, "$}"));
    }

    private void checkSmartScriptTokenStream(SmartScriptLexer lexer,
                                             SmartScriptToken[] correctData) {
        for (SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();
            assertEquals(expected.getType(), actual.getType());
            assertEquals(expected.getValue(), actual.getValue());
        }
    }

    private void checkSmartScriptToken(SmartScriptToken actual,
                                       SmartScriptToken expected) {
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getValue(), actual.getValue());
    }
}

