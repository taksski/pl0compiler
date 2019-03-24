package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class BlockParser extends AbstractParser {

    private BlockParser() {
        super();
    }

    public static BlockParser getInstance() {
        return new BlockParser();
    }

    @Override
    public String parse(Scanner scanner) {
        Token token = scanner.getToken();

        if (token.getType() == Token.Type.CONST) {
            scanner.read();
            if (parseConstBlock(scanner)) {
                token = scanner.read();
                while (token.getType() == Token.Type.COMMA) {
                    scanner.read();
                    if (parseConstBlock(scanner)) {
                        token = scanner.read();
                    } else {
                        return ERROR;
                    }
                }
            } else {
                return ERROR;
            }
            if (token.getType() == Token.Type.SEMICOLON) {
                token = scanner.read();
            } else {
                return ERROR;
            }
        }

        if (token.getType() == Token.Type.VAR) {
            token = scanner.read();
            if (token.getType() == Token.Type.IDENT) {
                token = scanner.read();
                while (token.getType() == Token.Type.COMMA) {
                    token = scanner.read();
                    if (token.getType() == Token.Type.IDENT) {
                        token = scanner.read();
                    } else {
                        return ERROR;
                    }
                }
                if (token.getType() == Token.Type.SEMICOLON) {
                    token = scanner.read();
                } else {
                    return ERROR;
                }
            } else {
                return ERROR;
            }
        }

        while (token.getType() == Token.Type.PROCEDURE) {
            token = scanner.read();
            if (token.getType() == Token.Type.IDENT) {
                token = scanner.read();
                if (token.getType() == Token.Type.SEMICOLON) {
                    scanner.read();
                    BlockParser procParser = BlockParser.getInstance();
                    String result = procParser.parse(scanner);
                    if (result.equals("accept")) {
                        token = scanner.getToken();
                        if (token.getType() != Token.Type.SEMICOLON) {
                            return ERROR;
                        }

                    } else {
                        return result;
                    }
                } else {
                    return ERROR;
                }
            } else {
                return ERROR;
            }
            token = scanner.read();
        }
        return (StatementParser.getInstance()).parse(scanner);
    }

    private boolean parseConstBlock(Scanner scanner) {
        Token token = scanner.getToken();
        if (token.getType() == Token.Type.IDENT) {
            token = scanner.read();
            if (token.getType() == Token.Type.EQUAL) {
                token = scanner.read();
                return (token.getType() == Token.Type.NUMBER);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
