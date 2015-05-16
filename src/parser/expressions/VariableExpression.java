package parser.expressions;

/**
 * Created by Adrian on 2015-05-16.
 */
public class VariableExpression extends Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
