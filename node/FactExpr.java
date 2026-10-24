package node;

import eval.Environment;
import eval.EvalException;
import syntax.*;

public class FactExpr extends Fact {

    protected Expr expr;
    
    @Override
    public double eval(Environment env) throws EvalException {
    return expr.eval(env);
}

    public FactExpr(int position, Expr expr) {
        this.position = position;
        this.expr = expr;
    }
}