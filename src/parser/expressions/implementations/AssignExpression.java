package parser.expressions.implementations;

import generator.Variable;
import parser.expressions.Expression;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-14.
 */
public class AssignExpression extends Expression {
    private final String variableName;
    private MathOperation expression;

    public AssignExpression(String variableName, MathOperation expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void process() {
        String variableValue = expression.get();
        Variable variable = new Variable(variableName, variableValue);
        stackManager.putVariable(variable);
    }
}
