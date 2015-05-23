package parser.expressions.implementations;

import parser.expressions.Expression;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-14.
 */
public class AssignExpression implements Expression {
    private final String variableName;
    private Operation expression;

    public AssignExpression(String variableName) {
        this.variableName = variableName;
    }

    public AssignExpression(String variableName, Operation expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    //TODO implement assign to variable
    @Override
    public void process() {

    }

    public String getVariableName() {
        return variableName;
    }

    public Operation getExpression() {
        return expression;
    }

    public void setExpression(Operation expression) {
        this.expression = expression;
    }
}
