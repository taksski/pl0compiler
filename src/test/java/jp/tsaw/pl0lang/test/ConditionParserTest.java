package jp.tsaw.pl0lang.test;

import jp.tsaw.pl0lang.parser.AbstractParser;
import jp.tsaw.pl0lang.parser.ConditionParser;
import jp.tsaw.pl0lang.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ConditionParserTest {

    @Test
    void createConditionParser() {
        ConditionParser.getInstance();
        assertTrue(true);
    }

    @Test
    void acceptOddCondition() {
        String condition = "odd a+1";
        Scanner scanner = Scanner.getInstance(new StringReader(condition));
        scanner.read();
        ConditionParser parser = ConditionParser.getInstance();
        assertEquals(AbstractParser.ACCEPT, parser.parse(scanner));
    }

    @Test
    void acceptCompareCondition() {
        String[] conditions = {
                "a = b", // equal
                "a != b", // not equal
                "a > b", // greater
                "a >= b", //greater equal
                "a < b",  // less
                "a <= b" // less equal
        };
        for (String condition: conditions) {
            Scanner scanner = Scanner.getInstance(new StringReader(condition));
            scanner.read();
            ConditionParser parser = ConditionParser.getInstance();
            assertEquals(AbstractParser.ACCEPT, parser.parse(scanner),
                    "condition: " + condition );
        }
    }
}
