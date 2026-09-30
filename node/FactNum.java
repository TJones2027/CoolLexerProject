package node;

import syntax.*;

public class FactNum extends Fact {

    protected Token num;

    public FactNum(int position, Token num) {
        this.position = position;
        this.num = num;
    }
}