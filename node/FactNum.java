package node;

import eval.Environment;
import eval.EvalException;
import syntax.*;

public class FactNum extends Fact {

    protected Token num;

    public FactNum(int position, Token num) {
        this.position = position;
        this.num = num;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        return Double.parseDouble(num.getLexeme());
    }
}