package parser.operations.implementations;

import generator.StackManager;
import generator.Variable;
import parser.expressions.Expression;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class VariableExpression implements MathOperation {
    private final String name;
    private final StackManager stackManager = StackManager.getStackManager();

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public String get() {
        return returnVariableValue(stackManager.getVariable(name));
    }

    private String returnVariableValue(Variable variable) {
        return variable.getValue();
    }

}
