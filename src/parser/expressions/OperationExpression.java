package parser.expressions;

import parser.literals.Operator;

import java.util.List;

/**
 * Created by Adrian on 2015-05-16.
 */
public class OperationExpression extends Expression {
    private final Expression firstArgument;
    private final List<Operator> operators;
    private final List<Expression> weakArguments;

    public OperationExpression(Expression firstArgument, List<Operator> operators, List<Expression> weakArguments) {
        this.firstArgument = firstArgument;
        this.operators = operators;
        this.weakArguments = weakArguments;
    }
}
