package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class ConditionParser extends AbstractParser {

    private ConditionParser() { super(); }

    public static ConditionParser getInstance() {
        return new ConditionParser();
    }

    @Override
    public String parse(Scanner scanner) {
        Token token = scanner.getToken();
        if (token.getType() == Token.Type.ODD) {
            scanner.read();
            ExpressionParser expressionParser = ExpressionParser.getInstance();
            return expressionParser.parse(scanner);
        } else {
            ExpressionParser leftExpressionParser = ExpressionParser.getInstance();
            String result = leftExpressionParser.parse(scanner);
            if (result.equals(ACCEPT)) {
                token = scanner.getToken();
                if (token.getType() == Token.Type.EQUAL ||
                        token.getType() == Token.Type.NOT_EQUAL ||
                        token.getType() == Token.Type.LESS ||
                        token.getType() == Token.Type.LESS_EQUAL ||
                        token.getType() == Token.Type.GREATER ||
                        token.getType() == Token.Type.GREATER_EQUAL) {
                    scanner.read();
                    ExpressionParser rightExpressionParser = ExpressionParser.getInstance();
                    result = rightExpressionParser.parse(scanner);
                    return result;
                }
            } else {
                return ERROR;
            }
        }
        return ERROR;
    }
}
