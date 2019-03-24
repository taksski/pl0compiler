package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.ExpressionParser;
import jp.tsaw.pl0lang.parser.FactorParser;
import jp.tsaw.pl0lang.parser.TermParser;
import jp.tsaw.pl0lang.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionParserTest {

    @Test
    void createExpressionParser() {
        ExpressionParser parser = ExpressionParser.getInstance();
        assertTrue(true);
    }

    @Test
    void createTermParser() {
        TermParser parser = TermParser.getInstance();
        assertTrue(true);
    }

    @Test
    void createFactorParser() {
        FactorParser parser = FactorParser.getInstance();
        assertTrue(true);
    }

    @Test
    void acceptFactors() {
        String[] factors = {
                "a", // ident
                "123", //number
                "(a + 1)" // braced expression
        };
        for (String factor: factors) {
            Scanner scanner = Scanner.getInstance(new StringReader(factor));
            FactorParser parser = FactorParser.getInstance();
            scanner.read();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner), "factor: \n" + factor);
        }
    }

    @Test
    void complexTerm() {
        String[] terms = {
                "a * b",
                "a / b",
                "4 * 3 / 2"
        };
        for (String term: terms) {
            Scanner scanner = Scanner.getInstance(new StringReader(term));
            TermParser parser = TermParser.getInstance();
            scanner.read();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
        }
    }

    @Test
    void termExpression() {
        String[] terms = {
                "a * b",
                "a / b",
                "4 * 3 / 2"
        };
        for (String term: terms) {
            Scanner scanner = Scanner.getInstance(new StringReader(term));
            ExpressionParser parser = ExpressionParser.getInstance();
            scanner.read();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
        }
    }

    @Test
    void complexExpression() {
        String[] expressions = {
                "a + b * c",
                "a - b",
                "1 + d / x",
                "200 * 30 + e"
        };
        for (String expression: expressions) {
            Scanner scanner = Scanner.getInstance(new StringReader(expression));
            ExpressionParser parser = ExpressionParser.getInstance();
            scanner.read();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner), "Expression: "+expression);
        }
    }
}
