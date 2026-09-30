package syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * To assist you in creating correct parse trees.
 */
class ParserTest {

    @Test
    void testIdentifier() throws SyntaxException {
        Parser parser = new Parser();
        String prg = "x";

        System.out.println(parser.parse(prg).toString());
    }


    @Test
    void testAddition() throws SyntaxException {
        Parser parser = new Parser();
        String prg = "x + 3";

        System.out.println(parser.parse(prg).toString());
    }

    @Test
void testSyntax_1() {

        Parser parser = new Parser();
        String prg = "x * *";

        assertThrows(SyntaxException.class, () -> {
            parser.parse(prg);
        });
    }
    @Test
void testParens() throws SyntaxException {
    Parser parser = new Parser();
    String prg = "((x))";

    System.out.println(parser.parse(prg).toString());
}

@Test
void testAddMulPrecedence() throws SyntaxException {
    Parser parser = new Parser();
    String prg = "x + 1 / 2";

    System.out.println(parser.parse(prg).toString());
}

@Test
void testSubtractionChain() throws SyntaxException {
    Parser parser = new Parser();
    String prg = "10 - 4 - 3";

    // Matches the third sample tree from the assignment sheet exactly
    System.out.println(parser.parse(prg).toString());
}

@Test
void testSyntax_missingOperand() {
    Parser parser = new Parser();
    String prg = "x +";

    assertThrows(SyntaxException.class, () -> {
        parser.parse(prg);
    });
}
}