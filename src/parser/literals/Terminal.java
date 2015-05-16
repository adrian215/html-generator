package parser.literals;

import parser.expressions.Expression;

/**
 * Created by Adrian on 2015-05-14.
 */
public class Terminal extends Expression {
    private final String value;

    public Terminal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
