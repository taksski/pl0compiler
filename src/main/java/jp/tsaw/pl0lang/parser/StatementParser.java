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
                token = scanner.read();
                if (token.getType() == Token.Type.BECOMES) {
                    scanner.read();
                    ExpressionParser expressionParser = ExpressionParser.getInstance();
                    return expressionParser.parse(scanner);
                } else {
                    return ERROR;
                }
            case CALL:
                token = scanner.read();
                if (token.getType() == Token.Type.IDENT) {
                    scanner.read();
                    return ACCEPT;
                } else {
                    return ERROR;
                }
            case BEGIN:// TODO: implement parse methods
            case IF:// TODO: implement parse methods
            case WHILE:// TODO: implement parse methods
                return ERROR;
            default:
        }
        return ACCEPT;
    }

}
