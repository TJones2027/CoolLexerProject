package node;

import eval.Environment;
import eval.EvalException;

public class Wr extends Stmt {

    protected Expr expr;

    public Wr(int position, Expr expr) {
        this.position = position;
        this.expr = expr;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        double result = expr.eval(env);
        System.out.println(result);
        return result;
    }
}