package jp.tsaw.pl0lang.scanner;

import jp.tsaw.pl0lang.scanner.token.Token;

import java.io.IOException;
import java.io.Reader;

public class Scanner implements AutoCloseable {

    private static final int EOF = -1;

    private final Reader reader;

    /* 先読みした文字を保持 */
    private int readedChar;

    /* Token を保持 */
    private Token token;

    /* 現在の token が存在する行 */
    private int linePosition;

    /* 現在の token の開始位置 */
    private int tokenPosition;

    /* token 取得までに読み込んだ文字数 */
    private int count;

    private Scanner(Reader reader) {
        try {
            this.reader = reader;
            this.readedChar = reader.read();
            this.token = Token.getNull();
            this.linePosition = 1;
            this.tokenPosition = 1;
            this.count = 0;
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

    public int getLinePosition() {
        return linePosition;
    }

    public int getTokenPosition() {
        return tokenPosition;
    }

    public Token read() {
        StringBuilder valueBuilder = new StringBuilder();
        tokenPosition += count;
        try {
            while (Character.isWhitespace(readedChar)) {
                if (readedChar == '\n') {
                    linePosition += 1;
                    tokenPosition = 1;
                } else {
                    tokenPosition += 1;
                }
                readedChar = reader.read();
            }
            if (readedChar == EOF) {
                token = Token.getNull();
            }
            count = 0;
            if (Character.isDigit(readedChar)) {
                appendAndRead(valueBuilder);
                while (Character.isDigit(readedChar)) {
                    appendAndRead(valueBuilder);
                }
                token = Token.getNumber(valueBuilder.toString());
            } else {
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
                } else if (readedChar == Character.hashCode(':')
                        || readedChar == Character.hashCode('!')) {
                    appendAndRead(valueBuilder);
                    if (readedChar != Character.hashCode('=')) {
                        return Token.getNull(); // 本来は受理できないので別扱いにすべき
                    }
                    appendAndRead(valueBuilder);
                } else if (readedChar == Character.hashCode('>')
                        || readedChar == Character.hashCode('<')) {
                    appendAndRead(valueBuilder);
                    if (readedChar == Character.hashCode('=')) {
                        appendAndRead(valueBuilder);
                    }
                } else if (Character.isLetter(readedChar)) {
                    appendAndRead(valueBuilder);
                    while (Character.isLetterOrDigit(readedChar)) {
                        appendAndRead(valueBuilder);
                    }
                }
                token = Token.getLiteralToken(valueBuilder.toString());
            }
        } catch (IOException e) {
            throw new ScannerException(e);
        }
        return token;
    }

    private void appendAndRead(StringBuilder builder) throws IOException {
        builder.append((char)readedChar);
        readedChar = reader.read();
        count += 1;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
