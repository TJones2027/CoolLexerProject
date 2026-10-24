package eval;

import node.*;
import syntax.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * System tests for the semantic actions of the programming language.
 */
class EvaluationTest {

    private Parser parser;
    private Environment env;


    @BeforeEach
    void setUp() {

        parser = new Parser();
        env = new Environment();
    }

    @Test
    void testAssignment() throws SyntaxException, EvalException{
        String prg = "x = 1; wr x";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(1, evaluation);
    }

    @Test
    void testEvalError() throws SyntaxException {

        String prg = "wr x";
        Node parseTree = parser.parse(prg);

        assertThrows(EvalException.class, () -> {
            parseTree.eval(env);
        });
    }

    @Test
    void testAssociation() throws SyntaxException, EvalException{
        String prg = "wr 10 - 4 - 3";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

    @Test
    void testPrecedence() throws SyntaxException, EvalException{
        String prg = "wr 6 / (10 - 8)";

        double evaluation = parser.parse(prg).eval(env);
        assertEquals(3, evaluation);
    }

        @Test
    void testNegative() throws SyntaxException, EvalException {
        assertEquals(7, parser.parse("wr 10 + -3").eval(env));
    }

    @Test
    void testNegativeVariable() throws SyntaxException, EvalException {
        assertEquals(-4, parser.parse("x = 4; wr -x").eval(env));
    }

    @Test
    void testDoubleNegative() throws SyntaxException, EvalException {
        assertEquals(3, parser.parse("wr --3").eval(env));
    }

    @Test
    void testEnvironment() throws SyntaxException, EvalException {
        assertEquals(1, parser.parse("x = 1; y = x; wr y").eval(env));
    }

    @Test
    void testReassignment() throws SyntaxException, EvalException {
        assertEquals(2, parser.parse("x = 1; x = x + 1; wr x").eval(env));
    }

    @Test
    void testMulBeforeAdd() throws SyntaxException, EvalException {
        assertEquals(14, parser.parse("wr 2 + 3 * 4").eval(env));
    }

    @Test
    void testDecimalDivision() throws SyntaxException, EvalException {
        assertEquals(3.5, parser.parse("wr 7 / 2").eval(env));
    }

    @Test
    void testComment() throws SyntaxException, EvalException {
        assertEquals(5, parser.parse("wr 5 // this is ignored").eval(env));
    }

    @Test
    void testTrailingSemicolon() {
        assertThrows(SyntaxException.class, () -> {
            parser.parse("wr 1;");
        });
    }

    @Test
    void testMissingExpression() {
        assertThrows(SyntaxException.class, () -> {
            parser.parse("x = ; wr 1");
        });
    }
}
