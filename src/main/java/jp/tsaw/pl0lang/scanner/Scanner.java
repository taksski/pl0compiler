package jp.tsaw.pl0lang.scanner;

import jp.tsaw.pl0lang.scanner.token.Token;

import java.io.IOException;
import java.io.Reader;

public class Scanner {

    private static final int EOF = -1;

    private final Reader reader;

    /* 先読みした文字を保持 */
    private int readedChar;

    private Scanner(Reader reader) {
        try {
            this.reader = reader;
            this.readedChar = reader.read();
        } catch (IOException e) {
            throw new ScannerInitializeException(e);
        }
    }

    public static Scanner getInstance(Reader reader) {
        return new Scanner(reader);
    }

    public Token getToken() {
        StringBuilder valueBuffer = new StringBuilder();
        try {
            while (Character.isSpaceChar(readedChar)) {
                readedChar = reader.read();
            }
            if (readedChar == EOF) {
                return Token.getNull();
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
                String symbolValue = String.valueOf((char) readedChar);
                readedChar = reader.read();
                return Token.getLiteralToken(symbolValue);
            }
            if (readedChar == Character.hashCode(':')
                    || readedChar == Character.hashCode('!')) {
                valueBuffer.append((char)readedChar);
                readedChar = reader.read();
                if (readedChar != Character.hashCode('=')) {
                    return Token.getNull(); // 本来は受理できないので別扱いにすべき
                }
                valueBuffer.append((char)readedChar);
                readedChar = reader.read();
                return Token.getLiteralToken(valueBuffer.toString());
            }
            if (readedChar == Character.hashCode('>')
                    || readedChar == Character.hashCode('<')) {
                valueBuffer.append((char)readedChar);
                readedChar = reader.read();
                if (readedChar != Character.hashCode('=')) {
                    return Token.getLiteralToken(valueBuffer.toString());
                } else {
                    valueBuffer.append((char) readedChar);
                    readedChar = reader.read();
                    return Token.getLiteralToken(valueBuffer.toString());
                }
            }
            if (Character.isLetter(readedChar)) {
                valueBuffer.append((char) readedChar);
                readedChar = reader.read();
                while (Character.isLetterOrDigit(readedChar)) {
                    valueBuffer.append((char) readedChar);
                    readedChar = reader.read();
                }
                String value = valueBuffer.toString();
                return Token.getLiteralToken(value);
            } else if (Character.isDigit(readedChar)) {
                valueBuffer.append((char) readedChar);
                readedChar = reader.read();
                while (Character.isDigit(readedChar)) {
                    valueBuffer.append((char) readedChar);
                    readedChar = reader.read();
                }
                return Token.getNumber(valueBuffer.toString());
            }
        } catch (IOException e) {
            throw new ScannerException(e);
        }
        return Token.getNull();
    }
}
