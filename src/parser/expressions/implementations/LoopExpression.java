package parser.expressions.implementations;

import parser.expressions.Expression;
import parser.expressions.StoringContextExpression;
import parser.operations.MathOperation;
import parser.operations.Operation;

/**
 * Created by Adrian on 2015-05-16.
 */
public class LoopExpression extends StoringContextExpression {
    private final MathOperation from;
    private final MathOperation to;
    private final Expression statements;

    public LoopExpression(MathOperation from, MathOperation to, Expression statements) {
        this.from = from;
        this.to = to;
        this.statements = statements;
    }

    @Override
    protected void call() {
        try {
            int start = Integer.parseInt(from.get());
            int stop = Integer.parseInt(to.get());
            int from = lower(start, stop);
            int to = higher(start, stop);
            for (int i = from; i <= to; i++) {
                statements.process();
            }
        } catch (NumberFormatException e){
            // bad argument, loop should not execute
        }
    }

    private int higher(int start, int stop) {
        if (start >= stop) {
            return start;
        }
        return stop;
    }

    private int lower(int start, int stop) {
        if (start <= stop) {
            return start;
        }
        return stop;
    }

    public Operation getFrom() {
        return from;
    }

    public Operation getTo() {
        return to;
    }

    public Expression getStatements() {
        return statements;
    }
}
