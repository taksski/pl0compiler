package jp.tsaw.pl0lang.scanner;

import jp.tsaw.pl0lang.scanner.token.Token;

import java.io.IOException;
import java.io.Reader;

public class Scanner {

    private static final int EOF = -1;

    private final Reader reader;

    /* 先読みした文字を保持 */
    private int readedChar;

    /* Token を保持 */
    private Token token;

    private Scanner(Reader reader) {
        try {
            this.reader = reader;
            this.readedChar = reader.read();
            this.token = Token.getNull();
        } catch (IOException e) {
            throw new ScannerInitializeException(e);
        }
    }

    public static Scanner getInstance(Reader reader) {
        return new Scanner(reader);
    }

    public Token getToken() {
        return token;
    }

    public Token read() {
        StringBuilder valueBuilder = new StringBuilder();
        try {
            while (Character.isSpaceChar(readedChar)) {
                readedChar = reader.read();
            }
            if (readedChar == EOF) {
                token = Token.getNull();
            }
            if (readedChar == Character.hashCode('+')
                    || readedChar == Character.hashCode('-')
                    || readedChar == Character.hashCode('*')
                    || readedChar == Character.hashCode('/')
                    || readedChar == Character.hashCode(',')
                    || readedChar == Character.hashCode(';')
                    || readedChar == Character.hashCode('.')
                    || readedChar == Character.hashCode('(')
                    || readedChar == Character.hashCode(')')
                    || readedChar == Character.hashCode('=')) {
                appendAndRead(valueBuilder);
                token = Token.getLiteralToken(valueBuilder.toString());
            }
            if (readedChar == Character.hashCode(':')
                    || readedChar == Character.hashCode('!')) {
                appendAndRead(valueBuilder);
                if (readedChar != Character.hashCode('=')) {
                    token = Token.getNull(); // 本来は受理できないので別扱いにすべき
                }
                appendAndRead(valueBuilder);
                token = Token.getLiteralToken(valueBuilder.toString());
            }
            if (readedChar == Character.hashCode('>')
                    || readedChar == Character.hashCode('<')) {
                appendAndRead(valueBuilder);
                if (readedChar != Character.hashCode('=')) {
                    token = Token.getLiteralToken(valueBuilder.toString());
                } else {
                    appendAndRead(valueBuilder);
                    token = Token.getLiteralToken(valueBuilder.toString());
                }
            }
            if (Character.isLetter(readedChar)) {
                appendAndRead(valueBuilder);
                while (Character.isLetterOrDigit(readedChar)) {
                    appendAndRead(valueBuilder);
                }
                String value = valueBuilder.toString();
                token = Token.getLiteralToken(value);
            } else if (Character.isDigit(readedChar)) {
                appendAndRead(valueBuilder);
                while (Character.isDigit(readedChar)) {
                    appendAndRead(valueBuilder);
                }
                token = Token.getNumber(valueBuilder.toString());
            }
        } catch (IOException e) {
            throw new ScannerException(e);
        }
        return token;
    }

    private void appendAndRead(StringBuilder builder) throws IOException {
        builder.append((char)readedChar);
        readedChar = reader.read();
    }
}
