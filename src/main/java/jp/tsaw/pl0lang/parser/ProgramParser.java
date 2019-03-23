package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;
import jp.tsaw.pl0lang.scanner.token.Token;

public class ProgramParser extends AbstractParser {

    private ProgramParser() {
        super();
    }

    public static ProgramParser getInstance() {
        return new ProgramParser();
    }

    @Override
    public String parse(Scanner scanner) {
        String result = BlockParser.getInstance().parse(scanner);
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
