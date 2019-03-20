package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class ProgramParser {

    private Scanner scanner;

    private ProgramParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public static ProgramParser getInstance(Scanner scanner) {
        return new ProgramParser(scanner);
    }

    public String parse() {
        scanner.read();
        String result = BlockParser.getInstance(scanner).parse();
        if (result.equals("accept")){
            Token token = scanner.read();
            if (token.getType() == Token.Type.PERIOD) {
                return "accept";
            }
        }
        return "parse error!";
    }
}
