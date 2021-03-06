package parser.expressions.implementations;

import parser.exceptions.GenerationException;
import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;

import java.util.List;

/**
 * Created by Adrian on 2015-05-14.
 */
public class BlockExpression extends StoringContextExpression {
    private final List<Expression> expressions;

    public BlockExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    protected void call() throws GenerationException {
        for (Expression expression : expressions) {
            expression.process();
        }
    }
}
