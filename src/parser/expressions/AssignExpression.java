package parser.expressions;

/**
 * Created by Adrian on 2015-05-14.
 */
public class AssignExpression extends Expression {
    private final String variableName;
    private Expression expression;

    public AssignExpression(String variableName) {
        this.variableName = variableName;
    }

    public AssignExpression(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
