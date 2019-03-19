package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class ProgramParser {

    private Scanner scanner;
    private Token readedToken;

    private ProgramParser(Scanner scanner) {
        this.scanner = scanner;
        readedToken = null;
    }

    public static ProgramParser getInstance(Scanner scanner) {
        return new ProgramParser(scanner);
    }

    public String parse(Token token) {
        return "parse error!";
    }
}
