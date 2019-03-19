package jp.tsaw.pl0lang.scanner.token;

public class Token {

    public enum Type {
        NULL("NULL",""),
        IDENT("IDENT",""),
        NUMBER("NUMBER",""),
        PLUS("+","+"),
        MINUS("-","-"),
        TIMES("*","*"),
        SLASH("/","/"),
        ODD("SYMBOL_odd","odd"),
        EQUAL("=", "="),
        NOT_EQUAL("!=", "!="),
        LESS("<", "<"),
        LESS_EQUAL("<=", "<="),
        GREATER(">", ">"),
        GREATER_EQUAL(">=", ">="),
        LPAREN("(", "("),
        RPAREN(")", ")"),
        COMMA(",",","),
        SEMICOLON(";",";"),
        PERIOD(".","."),
        BECOMES(":=",":="),
        BEGIN("SYMBOL_begin","begin"),
        END("SYMBOL_end", "end"),
        IF("SYMBOL_if", "if"),
        THEN("SYMBOL_then", "then"),
        WHILE("SYMBOL_while", "while"),
        DO("SYMBOL_do", "do"),
        CALL("SYMBOL_call", "call"),
        CONST("SYMBOL_const", "const"),
        VAR("SYMBOL_var","var"),
        PROCEDURE("SYMBOL_procedure", "procedure");

        private final String name;
        private final String symbol;

        Type(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        public String toString() { return name; }
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
