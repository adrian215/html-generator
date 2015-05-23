package parser.operations.implementations;

import parser.literals.Operator;
import parser.operations.BoolOperation;
import parser.operations.MathOperation;

import java.util.Objects;

/**
 * Created by Adrian on 2015-05-23.
 */
public class BoolEqual implements BoolOperation {

    private final MathOperation firstArgument;
    private final Operator operator;
    private final MathOperation secondArgument;

    public BoolEqual(MathOperation firstArgument, Operator operator, MathOperation weakArguments) {
        this.firstArgument = firstArgument;
        this.operator = operator;
        this.secondArgument = weakArguments;
    }

    @Override
    public Boolean get() {
        if(operator == Operator.EQUALS)
            return objectEquals(firstArgument.get(), secondArgument.get());
        return false;
    }

    private Boolean objectEquals(String arg1, String arg2) {
        if(Objects.isNull(arg1) || Objects.isNull(arg2))
            return false;
        return arg1.equals(arg2);
    }
}
