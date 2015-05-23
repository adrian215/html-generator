package parser.expressions.implementations;

import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class LoopExpression extends StoringContextExpression {
    private final Operation from;
    private final Operation to;
    private final Expression statements;

    public LoopExpression(Operation from, Operation to, Expression statements) {
        this.from = from;
        this.to = to;
        this.statements = statements;
    }

    //TODO implement loop call
    @Override
    protected void call() {

    }

    public Operation getFrom() {
        return from;
    }

    public Operation getTo() {
        return to;
    }

    public Expression getStatements() {
        return statements;
    }
}
