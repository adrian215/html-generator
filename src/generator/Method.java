package generator;

import parser.expressions.Expression;

import java.util.List;

/**
 * Created by Adrian on 2015-05-23.
 */
public class Method {
    private final String name;
    private final List<String> params;
    private final Expression statements;

    public Method(String name, List<String> params, Expression statements) {
        this.name = name;
        this.params = params;
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public List<String> getParams() {
        return params;
    }

    public Expression getStatements() {
        return statements;
    }
}
