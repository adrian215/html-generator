package tests;

import com.google.common.collect.Lists;
import generator.Method;
import generator.StackManager;
import generator.Variable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import parser.expressions.Expression;
import parser.expressions.implementations.*;
import parser.literals.Operator;
import parser.operations.BoolOperation;
import parser.operations.MathOperation;
import parser.operations.implementations.BoolEqual;
import parser.operations.implementations.BoolOperationExpression;
import parser.operations.implementations.Const;
import parser.operations.implementations.VariableExpression;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Adrian on 2015-05-23.
 */
public class GenerationTest {

    @Before
    public void setUp() throws Exception {
        StackManager.cleanAll();

    }

    @Test
    public void testVariableAssign() {
        //given
        final String VARIABLE_NAME = "variable";
        final String VARIABLE_VALUE = "1";
        StackManager stackManager = StackManager.getStackManager();
        Const c = new Const(VARIABLE_VALUE);
        AssignExpression assign = new AssignExpression(VARIABLE_NAME, c);
        //when
        assign.process();
        //then
        Variable variable = stackManager.getVariable(VARIABLE_NAME);
        assertEquals(VARIABLE_NAME, variable.getName());
        assertEquals(VARIABLE_VALUE, variable.getValue());
    }

    @Test
    public void testMethodDeclaration() throws Exception {
        //given
        StackManager stackManager = StackManager.getStackManager();
        List<String> params = Lists.newArrayList("param1", "param2");
        Expression statements = null;
        final String METHOD_NAME = "methodName";
        DeclarationExpression declarationExpression = new DeclarationExpression(METHOD_NAME, params, statements);
        //when
        declarationExpression.process();
        //then
        Method method = stackManager.getMethod(METHOD_NAME);
        assertEquals(METHOD_NAME, method.getName());
        assertEquals(params, method.getParams());
        assertEquals(statements, method.getStatements());
    }

    @Test
    public void testAssignVariableToOtherVariable() throws Exception {
        //given
        final String VARIABLE_NAME1 = "variable1";
        final String VARIABLE_NAME2 = "variable2";
        final String VARIABLE_VALUE = "1";
        StackManager stackManager = StackManager.getStackManager();
        stackManager.putVariable(new Variable(VARIABLE_NAME1, VARIABLE_VALUE));
        VariableExpression variableExpression = new VariableExpression(VARIABLE_NAME1);
        AssignExpression assign = new AssignExpression(VARIABLE_NAME2, variableExpression);
        //when
        assign.process();
        //then
        Variable variable = stackManager.getVariable(VARIABLE_NAME2);
        assertEquals(VARIABLE_VALUE, variable.getValue());

    }

    @Test
    public void testMethodCall() throws Exception {
        //given
        String TEST_VARIABLE_NAME = "test";
        Variable testVariable = new Variable(TEST_VARIABLE_NAME, "test");
        String METHOD_NAME = "method";
        String PARAM_NAME = "param";
        String PARAM_VALUE = "paramValue";
        List<String> paramsNames = Lists.newArrayList(PARAM_NAME);
        List<MathOperation> paramsValues = Lists.newArrayList(new Const(PARAM_VALUE));
        StackManager stackManager = StackManager.getStackManager();
        Expression statement = new Expression() {
            @Override
            public void process() {
                stackManager.putVariable(testVariable);
                assertEquals(testVariable, stackManager.getVariable(TEST_VARIABLE_NAME));
                assertEquals(PARAM_VALUE, stackManager.getVariable(PARAM_NAME).getValue());
            }
        };
        stackManager.putMethod(new Method(METHOD_NAME, paramsNames, statement));
        CallExpression callExpression = new CallExpression(METHOD_NAME, paramsValues);
        //when
        callExpression.process();
        //then
        assertNull(stackManager.getVariable(TEST_VARIABLE_NAME));
    }

    @Test
    public void testConditionalExecuted() throws Exception {
        //given
        BoolOperation condition = mock(BoolEqual.class);
        when(condition.get()).thenReturn(true);
        Expression statement = mock(Expression.class);
        ConditionalExpression expression = new ConditionalExpression(condition, statement);
        //when
        expression.process();
        //then
        verify(statement).process();

    }

    @Test
    public void testConditionalNotExecuted() throws Exception {
        //given
        BoolOperation condition = mock(BoolEqual.class);
        when(condition.get()).thenReturn(false);
        Expression statement = mock(Expression.class);
        ConditionalExpression expression = new ConditionalExpression(condition, statement);
        //when
        expression.process();
        //then
        verify(statement, never()).process();
    }

    @Test
    public void testLoopIncrease() throws Exception {
        //given
        final String START = "1";
        final String STOP = "5";
        final int EXECUTED_TIMES = 5;
        MathOperation from = new Const(START);
        MathOperation to = new Const(STOP);
        Expression statement = mock(Expression.class);
        LoopExpression loop = new LoopExpression(from, to, statement);
        //when
        loop.process();
        //then
        verify(statement, times(EXECUTED_TIMES)).process();
    }

    @Test
    public void testLoopDecrease() throws Exception {
        //given
        final String START = "5";
        final String STOP = "3";
        final int EXECUTED_TIMES = 3;
        MathOperation from = new Const(START);
        MathOperation to = new Const(STOP);
        Expression statement = mock(Expression.class);
        LoopExpression loop = new LoopExpression(from, to, statement);
        //when
        loop.process();
        //then
        verify(statement, times(EXECUTED_TIMES)).process();
    }

    @Test
    public void testBlockOfExpressions() throws Exception {
        //given
        Expression exp1 = mock(Expression.class);
        Expression exp2 = mock(Expression.class);
        List<Expression> statemetns = Lists.newArrayList(exp1, exp2);
        Expression blockExpression = new BlockExpression(statemetns);
        //when
        blockExpression.process();
        //then
        verify(exp1).process();
        verify(exp2).process();

    }
}
