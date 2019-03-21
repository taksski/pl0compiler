package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

import java.sql.Statement;

public class BlockParser extends AbstractParser {

    private BlockParser(Scanner scanner) {
        super(scanner);
    }

    public static BlockParser getInstance(Scanner scanner) {
        return new BlockParser(scanner);
    }

    @Override
    public String parse() {
        Token token = scanner.getToken();

        if (token.getType() == Token.Type.CONST) {
            scanner.read();
            if (parseConstBlock()) {
                token = scanner.read();
                while (token.getType() == Token.Type.COMMA) {
                    scanner.read();
                    if (parseConstBlock()) {
                        token = scanner.read();
                    } else {
                        return ERROR;
                    }
                }
            } else {
                return ERROR;
            }
            if (token.getType() != Token.Type.SEMICOLON) {
                return "parse error!";
            }
            token = scanner.read();
        }

        if (token.getType() == Token.Type.VAR) {
            token = scanner.read();
            if (token.getType() == Token.Type.IDENT) {
                token = scanner.read();
                if (token.getType() == Token.Type.COMMA) {
                    token = scanner.read();
                } else if (token.getType() != Token.Type.SEMICOLON) {
                    return "parse error!";
                }
            } else {
                return "parse error!";
            }
            while (token.getType() == Token.Type.IDENT) {
                token = scanner.read();
                if (token.getType() == Token.Type.COMMA) {
                    token = scanner.read();
                } else if (token.getType() != Token.Type.SEMICOLON) {
                    return "parse error!";
                }
            }
            token = scanner.read();
        }

        while (token.getType() == Token.Type.PROCEDURE) {
            token = scanner.read();
            if (token.getType() == Token.Type.IDENT) {
                token = scanner.read();
                if (token.getType() == Token.Type.SEMICOLON) {
                    scanner.read();
                    BlockParser procParser = BlockParser.getInstance(scanner);
                    String result = procParser.parse();
                    if (result.equals("accept")) {
                        token = scanner.read();
                        if (token.getType() != Token.Type.SEMICOLON) {
                            return "parse error!";
                        }

                    } else {
                        return result;
                    }
                } else {
                    return "parse error!";
                }
            } else {
                return "parse error!";
            }
            token = scanner.read();
        }
        return (StatementParser.getInstance(scanner)).parse();
    }

    private boolean parseConstBlock() {
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
