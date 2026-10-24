package node;

import eval.Environment;
import eval.EvalException;

public class FactNeg extends Fact {

    protected Fact fact;

    public FactNeg(int position, Fact fact) {
        this.position = position;
        this.fact = fact;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return -fact.eval(env);
    }
}