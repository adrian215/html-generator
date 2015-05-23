package parser.operations.implementations;

import parser.expressions.Expression;
import parser.literals.Operator;
import parser.operations.MathOperation;
import parser.operations.Operation;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Adrian on 2015-05-16.
 */
public class OperationExpression implements MathOperation {
    private final MathOperation firstArgument;
    private final List<Operator> operators;
    private final List<MathOperation> weakArguments;

    public OperationExpression(MathOperation firstArgument, List<Operator> operators, List<MathOperation> weakArguments) {
        this.firstArgument = firstArgument;
        this.operators = operators;
        this.weakArguments = weakArguments;
    }

    @Override
    public String get() {
        String result = firstArgument.get();
        Iterator<MathOperation> currentArgumentIterator = weakArguments.iterator();
        for (Operator operator : operators) {
            String nextArgument = currentArgumentIterator.next().get();
            result = calculate(result, operator, nextArgument);
        }
        return result;
    }

    private String calculate(String result, Operator operator, String next) {

        if (isNumber(result) && isNumber(next)) {
            return calculateMath(result, operator, next);
        } else {
            return calculateString(result, operator, next);
        }
    }

    private String calculateMath(String val1, Operator operator, String val2) {
        int number1 = Integer.parseInt(val1);
        int number2 = Integer.parseInt(val2);
        int result = 0;
        switch (operator){
            case PLUS:
                result = number1 + number2;
                break;
            case MINUS:
                result = number1 - number2;
                break;
            case MULTIPLICATION:
                result = number1 * number2;
                break;
            case DIVISION:
                result = number1 / number2;
                break;
        }

        return String.valueOf(result);
    }

    private String calculateString(String s1, Operator operator, String s2) {
        String result;

        if (operator == Operator.PLUS)
            result = s1.concat(s2);
        else result = s1;

        return result;
    }

    private boolean isNumber(String string) {
        return string.matches("[0-9]+");
    }
}
