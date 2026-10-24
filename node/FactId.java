package node;

import eval.Environment;
import eval.EvalException;
import syntax.*;


public class FactId extends Fact {

    protected Token id;

    @Override
    public double eval(Environment env) throws EvalException {
    return env.get(this.position, id.getLexeme());
}

    public FactId(int position, Token id) {
        this.position = position;
        this.id = id;
    }
}