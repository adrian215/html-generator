package parser.expressions;

/**
 * Created by Adrian on 2015-05-14.
 */
public class TextExpression extends Expression {
    private final String value;

    public TextExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
