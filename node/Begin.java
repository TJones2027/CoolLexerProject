package node;

import eval.Environment;
import eval.EvalException;

public class Begin extends Stmt {

    protected Block block;

    public Begin(int position, Block block) {
        this.position = position;
        this.block = block;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return block.eval(env);
    }
}