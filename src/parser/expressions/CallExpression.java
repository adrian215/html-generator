package parser.expressions;

import java.util.List;

/**
 * Created by Adrian on 2015-05-16.
 */
public class CallExpression extends Expression {
    private final String methodName;
    private final List<Expression> params;

    public CallExpression(String methodName, List<Expression> params) {
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Expression> getParams() {
        return params;
    }
}
