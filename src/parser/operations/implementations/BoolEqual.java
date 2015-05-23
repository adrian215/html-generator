package parser.operations.implementations;

import parser.literals.Operator;
import parser.operations.BoolOperation;
import parser.operations.Operation;

import java.util.List;

/**
 * Created by Adrian on 2015-05-23.
 */
public class BoolEqual implements BoolOperation {

    private final Operation firstArgument;
    private final List<Operator> operators;
    private final List<Operation> weakArguments;

    public BoolEqual(Operation firstArgument, List<Operator> operators, List<Operation> weakArguments) {
        this.firstArgument = firstArgument;
        this.operators = operators;
        this.weakArguments = weakArguments;
    }

    @Override
    public Boolean get() {
        return null;
    }
}
