package generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Adrian on 2015-05-23.
 */
public class StackManager {
    private static StackManager stackManager;

    private Map<String, Variable> variables = new HashMap<>();
    private Map<String, Method> methods = new HashMap<>();
    private Stack<Map<String, Variable>> variableStack = new Stack<>();
    private Stack<Map<String, Method>> methodStack = new Stack<>();

    public static StackManager getStackManager() {
        if (stackManager == null) {
            stackManager = new StackManager();
        }
        return stackManager;
    }

    public void putVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    public Variable getVariable(String variableName) {
        return variables.get(variableName);
    }

    public void putMethod(Method method) {
        methods.put(method.getName(), method);
    }

    public Method getMethod(String methodName) {
        return methods.get(methodName);
    }

    public void pushCurrent(){
        Map<String, Variable> variableStackCopy = new HashMap<>(variables);
        Map<String, Method> methodStackCopy = new HashMap<>(methods);
        variableStack.push(variableStackCopy);
        methodStack.push(methodStackCopy);
    }

    public void popCurrent() {
        if (!variableStack.isEmpty() && !methodStack.isEmpty()) {
            variables = variableStack.pop();
            methods = methodStack.pop();
        }
    }
}
