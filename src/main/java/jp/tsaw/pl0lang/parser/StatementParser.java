package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class StatementParser extends AbstractParser {

    private StatementParser(Scanner scanner) {
        super(scanner);
    }

    public static StatementParser getInstance(Scanner scanner) {
        return new StatementParser(scanner);
    }

    @Override
    public String parse() {
        return ACCEPT;
    }

}
