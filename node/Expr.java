package node;

import eval.Environment;
import eval.EvalException;


public class Expr extends Node {

    protected Term term;
    protected Addop addop;
    protected Expr expr;

    public Expr(Term term, Addop addop, Expr expr) {
        this.term = term;
        this.addop = addop;
        this.expr = expr;
    }

    public Expr(Term term) {
        this.term = term;
        this.addop = null;
        this.expr = null;
    }

    public void append(Expr expr) {
        if (this.expr == null) 
        {
            this.addop = expr.addop;
            this.expr = expr;
            expr.addop = null;
        } 
        else{
            this.expr.append(expr);
        }
    }

    @Override
    public double eval(Environment env) throws EvalException {
        if (expr == null) {
            return term.eval(env);
        }
        return addop.compute(expr.eval(env), term.eval(env));
    }

}