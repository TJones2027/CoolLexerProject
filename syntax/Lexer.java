package syntax;

import java.util.*;

/**
 * Lexical Analyzer for CS354 programming language
 */
public class Lexer {

    private String program;      // source program being interpreted
    private int position;        // index of next char in program

    private Set<String> whitespace = new HashSet<>();
    private Set<String> letters = new HashSet<>();
    private Set<String> digits = new HashSet<>();
    private Set<String> keywords = new HashSet<>();
    private Set<String> operator = new HashSet<>();

    /**
     * Creates a new lexical analyzer
     *
     * @param program - the program text to scan
     */
    public Lexer(String program) {
        this.program = program;
        position = 0;
        initWhitespace(whitespace);
        initLetters(letters);
        initKeywords(keywords);
        initDigits(digits);
        initOperator(operator);
    }

    // defines keywords
    private void initKeywords(Set<String> s) {
        s.add("wr");
    }

    // defines letters available
    private void initLetters(Set<String> s) {
        fill(s, 'A', 'Z');
        fill(s, 'a', 'z');
    }

    // defines valid digits
    private void initDigits(Set<String> s) {
        fill(s, '0', '9');
    }

    // defines operators
    private void initOperator(Set<String> s) {
        s.add(")");
        s.add("(");
        s.add("=");
        s.add(";");
        s.add("/");
        s.add("*");
        s.add("-");
        s.add("+");
    }

    private void fill(Set<String> s, char lo, char hi) {
        for (char c = lo; c <= hi; c++) {
            s.add(c + "");
        }
    }

    // defines whitespace
    private void initWhitespace(Set<String> s) {
        s.add(" ");
        s.add("\n");
        s.add("\t");
        s.add("\r");
    }

    private void advance() {
        this.position++;
    }

    private String peek() {
        if (hasChar()) {
            return program.charAt(position) + "";
        } else {
            return null;
        }
    }

    // skips a comment up to (not including) the end of the line
    private void skipComment() {
        while (hasChar() && !"\n".equals(peek())) {
            advance();
        }
    }

    /**
     * Scans a keyword or an id (a letter followed by letters or digits)
     *
     * @return scanned token
     */
    private Token nextKwID() {

        int old = this.position;
        advance();

        while (hasChar() && (letters.contains(peek()) || digits.contains(peek()))) {
            advance();
        }

        String lexeme = program.substring(old, position);

        if (keywords.contains(lexeme))
            return new Token(lexeme, lexeme);
        else
            return new Token("id", lexeme);
    }

    /**
     * Scans a number, including forms like 5, 5.25 and .5
     *
     * @return scanned token
     */
    private Token nextNumber() {
        int old = this.position;

        // consume initial digits
        while (hasChar() && digits.contains(peek())) {
            advance();
        }

        // handle decimal point
        if (hasChar() && ".".equals(peek())) {
            advance();
            while (hasChar() && digits.contains(peek())) {
                advance();
            }
        }

        String lexeme = program.substring(old, position);
        return new Token("num", lexeme);
    }

    /**
     * Scans a single-character operator
     *
     * @return the scanned token
     */
    private Token nextOperator() {
        int old = this.position;
        advance();
        String lexeme = program.substring(old, position);
        return new Token(lexeme);
    }

    /**
     * Determines the kind of the next token (e.g., "id") and calls the
     * appropriate method to scan the token's lexeme (e.g., "foo").
     *
     * @return the scanned token.
     */
    public Token next() {

        while (hasChar() && whitespace.contains(peek())) {
            advance();
        }

        if (!hasChar()) {
            return new Token("EOF");
        }
        else if (letters.contains(peek())) {
            return nextKwID();
        }
        else if (digits.contains(peek())) {
            return nextNumber();
        }
        // a '.' only starts a number if a digit follows it (e.g. .5)
        else if (".".equals(peek())
                && position + 1 < program.length()
                && digits.contains(program.charAt(position + 1) + "")) {
            return nextNumber();
        }
        // "//" starts a comment; this check must come before the operator check
        else if ("/".equals(peek())
                && position + 1 < program.length()
                && "/".equals(program.charAt(position + 1) + "")) {
            skipComment();
            return next();
        }
        else if (operator.contains(peek())) {
            return nextOperator();
        }
        else {
            System.err.println("illegal character at position " + position);
            position++;
            return next();
        }
    }

    /**
     * Determines if the current position of the lexer is in the bounds of the
     * program
     * @return true if there are more characters in program
     */
    public boolean hasChar() {
        return position < program.length();
    }

    /**
     * Getter for position of the lexer in the program
     * @return index of the current position of the scanner
     */
    public int getPosition() {
        return position;
    }
}