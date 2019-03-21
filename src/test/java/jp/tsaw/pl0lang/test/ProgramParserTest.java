package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.ProgramParser;
import jp.tsaw.pl0lang.scanner.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

class ProgramParserTest {

    @Test
    void createProgramParser() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        ProgramParser.getInstance(scanner);
        assertTrue(true);
    }

    @Test
    void emptyProgram() {
        Scanner scanner = Scanner.getInstance(new StringReader("."));
        scanner.read();
        ProgramParser parser = ProgramParser.getInstance(scanner);
        assertEquals(AbstractParser.ACCEPT, parser.parse());
    }
}