package parser.expressions.implementations;

import parser.exceptions.GenerationException;
import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.BoolOperation;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class ConditionalExpression extends StoringContextExpression {
    private final BoolOperation condition;
    private final Expression statements;

    public ConditionalExpression(BoolOperation condition, Expression statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    protected void call() throws GenerationException {
        if (condition.get()) {
            statements.process();
        }
    }
}
