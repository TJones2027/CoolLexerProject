package syntax;

import node.*;

/**
 * A recursive descent parser
 */
public class Parser {

    private Lexer lexer;
    private Token lookahead;

    /**
     * Parse an input program and return a Node that is the root of the
     * resulting parse tree.
     *
     * @param program String to be scanned and parsed
     * @return the Root Node of a parse tree
     * @throws SyntaxException - If there is a syntax error
     */
    public Node parse(String program) throws SyntaxException {
        lexer = new Lexer(program);
        lookahead = lexer.next(); //"prime the pump"
        Expr expr = parseExpr();
        match("EOF");
        return expr;
    }

    /**
     * TODO
     *
     * @return
     * @throws SyntaxException
     */
    private Expr parseExpr() throws SyntaxException {

        Term term = parseTerm();
        Addop addop = parseAddop();

        if (addop == null) {
            return new Expr(term);
        } else {
            Expr expr=parseExpr();
            expr.append(new Expr(term,addop,null));
            return expr;
        }
    }

    /**
     * TODO
     * @return
     * @throws SyntaxException
     */
 private Term parseTerm() throws SyntaxException {

    Fact fact = parseFact();
    Mulop mulop = parseMulop();

    if (mulop == null) {
        return new Term(fact);
    } else {
        Term term = parseTerm();
        term.append(new Term(fact, mulop, null));
        return term;
    }
}

    /**
     * TODO
     * @return
     * @throws SyntaxException
     */
private Fact parseFact() throws SyntaxException {

    Token current = lookahead;

    if (current.equalType(new Token("id"))) {
        match("id");
        return new FactId(lexer.getPosition(), current);

    } else if (current.equalType(new Token("num"))) {
        match("num");
        return new FactNum(lexer.getPosition(), current);

    } else {
        match("(");
        Expr expr = parseExpr();
        match(")");
        return new FactExpr(lexer.getPosition(), expr);
    }
}

    /**
     * Parses an Addop nonterminal and returns it.
     * @return a Node that represent an addop
     * @throws SyntaxException if an invalid terminal is discovered
     */
    private Addop parseAddop() throws SyntaxException {
        Token addop = lookahead;
        if (addop.equalType("+")) {
            match("+");
            return new Addop(lexer.getPosition(), addop);

        } else if (addop.equalType("-")) {
            match("-");
            return new Addop(lexer.getPosition(), addop);

        } else {
            return null;
        }
    }

    /**
     * TODO
     * @return
     * @throws SyntaxException
     */
private Mulop parseMulop() throws SyntaxException {
    Token mulop = lookahead;
    if (mulop.equalType("*")) {
        match("*");
        return new Mulop(lexer.getPosition(), mulop);

    } else if (mulop.equalType("/")) {
        match("/");
        return new Mulop(lexer.getPosition(), mulop);

    } else {
        return null;
    }
}

    private void match(String s) throws SyntaxException {
        if (lookahead.equalType(s)) {
            lookahead = lexer.next();
        } else {
            throw new SyntaxException(lexer.getPosition(), new Token(s), lookahead);
        }
    }
}
