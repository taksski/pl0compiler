package jp.tsaw.pl0lang.scanner.token;

public class Token {

    public enum Type {
        NULL(""),
        IDENT(""),
        NUMBER(""),
        PLUS("+"),
        MINUS("-"),
        TIMES("*"),
        SLASH("/"),
        ODD("odd"),
        EQUAL("="),
        NOT_EQUAL("!="),
        LESS("<"),
        LESS_EQUAL("<="),
        GREATER(">"),
        GREATER_EQUAL(">="),
        LPAREN("("),
        RPAREN(")"),
        COMMA(","),
        SEMICOLON(";"),
        PERIOD("."),
        BECOMES(":="),
        BEGIN("begin"),
        END("end"),
        IF("if"),
        THEN("then"),
        WHILE("while"),
        DO("do"),
        CALL("call"),
        CONST("const"),
        VAR("var"),
        PROCEDURE("procedure");

        private final String symbol;

        Type(String symbol) {
            this.symbol = symbol;
        }

        public String toString() { return symbol; }
        public boolean match(String value) { return symbol.equals(value); }
    }

    private Type type;
    private String value;

    private Token(Type type) {
        this.type = type;
        this.value = type.toString();
    }

    private Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }


    public static Token getNull() { return new Token(Type.NULL);}
    public static Token getLiteralToken(String value) {
        for (Type e: Type.values()) {
            if (e.match(value)) {
                return new Token(e);
            }
        }

        return new Token(Type.IDENT, value);
    }
    public static Token getNumber(String value) {
        return new Token(Type.NUMBER, value);
    }

    public Type getType() { return type; }
    public String getValue() { return value; }
}
