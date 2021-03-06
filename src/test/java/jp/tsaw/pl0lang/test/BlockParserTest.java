package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.BlockParser;
import jp.tsaw.pl0lang.scanner.Scanner;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockParserTest {

    @Test
    void createBlockParser() {
        BlockParser.getInstance();
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
            BlockParser parser = BlockParser.getInstance();
            String result = parser.parse(scanner);
            assertEquals(AbstractParser.ACCEPT, result, "test statement: "+statement);
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
            BlockParser parser = BlockParser.getInstance();
            String result = parser.parse(scanner);
            assertEquals(AbstractParser.ERROR, result, "test statement: "+error);
        }
    }

    @Test
    void acceptVarBlock() {
        String statement = "var a, b, c;";
        Scanner scanner = Scanner.getInstance(new StringReader(statement));
        scanner.read();
        BlockParser parser = BlockParser.getInstance();
        String result = parser.parse(scanner);
        assertEquals(AbstractParser.ACCEPT, result);
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
            BlockParser parser = BlockParser.getInstance();
            String result = parser.parse(scanner);
            assertEquals("parse error!", result, "statement: "+error);
        }
    }

    @Test
    void acceptContAndVarDeclaration() {
        String block = "const a = 1, b = 2; var c, d;";
        Scanner scanner = Scanner.getInstance(new StringReader(block));
        scanner.read();
        BlockParser parser = BlockParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void acceptProcedureBlock() {
        String statement = "procedure sample; var a; begin a := 1; end;"; // simple procedure;
        Scanner scanner = Scanner.getInstance(new StringReader(statement));
        scanner.read();
        BlockParser parser = BlockParser.getInstance();
        String result = parser.parse(scanner);
        assertEquals("accept",result);
    }
}
