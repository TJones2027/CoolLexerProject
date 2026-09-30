package node;

public class FactExpr extends Fact {

    protected Expr expr;

    public FactExpr(int position, Expr expr) {
        this.position = position;
        this.expr = expr;
    }
}