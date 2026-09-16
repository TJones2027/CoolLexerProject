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

    private void initKeywords(Set<String> keywords2) {
        //.... no keywords yet
    }
    //defines letters available
    private void initLetters(Set<String> s) {
        fill(s, 'A', 'Z');
        fill(s, 'a', 'z');
    }
    //defines valid digits
    private void initDigits(Set<String> s){
        fill(s, '0', '9');
    }
    //defines operators
    private void initOperator(Set<String> s){
        s.add( ")" );
        s.add( "(" );
        s.add( "=" );
        s.add( ";" );
        s.add( "/" );
        s.add( "*" );
        s.add( "-" );
        s.add( "+" );
    }

    private void fill(Set<String> s, char lo, char hi) {
        for (char c = lo; c <= hi; c++) {
            s.add(c + "");
        }
    }
    //defines whitespace
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


//checks for comments and skips
private void skipComment() {
    while (hasChar() && !"\n".equals(peek())) {
        advance();
    }
}


/**
 * checks if token is an id 
 * 
 * @return scanned token
 */
    private Token nextKwID() {

        int old = this.position;
        advance();

// checks for letters followed by numbers to output token as an id
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
 * Checks if next token is a number including .digit || digit
 * 
 * @return scanned token
 */
private Token nextNumber() {
    int old = this.position;

    // Consume initial digits
    while (hasChar() && digits.contains(peek())) {
        advance();
    }

    // Handle decimal point
    if (hasChar() && ".".equals(peek())) {
        advance(); // consume '.'
        while (hasChar() && digits.contains(peek())) {
            advance();
        }
    }

    String lexeme = program.substring(old, position);
    return new Token("num", lexeme);
}

/**
 * Determines if next token is an operator
 * 
 * @return the scanned token
 * 
 */
private Token nextOperator(){
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
        else if (hasChar() && letters.contains(peek())) {
            return nextKwID();
        } 
        else if (hasChar() && (digits.contains(peek()) || ".".equals(peek()))) {
            return nextNumber();
        }
        //checks for start of comment
        else if (hasChar() && "/".equals(peek())
        && position + 1 < program.length()
        && "/".equals(program.charAt(position + 1) + "")) {
        skipComment();
        return next();
        }
        //checks for next operator
        else if (hasChar() && operator.contains(peek())){
            return nextOperator();
        }
        else if (hasChar() && ".".equals(peek()) 
            && position + 1 < program.length() 
            && digits.contains(program.charAt(position + 1) + "")) {
        return nextNumber();
        }

        //	rest here!
        else {
            System.err.println("illegal character at position "+ position);
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