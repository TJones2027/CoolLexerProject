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
        Block block = parseBlock();
        match("EOF");
        return block;
    }

    /**     *
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
     * Parses a Term nonterminal and returns it.
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
     * Parses a Fact nonterminal and returns it
     * @return
     * @throws SyntaxException
     */
private Fact parseFact() throws SyntaxException {

    Token current = lookahead;

    if (current.equalType(new Token("id"))) {
        match("id");
        return new FactId(lexer.getPosition(), current);

    }
    else if (current.equalType(new Token("num"))) {
        match("num");
        return new FactNum(lexer.getPosition(), current);

    }
    else if (current.equalType(new Token("-"))) {
        match("-");
        Fact fact = parseFact();
        return new FactNeg(lexer.getPosition(), fact);

    } 
    else{
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

        }
        else if (addop.equalType("-")) {
            match("-");
            return new Addop(lexer.getPosition(), addop);

        }
        else {
            return null;
        }
    }

    /**
     * Parses a Mulop nonterminal and returns it.
     * @return
     * @throws SyntaxException
     */
private Mulop parseMulop() throws SyntaxException {
    Token mulop = lookahead;
    if (mulop.equalType("*")) {
        match("*");
        return new Mulop(lexer.getPosition(), mulop);

    }
    else if (mulop.equalType("/")) {
        match("/");
        return new Mulop(lexer.getPosition(), mulop);

    }
        else {
            return null;
        }
    }

    private void match(String s) throws SyntaxException {
        if (lookahead.equalType(s)) {
            lookahead = lexer.next();
        }
        else {
            throw new SyntaxException(lexer.getPosition(), new Token(s), lookahead);
        }
    }

    /**
    * Parses a Block nonterminal: stmt ';' block | stmt
    * @return a Block node
    * @throws SyntaxException if an invalid terminal is discovered
    */
    private Block parseBlock() throws SyntaxException {
    Stmt stmt = parseStmt();

    if (lookahead.equalType(";")) {
        match(";");
        Block block = parseBlock();
        return new Block(lexer.getPosition(), stmt, block);
    }
    else{
            return new Block(lexer.getPosition(), stmt);
        }
    }

/**
 * Parses a Stmt nonterminal: assn | 'wr' expr
 * @return an Assn or Wr node
 * @throws SyntaxException if an invalid terminal is discovered
 */
private Stmt parseStmt() throws SyntaxException {
    if (lookahead.equalType("id")) {
        return parseAssn();
    } 
    else{
        return parseWr();   // match("wr") throws if this isn't a wr
    }
}

    /**
    * Parses an Assn nonterminal: id '=' expr
    * @return an Assn node
    * @throws SyntaxException if an invalid terminal is discovered
    */
    private Assn parseAssn() throws SyntaxException {
        Token id = lookahead;
        match("id");
        match("=");
        Expr expr = parseExpr();
        return new Assn(lexer.getPosition(), id, expr);
}

    /**
    * Parses a Wr statement: 'wr' expr
    * @return a Wr node
    * @throws SyntaxException if an invalid terminal is discovered
    */
    private Wr parseWr() throws SyntaxException {
    match("wr");
    Expr expr = parseExpr();
    return new Wr(lexer.getPosition(), expr);
    }
}
