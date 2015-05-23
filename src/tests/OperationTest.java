package tests;

import com.google.common.collect.Lists;
import org.junit.Test;
import parser.literals.Operator;
import parser.operations.BoolOperation;
import parser.operations.MathOperation;
import parser.operations.implementations.BoolEqual;
import parser.operations.implementations.BoolOperationExpression;
import parser.operations.implementations.Const;
import parser.operations.implementations.OperationExpression;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Adrian on 2015-05-23.
 */
public class OperationTest {

    @Test
    public void testConstValue() {
        //given
        String VALUE = "3";
        MathOperation c = new Const(VALUE);
        //when
        String result = c.get();
        //then
        assertEquals(VALUE, result);
    }

    @Test
    public void testSimpleMathNoArgsOperation() throws Exception {
        //given
        String VALUE = "3";
        MathOperation c = new Const(VALUE);
        MathOperation operation = new OperationExpression(c, new ArrayList<>(), new ArrayList<>());
        //when
        String result = operation.get();
        //then
        assertEquals(VALUE, result);
    }

    @Test
    public void testTwoNumbersAddOperation() throws Exception {
        //given
        String VALUE1 = "1";
        String VALUE2 = "2";
        String VALUE_RESULT = "3";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        List<MathOperation> arguments = new ArrayList<>();
        arguments.add(n2);
        List<Operator> operators = new ArrayList<>();
        operators.add(Operator.PLUS);
        MathOperation result = new OperationExpression(n1, operators, arguments);
        //when
        String resultValue = result.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testTwoNumbersSubtractOperation() throws Exception {
        //given
        String VALUE1 = "1";
        String VALUE2 = "2";
        String VALUE_RESULT = "-1";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        List<MathOperation> arguments = new ArrayList<>();
        arguments.add(n2);
        List<Operator> operators = new ArrayList<>();
        operators.add(Operator.MINUS);
        MathOperation result = new OperationExpression(n1, operators, arguments);
        //when
        String resultValue = result.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testTwoNumbersMultiplyOperation() throws Exception {
        //given
        String VALUE1 = "2";
        String VALUE2 = "2";
        String VALUE_RESULT = "4";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        List<MathOperation> arguments = new ArrayList<>();
        arguments.add(n2);
        List<Operator> operators = new ArrayList<>();
        operators.add(Operator.MULTIPLICATION);
        MathOperation result = new OperationExpression(n1, operators, arguments);
        //when
        String resultValue = result.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testTwoNumbersDivideOperation() throws Exception {
        //given
        String VALUE1 = "4";
        String VALUE2 = "2";
        String VALUE_RESULT = "2";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        List<MathOperation> arguments = new ArrayList<>();
        arguments.add(n2);
        List<Operator> operators = new ArrayList<>();
        operators.add(Operator.DIVISION);
        MathOperation result = new OperationExpression(n1, operators, arguments);
        //when
        String resultValue = result.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testThreeNumbersOperation() throws Exception {
        //given
        String VALUE1 = "1";
        String VALUE2 = "2";
        String VALUE3 = "3";
        String VALUE_RESULT = "0";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        MathOperation n3 = new Const(VALUE3);
        List<MathOperation> arguments = new ArrayList<>();
        arguments.add(n2);
        arguments.add(n3);
        List<Operator> operators = new ArrayList<>();
        operators.add(Operator.PLUS);
        operators.add(Operator.MINUS);
        MathOperation result = new OperationExpression(n1, operators, arguments);
        //when
        String resultValue = result.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testComplexMathOperation() throws Exception {
        //given
        String VALUE1 = "1";
        String VALUE2 = "2";
        String VALUE3 = "3";
        String VALUE_RESULT = "7";
        MathOperation n1 = new Const(VALUE1);
        MathOperation n2 = new Const(VALUE2);
        MathOperation n3 = new Const(VALUE3);

        List<MathOperation> multiplyArguments = new ArrayList<>();
        multiplyArguments.add(n3);
        List<Operator> multiplyOperators = new ArrayList<>();
        multiplyOperators.add(Operator.MULTIPLICATION);
        MathOperation multiply = new OperationExpression(n2, multiplyOperators, multiplyArguments);

        List<MathOperation> addArguments = new ArrayList<>();
        addArguments.add(multiply);
        List<Operator> addOperators = new ArrayList<>();
        addOperators.add(Operator.PLUS);
        MathOperation add = new OperationExpression(n1, addOperators, addArguments);
        //when
        String resultValue = add.get();
        //then
        assertEquals(VALUE_RESULT, resultValue);
    }

    @Test
    public void testEquality() throws Exception {
        //given
        String VALUE = "testValue";
        Const c1 = new Const(VALUE);
        Const c2 = new Const(VALUE);
        BoolEqual equal = new BoolEqual(c1, Operator.EQUALS, c2);
        //when
        Boolean result = equal.get();
        //then
        assertTrue(result);
    }

    @Test
    public void testAndBoolOperationSuccess() throws Exception {
        //given
        String VALUE = "a";
        Const c = new Const(VALUE);
        BoolEqual equal1 = new BoolEqual(c, Operator.EQUALS, c);
        BoolEqual equal2 = new BoolEqual(c, Operator.EQUALS, c);
        List<Operator> operators = Lists.newArrayList(Operator.AND);
        List<BoolOperation> arguments = Lists.newArrayList(equal2);
        BoolOperation andOperation = new BoolOperationExpression(equal1, operators, arguments);
        //when
        Boolean result = andOperation.get();
        //then
        assertTrue(result);
    }

    @Test
    public void testAndBoolOperationFail() throws Exception {
        //given
        String VALUE = "a";
        String VALUE2 = "b";
        Const c = new Const(VALUE);
        Const c2 = new Const(VALUE2);
        BoolEqual equal1 = new BoolEqual(c, Operator.EQUALS, c);
        BoolEqual equal2 = new BoolEqual(c, Operator.EQUALS, c2);
        List<Operator> operators = Lists.newArrayList(Operator.AND);
        List<BoolOperation> arguments = Lists.newArrayList(equal2);
        BoolOperation andOperation = new BoolOperationExpression(equal1, operators, arguments);
        //when
        Boolean result = andOperation.get();
        //then
        assertFalse(result);
    }

    @Test
    public void testOrBoolOperation() throws Exception {
        //given
        String VALUE = "a";
        String VALUE2 = "b";
        Const c = new Const(VALUE);
        Const c2 = new Const(VALUE2);
        BoolEqual equal1 = new BoolEqual(c, Operator.EQUALS, c);
        BoolEqual equal2 = new BoolEqual(c, Operator.EQUALS, c2);
        List<Operator> operators = Lists.newArrayList(Operator.OR);
        List<BoolOperation> arguments = Lists.newArrayList(equal2);
        BoolOperation andOperation = new BoolOperationExpression(equal1, operators, arguments);
        //when
        Boolean result = andOperation.get();
        //then
        assertTrue(result);
    }

    @Test
    public void testOrBoolOperationFail() throws Exception {
        //given
        String VALUE = "a";
        String VALUE2 = "b";
        Const c = new Const(VALUE);
        Const c2 = new Const(VALUE2);
        BoolEqual equal1 = new BoolEqual(c, Operator.EQUALS, c2);
        BoolEqual equal2 = new BoolEqual(c, Operator.EQUALS, c2);
        List<Operator> operators = Lists.newArrayList(Operator.OR);
        List<BoolOperation> arguments = Lists.newArrayList(equal2);
        BoolOperation andOperation = new BoolOperationExpression(equal1, operators, arguments);
        //when
        Boolean result = andOperation.get();
        //then
        assertFalse(result);
    }
}
