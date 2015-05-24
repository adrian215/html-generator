package parser.expressions.implementations;

import generator.Method;
import parser.exceptions.GenerationException;
import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.MathOperation;
import parser.operations.Operation;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Adrian on 2015-05-16.
 */
public class CallExpression extends StoringContextExpression {
    private final String methodName;
    private final List<MathOperation> params;

    public CallExpression(String methodName, List<MathOperation> params) {
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    protected void call() throws GenerationException {
        Method method = stackManager.getMethod(methodName);
        initializeParamsVariables(method);
        method.getStatements().process();

    }

    private void initializeParamsVariables(Method method) throws GenerationException {
        Iterator<String> paramsNamesIterator = method.getParams().iterator();
        for (MathOperation param : params) {
            String paramName = paramsNamesIterator.next();
            createLocalParamVariable(param, paramName);
        }
    }

    private void createLocalParamVariable(MathOperation param, String paramName) throws GenerationException {
        new AssignExpression(paramName, param).process();
    }
}
