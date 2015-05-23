package parser.expressions.implementations;

import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.Operation;

import java.util.List;

/**
 * Created by Adrian on 2015-05-16.
 */
public class CallExpression extends StoringContextExpression {
    private final String methodName;
    private final List<Operation> params;

    public CallExpression(String methodName, List<Operation> params) {
        this.methodName = methodName;
        this.params = params;
    }

    //TODO implement method call
    @Override
    public void call() {

    }

    public String getMethodName() {
        return methodName;
    }

    public List<Operation> getParams() {
        return params;
    }
}
