package parser.operations.implementations;

import parser.expressions.Expression;
import parser.literals.Operator;
import parser.operations.MathOperation;
import parser.operations.Operation;

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

    //TODO implement operation call
    @Override
    public String get() {
        return null;
    }
}
