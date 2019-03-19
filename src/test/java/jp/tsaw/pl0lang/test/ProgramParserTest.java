package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.ProgramParser;
import jp.tsaw.pl0lang.scanner.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

class ProgramParserTest {

    @Test
    void createProgramParser() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        ProgramParser parser = ProgramParser.getInstance(scanner);
        assertTrue(true);
    }
}
