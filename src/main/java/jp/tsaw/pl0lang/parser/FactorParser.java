package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class FactorParser extends AbstractParser {

    private FactorParser() { super(); }

    public static FactorParser getInstance() {
        return new FactorParser();
    }

    @Override
    public String parse(Scanner scanner) {
        Token token = scanner.getToken();
        switch (token.getType()) {
            case IDENT:
            case NUMBER:
                return ACCEPT;
            case LPAREN:
                scanner.read();
                ExpressionParser expressionParser = ExpressionParser.getInstance();
                String result = expressionParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    token = scanner.read();
                    if (token.getType() == Token.Type.RPAREN) {
                        scanner.read();
                        return ACCEPT;
                    } else {
                        return ERROR;
                    }
                } else {
                    return ERROR;
                }
            default:
                return ERROR;
        }
    }
}
