package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

import javax.sound.sampled.FloatControl;

public class ExpressionParser extends AbstractParser {

    private ExpressionParser() { super(); }

    public static ExpressionParser getInstance() {
        return new ExpressionParser();
    }

    @Override
    public String parse(Scanner scanner) {
        Token token = scanner.getToken();
        if (token.getType() == Token.Type.PLUS ||
            token.getType() == Token.Type.MINUS) {
            scanner.read();
        }
        TermParser termParser = TermParser.getInstance();
        String result = termParser.parse(scanner);
        if (result.equals(ACCEPT)) {
            token = scanner.read();
            while (token.getType() == Token.Type.PLUS ||
                   token.getType() == Token.Type.MINUS) {
                scanner.read();
                termParser = TermParser.getInstance();
                result = termParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    token = scanner.read();
                } else {
                    break;
                }
            }
        }
        return result;
    }
}
