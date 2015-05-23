package parser.operations.implementations;

import parser.expressions.Expression;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class VariableExpression implements MathOperation {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    //TODO implement variable call
    @Override
    public String get() {
        return null;
    }

    public String getName() {
        return name;
    }
}
