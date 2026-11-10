package node;

import syntax.*;

public class Relop extends Node {

    protected Token relop;

    public Relop(int position, Token relop) {
        this.position = position;
        this.relop = relop;
    }

    public double compute(double op1, double op2) {
        String op = relop.getLexeme();

        if (op.equals(">")) {
            return op1 > op2 ? 1.0 : 0.0;
        } 
        else if (op.equals(">=")) {
            return op1 >= op2 ? 1.0 : 0.0;
        } 
        else if (op.equals("<")) {
            return op1 < op2 ? 1.0 : 0.0;
        } 
        else if (op.equals("<=")) {
            return op1 <= op2 ? 1.0 : 0.0;
        } 
        else if (op.equals("<>")) {
            return op1 != op2 ? 1.0 : 0.0;
        } 
        else {
            return op1 == op2 ? 1.0 : 0.0; 
        }
    }
}