package parser.expressions;

/**
 * Created by Adrian on 2015-05-16.
 */
public class ConditionalExpression extends Expression {
    private final Expression condition;
    private final Expression statements;

    public ConditionalExpression(Expression condition, Expression statements) {
        this.condition = condition;
        this.statements = statements;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getStatements() {
        return statements;
    }
}
