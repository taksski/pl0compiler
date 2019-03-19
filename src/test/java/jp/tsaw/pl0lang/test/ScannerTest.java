package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.scanner.token.Token;
import org.junit.jupiter.api.Test;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.ScannerInitializeException;

import java.io.StringReader;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScannerTest {

    @Test
    void initialScannerState() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        Token token = scanner.getToken();
        assertEquals(Token.Type.NULL, token.getType());
    }

    @Test
    void catchScannerInitializeException() {
        StringReader reader = new StringReader("");
        reader.close();
        assertThrows(ScannerInitializeException.class,
                () -> Scanner.getInstance(reader));
    }

    @Test
    void getIdent() {
        Scanner scanner = Scanner.getInstance(new StringReader("abc"));
        Token token = scanner.read();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("abc", token.getValue());

        Token other = scanner.getToken();
        assertSame(token, other);

        scanner = Scanner.getInstance(new StringReader("  abc"));
        token = scanner.read();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("abc", token.getValue());
    }

    @Test
    void getDigit() {
        Scanner scanner = Scanner.getInstance(new StringReader("12345"));
        Token token = scanner.read();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals("12345", token.getValue());
    }

    @Test
    void getReservedToken() {
        Map<String, Token.Type> reservedWords =
                new HashMap<String, Token.Type>()
                {
                    { put("odd", Token.Type.ODD); }
                    { put("begin", Token.Type.BEGIN); }
                    { put("end", Token.Type.END); }
                    { put("if", Token.Type.IF); }
                    { put("then", Token.Type.THEN); }
                    { put("while", Token.Type.WHILE); }
                    { put("do", Token.Type.DO); }
                    { put("call", Token.Type.CALL); }
                    { put("const", Token.Type.CONST); }
                    { put("var", Token.Type.VAR); }
                    { put("procedure", Token.Type.PROCEDURE); }
                    { put("+", Token.Type.PLUS); }
                    { put("-", Token.Type.MINUS);}
                    { put("*", Token.Type.TIMES); }
                    { put("/", Token.Type.SLASH); }
                    { put(",", Token.Type.COMMA); }
                    { put(";", Token.Type.SEMICOLON); }
                    { put(".", Token.Type.PERIOD); }
                    { put(":=", Token.Type.BECOMES); }
                    { put("<", Token.Type.LESS); }
                    { put("<=", Token.Type.LESS_EQUAL); }
                    { put(">", Token.Type.GREATER); }
                    { put(">=", Token.Type.GREATER_EQUAL); }
                    { put("(", Token.Type.LPAREN); }
                    { put(")", Token.Type.RPAREN); }
                    { put("=", Token.Type.EQUAL); }
                    { put("!=", Token.Type.NOT_EQUAL); }
                };
        for (String word: reservedWords.keySet()) {
            Token.Type expected = reservedWords.get(word);
            Scanner scanner = Scanner.getInstance(new StringReader(word));
            Token token = scanner.read();
            String message = "expect: " + word;
            assertEquals(expected, token.getType(), message);
        }
    }

    @Test
    void getMultipleTokens() {
        Scanner scanner = Scanner.getInstance(new StringReader("var a := 1;"));
        Token token = scanner.read();
        assertEquals(Token.Type.VAR, token.getType());
        token = scanner.read();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("a", token.getValue());
        token = scanner.read();
        assertEquals(Token.Type.BECOMES, token.getType());
        token = scanner.read();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals("1", token.getValue());
        token = scanner.read();
        assertEquals(Token.Type.SEMICOLON, token.getType());
    }
}
