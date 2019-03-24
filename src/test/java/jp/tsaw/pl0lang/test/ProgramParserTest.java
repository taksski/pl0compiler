package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.ProgramParser;
import jp.tsaw.pl0lang.scanner.Scanner;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ProgramParserTest {

    @Test
    void emptyProgram() {
        Scanner scanner = Scanner.getInstance(new StringReader("."));
        scanner.read();
        ProgramParser parser = ProgramParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void acceptFile() {
        try {
            String testFile = "gcd.pl0";
            String path = getClass().getClassLoader().getResource(testFile).getPath();
            FileReader reader =
                    new FileReader(path);
            Scanner scanner = Scanner.getInstance(reader);
            scanner.read();
            ProgramParser parser = ProgramParser.getInstance();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
        } catch (FileNotFoundException e) {
            fail("No test file!");
        }
    }
}