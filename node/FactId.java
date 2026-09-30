package node;

import syntax.*;

public class FactId extends Fact {

    protected Token id;

    public FactId(int position, Token id) {
        this.position = position;
        this.id = id;
    }
}