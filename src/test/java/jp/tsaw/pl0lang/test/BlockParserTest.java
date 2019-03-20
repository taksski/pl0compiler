package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.BlockParser;
import jp.tsaw.pl0lang.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockParserTest {

    @Test
    void createBlockParser() {
        Scanner scanner = Scanner.getInstance(new StringReader(""));
        BlockParser.getInstance(scanner);
        assertTrue(true);
    }

    @Test
    void acceptConstBlock() {
        String[] statements = {
                "const a = 1;",
                "const a = 1, b = 2;"
        };
        for (String statement: statements) {
            Scanner scanner = Scanner.getInstance(new StringReader(statement));
            scanner.read();
            BlockParser parser = BlockParser.getInstance(scanner);
            String result = parser.parse();
            assertEquals("accept", result, "test statement: "+statement);
        }
    }

    @Test
    void errorConstBlock() {
        String[] errors = {
                "const b := 0;", // illegal use ':=' instead of '='
                "const a b = 0;", // continuous identifiers
                "const a = 0, b, c = 1;", // uninitialized constant
                "const ;" // no const definition
        };
        for (String error: errors) {
            Scanner scanner = Scanner.getInstance(new StringReader(error));
            scanner.read();
            BlockParser parser = BlockParser.getInstance(scanner);
            String result = parser.parse();
            assertEquals("parse error!", result, "test statement: "+error);
        }
    }

    @Test
    void acceptVarBlock() {
        String statement = "var a, b, c;";
        Scanner scanner = Scanner.getInstance(new StringReader(statement));
        scanner.read();
        BlockParser parser = BlockParser.getInstance(scanner);
        String result = parser.parse();
        assertEquals("accept", result);
    }

    @Test
    void errorVarBlock() {
        String[] errors = {
                "var a b c;", // var definition without comma
                "var a = 1;", // var definition with initialization
                "var ;" // no var definition
        };
        for (String error : errors) {
            Scanner scanner = Scanner.getInstance(new StringReader(error));
            scanner.read();
            BlockParser parser = BlockParser.getInstance(scanner);
            String result = parser.parse();
            assertEquals("parse error!", result, "statement: "+error);
        }
    }
}
