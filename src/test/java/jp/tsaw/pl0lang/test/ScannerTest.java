package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.scanner.token.Token;
import org.junit.jupiter.api.Test;

import jp.tsaw.pl0lang.scanner.Scanner;

import java.io.StringReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void emptyScanner() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        Token token = scanner.getToken();
        assertEquals(Token.Type.NULL, token.getType());
    }

    @Test
    void getIdent() {
        Scanner scanner = Scanner.getInstance(new StringReader("abc"));
        Token token = scanner.getToken();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("abc", token.getValue());

        scanner = Scanner.getInstance(new StringReader("  abc"));
        token = scanner.getToken();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("abc", token.getValue());
    }

    @Test
    void getDigit() {
        Scanner scanner = Scanner.getInstance(new StringReader("12345"));
        Token token = scanner.getToken();
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
            Token token = scanner.getToken();
            String message = "expect: " + word;
            assertEquals(expected, token.getType(), message);
        }
    }

    @Test
    void getMultipleTokens() {
        Scanner scanner = Scanner.getInstance(new StringReader("var a := 1;"));
        Token token = scanner.getToken();
        assertEquals(Token.Type.VAR, token.getType());
        token = scanner.getToken();
        assertEquals(Token.Type.IDENT, token.getType());
        assertEquals("a", token.getValue());
        token = scanner.getToken();
        assertEquals(Token.Type.BECOMES, token.getType());
        token = scanner.getToken();
        assertEquals(Token.Type.NUMBER, token.getType());
        assertEquals("1", token.getValue());
        token = scanner.getToken();
        assertEquals(Token.Type.SEMICOLON, token.getType());
    }
}
