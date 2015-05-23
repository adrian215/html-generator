package parser.operations.implementations;

import parser.expressions.Expression;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-14.
 */
public class Const implements MathOperation {
    private final String value;

    public Const(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

}
