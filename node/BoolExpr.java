package node;

import eval.Environment;
import eval.EvalException;

public class BoolExpr extends Node {

    protected Expr left;
    protected Relop relop;
    protected Expr right;

    public BoolExpr(int position, Expr left, Relop relop, Expr right) {
        this.position = position;
        this.left = left;
        this.relop = relop;
        this.right = right;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return relop.compute(left.eval(env), right.eval(env));
    }
}