package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class TermParser extends AbstractParser {

    private TermParser() { super(); }

    public static TermParser getInstance() {
        return new TermParser();
    }

    @Override
    public String parse(Scanner scanner) {
        FactorParser factorParser = FactorParser.getInstance();
        String result = factorParser.parse(scanner);
        if (result.equals(ACCEPT)) {
            Token token = scanner.getToken();
            while (token.getType() == Token.Type.TIMES ||
                token.getType() == Token.Type.SLASH) {
                scanner.read();
                factorParser = FactorParser.getInstance();
                result = factorParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    token = scanner.getToken();
                } else {
                    break;
                }
            }
        }
        return result;
    }
}
