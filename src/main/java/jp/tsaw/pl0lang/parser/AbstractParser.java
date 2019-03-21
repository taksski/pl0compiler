package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;

public abstract class AbstractParser {

    public static String ACCEPT = "accept";
    public static String ERROR = "parse error!";

    protected Scanner scanner;

    AbstractParser(Scanner scanner) {
        this.scanner = scanner;
    }

    abstract public String parse();
}
