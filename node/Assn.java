package node;

import eval.Environment;
import eval.EvalException;
import syntax.*;

public class Assn extends Stmt {

    protected Token id;
    protected Expr expr;

    public Assn(int position, Token id, Expr expr) {
        this.position = position;
        this.id = id;
        this.expr = expr;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return env.put(id.getLexeme(), expr.eval(env));
    }
}