package eval;

import node.*;
import syntax.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
        @Test
    void testRd() throws SyntaxException, EvalException {
        String input = "4.1\n4.2";
        InputStream io = new ByteArrayInputStream(input.getBytes());
        System.setIn(io);

        double evaluation = parser.parse("rd x; rd y; wr y + x").eval(env);
        assertEquals(8.3, evaluation);
    }

    @Test
    void testIfThen() throws SyntaxException, EvalException {
        assertEquals(5, parser.parse("x = 1; if x < 2 then x = 5; wr x").eval(env));
    }

    @Test
    void testIfElse() throws SyntaxException, EvalException {
        assertEquals(9, parser.parse("if 3 > 4 then wr 1 else wr 9").eval(env));
    }

    @Test
    void testIfFalseNoElse() throws SyntaxException, EvalException {
        assertEquals(1, parser.parse("x = 1; if x > 2 then x = 7; wr x").eval(env));
    }

    @Test
    void testWhile() throws SyntaxException, EvalException {
        assertEquals(10, parser.parse("i = 0; while i < 10 do i = i + 1; wr i").eval(env));
    }

    @Test
    void testBeginEnd() throws SyntaxException, EvalException {
        assertEquals(6, parser.parse("x = 0; i = 1; while i <= 3 do begin x = x + i; i = i + 1 end; wr x").eval(env));
    }

    @Test
    void testRelops() throws SyntaxException, EvalException {
        assertEquals(1, parser.parse("if 2 <> 3 then wr 1 else wr 0").eval(env));
        assertEquals(1, parser.parse("if 3 == 3 then wr 1 else wr 0").eval(env));
        assertEquals(0, parser.parse("if 3 >= 4 then wr 1 else wr 0").eval(env));
        assertEquals(1, parser.parse("if 3 <= 3 then wr 1 else wr 0").eval(env));
        assertEquals(1, parser.parse("if 3 < 4 then wr 1 else wr 0").eval(env));
        assertEquals(0, parser.parse("if 3 > 4 then wr 1 else wr 0").eval(env));
    }

    @Test
    void testGcd() throws SyntaxException, EvalException {
        System.setIn(new ByteArrayInputStream("25\n15".getBytes()));
        String prg = "rd a; rd b; while a<>b do if a>b then a=a-b else b=b-a; wr a";
        assertEquals(5, parser.parse(prg).eval(env));
    }

    @Test
    void testFibonacci() throws SyntaxException, EvalException {
        System.setIn(new ByteArrayInputStream("10".getBytes()));
        String prg = "rd n; if n <= 2 then wr 1 else begin a = 1; b = 1; i = 3; "
                + "while i <= n do begin temp = a; a = a + b; b = temp; i = i + 1 end; wr a end";
        assertEquals(55, parser.parse(prg).eval(env));
    }

    @Test
    void testIfMissingThen() {
        assertThrows(SyntaxException.class, () -> {
            parser.parse("if 1 < 2 wr 1");
        });
    }

    @Test
    void testMissingRelop() {
        assertThrows(SyntaxException.class, () -> {
            parser.parse("if 1 then wr 1");
        });
    }

    @Test
    void testUnclosedBegin() {
        assertThrows(SyntaxException.class, () -> {
            parser.parse("begin wr 1");
    });
}
}
