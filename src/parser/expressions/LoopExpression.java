package parser.expressions;

/**
 * Created by Adrian on 2015-05-16.
 */
public class LoopExpression extends Expression {
    private final Expression from;
    private final Expression to;
    private final Expression statements;

    public LoopExpression(Expression from, Expression to, Expression statements) {
        this.from = from;
        this.to = to;
        this.statements = statements;
    }

    public Expression getFrom() {
        return from;
    }

    public Expression getTo() {
        return to;
    }

    public Expression getStatements() {
        return statements;
    }
}
