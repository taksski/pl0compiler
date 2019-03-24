package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class StatementParser extends AbstractParser {

    private StatementParser() { super(); }

    public static StatementParser getInstance() {
        return new StatementParser();
    }

    @Override
    public String parse(Scanner scanner) {
        Token token = scanner.getToken();
        switch(token.getType()) {
            case IDENT:
                if (scanner.read().getType() == Token.Type.BECOMES) {
                    scanner.read();
                    ExpressionParser expressionParser = ExpressionParser.getInstance();
                    return expressionParser.parse(scanner);
                } else {
                    return ERROR;
                }
            case CALL:
                if (scanner.read().getType() == Token.Type.IDENT) {
                    scanner.read();
                    return ACCEPT;
                } else {
                    return ERROR;
                }
            case BEGIN:
                scanner.read();
                StatementParser statementParser = StatementParser.getInstance();
                String result = statementParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    while (scanner.getToken().getType() == Token.Type.SEMICOLON) {
                        scanner.read();
                        result = statementParser.parse(scanner);
                        if (!result.equals(ACCEPT)) {
                            return ERROR;
                        }
                    }
                    if (scanner.getToken().getType() == Token.Type.END) {
                        scanner.read();
                        return ACCEPT;
                    } else {
                        return ERROR;
                    }
                } else {
                    return ERROR;
                }
            case IF:
                scanner.read();
                ConditionParser conditionParser = ConditionParser.getInstance();
                result = conditionParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    if (scanner.getToken().getType() == Token.Type.THEN) {
                        scanner.read();
                        statementParser = StatementParser.getInstance();
                        return statementParser.parse(scanner);
                    } else {
                        return ERROR;
                    }
                } else {
                    return ERROR;
                }
            case WHILE:
                scanner.read();
                conditionParser = ConditionParser.getInstance();
                result = conditionParser.parse(scanner);
                if (result.equals(ACCEPT)) {
                    if (scanner.getToken().getType() == Token.Type.DO) {
                        scanner.read();
                        statementParser = StatementParser.getInstance();
                        return statementParser.parse(scanner);
                    }
                }
                return ERROR;
            default: // 空文の処理
                return ACCEPT;
        }
    }

}
