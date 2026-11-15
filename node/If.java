package node;

import eval.Environment;
import eval.EvalException;

public class If extends Stmt {

    protected BoolExpr boolExpr;
    protected Stmt thenStmt;
    protected Stmt elseStmt;

    public If(int position, BoolExpr boolExpr, Stmt thenStmt, Stmt elseStmt) {
        this.position = position;
        this.boolExpr = boolExpr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public If(int position, BoolExpr boolExpr, Stmt thenStmt) {
        this(position, boolExpr, thenStmt, null);
    }

    @Override
    public double eval(Environment env) throws EvalException {
        if (boolExpr.eval(env) != 0.0) {
            return thenStmt.eval(env);
        } 
        else if (elseStmt != null) {
            return elseStmt.eval(env);
        }
        return 0.0;
    }
}