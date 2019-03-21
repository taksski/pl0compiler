package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class ProgramParser extends AbstractParser {

    private ProgramParser(Scanner scanner) {
        super(scanner);
    }

    public static ProgramParser getInstance(Scanner scanner) {
        return new ProgramParser(scanner);
    }

    @Override
    public String parse() {
        String result = BlockParser.getInstance(scanner).parse();
        if (result.equals(ACCEPT)){
            Token token = scanner.getToken();
            if (token.getType() != Token.Type.PERIOD) {
                return ERROR;
            }
        } else {
            return ERROR;
        }
        return ACCEPT;
    }
}
