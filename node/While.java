package node;

import eval.Environment;
import eval.EvalException;

public class While extends Stmt {

    protected BoolExpr boolExpr;
    protected Stmt stmt;

        public While(int position, BoolExpr boolExpr, Stmt Stmt) {
        this.position = position;
        this.boolExpr = boolExpr;
        this.stmt = Stmt;
    }

        @Override
    public double eval(Environment env) throws EvalException {
        double result = 0.0;
        while (boolExpr.eval(env) != 0.0) {
            result = stmt.eval(env);
        }
        return result;
    }
}