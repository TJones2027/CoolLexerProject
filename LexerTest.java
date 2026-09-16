package syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for syntax.Lexer
 *
 * Uses Junit5.
 */
public class LexerTest {

    /**
     * Simply creates a 'program' that has only one token. When scanned,
     * the test checks to see that the current token is of the correct type.
     *
     * Try more than one Token in a different test case!
     * @throws SyntaxException - This suppresses the need for a try/catch block.
     */
    @Test
    public void test() throws SyntaxException{

        String prg = "4";
        Lexer lexer = new Lexer(prg);
        assertEquals(new Token("num", "4"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }


    /**
     * Tests that the lexer can recognize an identifier
     *
     * @throws SyntaxException
     */
    @Test
    public void testOneIdentifier() throws SyntaxException{

        String prg = "x";
        Lexer lexer = new Lexer(prg);
        assertEquals(new Token("id", "x"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }


    /**
     * Tests that the lexer can recognize an operator (the semicolon)
     * @throws SyntaxException
     */
    @Test
    public void testOneOperator() throws SyntaxException{

        String prg = ";";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token(";", ";"), lexer.next());

        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * @throws SyntaxException
     * 
     */
    @Test
    public void testMultiDigit() throws SyntaxException{

        String prg = "42";
        Lexer lexer = new Lexer(prg);

        assertEquals(new Token("num", "42"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests multiple different tokens in one program
     * @throws SyntaxException
     */
    @Test
    public void testMultipleTokens() throws SyntaxException {
        Lexer lexer = new Lexer("x = 5");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("num", "5"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests a fuller expression with parens and multiple operators
     * @throws SyntaxException
     */
    @Test
    public void testExpression() throws SyntaxException {
        Lexer lexer = new Lexer("(x+y)*2");
        assertEquals(new Token("(", "("), lexer.next());
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("+", "+"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token(")", ")"), lexer.next());
        assertEquals(new Token("*", "*"), lexer.next());
        assertEquals(new Token("num", "2"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that a newline between tokens is treated as whitespace
     * @throws SyntaxException
     */
    @Test
    public void testNewlineBetweenTokens() throws SyntaxException {
        Lexer lexer = new Lexer("x\ny");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that all operators from the spec are recognized
     * @throws SyntaxException
     */
    @Test
    public void testAllOperators() throws SyntaxException {
        Lexer lexer = new Lexer("+ - * / ; = ( )");
        assertEquals(new Token("+", "+"), lexer.next());
        assertEquals(new Token("-", "-"), lexer.next());
        assertEquals(new Token("*", "*"), lexer.next());
        assertEquals(new Token("/", "/"), lexer.next());
        assertEquals(new Token(";", ";"), lexer.next());
        assertEquals(new Token("=", "="), lexer.next());
        assertEquals(new Token("(", "("), lexer.next());
        assertEquals(new Token(")", ")"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that a negative-looking number is scanned as two separate tokens
     * @throws SyntaxException
     */
    @Test
    public void testNegativeIsTwoTokens() throws SyntaxException {
        Lexer lexer = new Lexer("-2.0");
        assertEquals(new Token("-", "-"), lexer.next());
        assertEquals(new Token("num", "2.0"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that an illegal character is reported and skipped, not returned as a token
     * @throws SyntaxException
     */
    @Test
    public void testIllegalCharacterSkipped() throws SyntaxException {
        Lexer lexer = new Lexer("@x");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that identifiers can contain embedded digits (not just start with letters)
     * @throws SyntaxException
     */
    @Test
    public void testIdWithDigits() throws SyntaxException {
        Lexer lexer = new Lexer("move2Up");
        assertEquals(new Token("id", "move2Up"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests a number starting with a leading dot, e.g. ".1"
     * @throws SyntaxException
     */
    @Test
    public void testLeadingDot() throws SyntaxException {
        Lexer lexer = new Lexer(".1");
        assertEquals(new Token("num", ".1"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests a number with a trailing dot, e.g. "1."
     * @throws SyntaxException
     */
    @Test
    public void testTrailingDot() throws SyntaxException {
        Lexer lexer = new Lexer("1.");
        assertEquals(new Token("num", "1."), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that a number with two dots is scanned as two separate num tokens
     * @throws SyntaxException
     */
    @Test
    public void testDoubleDotSplitsIntoTwoNums() throws SyntaxException {
        Lexer lexer = new Lexer("2.0.3");
        assertEquals(new Token("num", "2.0"), lexer.next());
        assertEquals(new Token("num", ".3"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that a comment is skipped and the token after it is still recognized
     * @throws SyntaxException
     */
    @Test
    public void testCommentSkippedBeforeNewline() throws SyntaxException {
        Lexer lexer = new Lexer("x // this is a comment\ny");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("id", "y"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests a comment that runs to EOF with no trailing newline
     * @throws SyntaxException
     */
    @Test
    public void testCommentAtEOFNoTrailingNewline() throws SyntaxException {
        Lexer lexer = new Lexer("x // trailing comment");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests an empty comment
     * @throws SyntaxException
     */
    @Test
    public void testEmptyComment() throws SyntaxException {
        Lexer lexer = new Lexer("//\nx");
        assertEquals(new Token("id", "x"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }

    /**
     * Tests that a single slash (not a double slash) is still scanned as division
     * @throws SyntaxException
     */
    @Test
    public void testLoneSlashIsDivision() throws SyntaxException {
        Lexer lexer = new Lexer("6/2");
        assertEquals(new Token("num", "6"), lexer.next());
        assertEquals(new Token("/", "/"), lexer.next());
        assertEquals(new Token("num", "2"), lexer.next());
        assertEquals(new Token("EOF", "EOF"), lexer.next());
    }
}