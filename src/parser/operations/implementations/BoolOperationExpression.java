package parser.operations.implementations;

import parser.exceptions.GenerationException;
import parser.literals.Operator;
import parser.operations.BoolOperation;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Adrian on 2015-05-23.
 */
public class BoolOperationExpression implements BoolOperation {

    private final BoolOperation firstArgument;
    private final List<Operator> operators;
    private final List<BoolOperation> arguments;

    public BoolOperationExpression(BoolOperation firstArgument, List<Operator> operators, List<BoolOperation> aguments) {
        this.firstArgument = firstArgument;
        this.operators = operators;
        this.arguments = aguments;
    }

    @Override
    public Boolean get() throws GenerationException {
        boolean result = firstArgument.get();
        Iterator<BoolOperation> argumentsIterator = arguments.iterator();

        for (Operator operator : operators) {
            boolean currentArgumentValue = argumentsIterator.next().get();
            result = check(result, operator, currentArgumentValue);
        }

        return result;
    }

    private boolean check(boolean argument1, Operator operator, boolean argument2) {
        if (operator == Operator.AND) {
            return argument1 && argument2;
        }
        else if (operator == Operator.OR) {
            return argument1 || argument2;
        }
        return false;
    }
}
