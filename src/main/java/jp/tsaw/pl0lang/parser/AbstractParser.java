package jp.tsaw.pl0lang.parser;

import jp.tsaw.pl0lang.scanner.Scanner;

public abstract class AbstractParser {

    public static String ACCEPT = "accept";
    public static String ERROR = "parse error!";

    abstract public String parse(Scanner scanner);
}
