package eval;

import java.util.HashMap;
import java.util.Map;

/**
 * A referencing environment for bindings
 */
public class Environment {

    private Map<String, Double> vars = new HashMap<>();

    /**
     * Stores a value for a variable
     *
     * @param var the variable name
     * @param val the value to store
     * @return the value that was stored
     */
    public double put(String var, double val) {
        vars.put(var, val);
        return val;
    }

    /**
     * Looks up the value of a variable.
     *
     * @param pos the position in the program, used for error reporting
     * @param var the variable name
     * @return the value stored for the variable
     * @throws EvalException if the variable has not been assigned
     */
    public double get(int pos, String var) throws EvalException {
        if (!vars.containsKey(var)) {
            throw new EvalException(pos, "undefined variable: " + var);
        }
        return vars.get(var);
    }
}