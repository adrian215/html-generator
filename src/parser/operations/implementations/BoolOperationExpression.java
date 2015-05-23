package parser.operations.implementations;

import parser.literals.Operator;
import parser.operations.BoolOperation;

import java.util.List;

/**
 * Created by Adrian on 2015-05-23.
 */
public class BoolOperationExpression implements BoolOperation {

    private final BoolOperation firstArgument;
    private final List<Operator> operators;
    private final List<BoolOperation> weakArguments;

    public BoolOperationExpression(BoolOperation firstArgument, List<Operator> operators, List<BoolOperation> weakArguments) {
        this.firstArgument = firstArgument;
        this.operators = operators;
        this.weakArguments = weakArguments;
    }

    //TODO implement boolean call
    @Override
    public Boolean get() {
        return null;
    }
}
