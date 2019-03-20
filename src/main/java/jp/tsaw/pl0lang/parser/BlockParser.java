package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class BlockParser {

    private final Scanner scanner;

    private BlockParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public static BlockParser getInstance(Scanner scanner) {
        return new BlockParser(scanner);
    }

    public String parse() {
        Token token = scanner.getToken();

        if (token.getType() == Token.Type.CONST) {
            scanner.read();
            if (!parseConstBlock()) {
                return "parse error!";
            }
            token = scanner.read();
            while (token.getType() == Token.Type.COMMA) {
                scanner.read();
                if (!parseConstBlock()) {
                    return "parse error!";
                }
                token = scanner.read();
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
        if (token.getType() == Token.Type.PROCEDURE) {
            return "parse error!"; // not implemented.
        } else {
            return "accept";
        }
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
