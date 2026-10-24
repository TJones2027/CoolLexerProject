package node;

import eval.Environment;
import eval.EvalException;

public class Block extends Node {

    protected Stmt stmt;
    protected Block block;

    public Block(int position, Stmt stmt, Block block) {
        this.position = position;
        this.stmt = stmt;
        this.block = block;
    }

    public Block(int position, Stmt stmt) {
        this(position, stmt, null);
    }

    @Override
    public double eval(Environment env) throws EvalException {
        double result = stmt.eval(env);
        if (block == null) {
            return result;
        }
        return block.eval(env);
    }
}