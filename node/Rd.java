package node;

import eval.Environment;
import eval.EvalException;
import syntax.*;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Rd extends Stmt {

    protected Token id;

    private static Scanner scanner;
    private static InputStream source;

    // One Scanner shared by every Rd node
    private static Scanner getScanner() {
        if (scanner == null || source != System.in) {
            source = System.in;
            scanner = new Scanner(source);
        }
        return scanner;
    }

    public Rd(int position, Token id) {
        this.position = position;
        this.id = id;
    }

    @Override
    public double eval(Environment env) throws EvalException {
        try {
            double value = getScanner().nextDouble();
            return env.put(id.getLexeme(), value);
        } catch (NoSuchElementException e) {
            throw new EvalException(position, "rd: no valid number to read");
        }
    }
}