package node;

import syntax.*;

public class Addop extends Node {

    protected Token addop;

    public Addop(int position, Token addop) {
        this.position = position;
        this.addop = addop;
    }

    public double compute(double op1, double op2) {
        if (addop.getLexeme().equals("+")) {
            return op1 + op2;
        }
        else{
            return op1 - op2;
        }
    }
}