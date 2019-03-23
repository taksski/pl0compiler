package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.StatementParser;
import jp.tsaw.pl0lang.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatementParserTest {

    @Test
    void acceptEmptyStatement() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        scanner.read();
        StatementParser parser = StatementParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void acceptAssignStatement() {
        Scanner scanner = Scanner.getInstance(new StringReader("a := 1"));
        scanner.read();
        StatementParser parser = StatementParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void acceptCallStatement() {
        Scanner scanner = Scanner.getInstance(new StringReader("call test"));
        scanner.read();
        StatementParser parser = StatementParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void errrorCallStatement() {
        Scanner scanner = Scanner.getInstance(new StringReader("call 123")); // number is not acceptable.
        scanner.read();
        StatementParser parser = StatementParser.getInstance();
        assertEquals(AbstractParser.ERROR, parser.parse(scanner));
    }
}
