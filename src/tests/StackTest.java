package tests;

import com.google.common.collect.Lists;
import generator.Method;
import generator.StackManager;
import generator.Variable;
import org.junit.Test;
import parser.expressions.Expression;
import parser.expressions.implementations.BlockExpression;
import parser.expressions.implementations.CallExpression;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Adrian on 2015-05-23.
 */
public class StackTest {

    @Test
    public void testStackVariablePush() {
        //given
        final String VARIABLE_NAME = "variable";
        StackManager stackManager = StackManager.getStackManager();
        Variable variable = new Variable(VARIABLE_NAME, "1");
        //when
        stackManager.putVariable(variable);
        Variable gotVariable = stackManager.getVariable(VARIABLE_NAME);
        //then
        assertEquals(variable, gotVariable);
    }

    @Test
    public void testStackVariablePushWithContextStore() throws Exception {
        //given
        final String VARIABLE1_NAME = "variable1";
        final String VARIABLE1_VALUE = "1";
        final String VARIABLE2_NAME = "variable2";
        final String VARIABLE2_VALUE = "2";
        Variable variable1 = new Variable(VARIABLE1_NAME, VARIABLE1_VALUE);
        Variable variable2 = new Variable(VARIABLE2_NAME, VARIABLE2_VALUE);
        StackManager stackManager = StackManager.getStackManager();
        //when
        stackManager.putVariable(variable1);
        stackManager.pushCurrent();
        stackManager.putVariable(variable2);
        stackManager.popCurrent();
        //then
        assertEquals(variable1, stackManager.getVariable(VARIABLE1_NAME));
        assertNull(stackManager.getVariable(VARIABLE2_NAME));
    }

    @Test
    public void testStackMethodPush() {
        //given
        final String METHOD_NAME = "methodName";
        final List<String> params = Lists.newArrayList("param1", "param2");
        final Expression statement = null;
        StackManager stackManager = StackManager.getStackManager();
        Method method = new Method(METHOD_NAME, params, statement);
        //when
        stackManager.putMethod(method);
        Method gotMethod = stackManager.getMethod(METHOD_NAME);
        //then
        assertEquals(method, gotMethod);
    }

    @Test
    public void testStackMethodPushWithContextStore() throws Exception {
        //given
        final String METHOD1_NAME = "methodName1";
        final String METHOD2_NAME = "methodName2";
        final List<String> params = Lists.newArrayList("param1", "param2");
        final Expression statement = null;
        StackManager stackManager = StackManager.getStackManager();
        Method method1 = new Method(METHOD1_NAME, params, statement);
        Method method2 = new Method(METHOD2_NAME, params, statement);
        //when
        stackManager.putMethod(method1);
        stackManager.pushCurrent();
        stackManager.putMethod(method2);
        stackManager.popCurrent();
        //then
        assertEquals(method1, stackManager.getMethod(METHOD1_NAME));
        assertNull(stackManager.getMethod(METHOD2_NAME));
    }
}
