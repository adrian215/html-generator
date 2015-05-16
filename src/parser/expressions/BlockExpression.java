package parser.expressions;

import java.util.List;

/**
 * Created by Adrian on 2015-05-14.
 */
public class BlockExpression extends Expression {
    private final List<Expression> expressions;

    public BlockExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }
}
