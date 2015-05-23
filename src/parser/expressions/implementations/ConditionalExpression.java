package parser.expressions.implementations;

import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class ConditionalExpression extends StoringContextExpression {
    private final Operation condition;
    private final Expression statements;

    public ConditionalExpression(Operation condition, Expression statements) {
        this.condition = condition;
        this.statements = statements;
    }

    //TODO implement conditional call
    @Override
    public void call() {

    }

    public Operation getCondition() {
        return condition;
    }

    public Expression getStatements() {
        return statements;
    }
}
