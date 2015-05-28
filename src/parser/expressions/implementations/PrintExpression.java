package parser.expressions.implementations;

import generator.Writer;
import parser.exceptions.GenerationException;
import parser.expressions.Expression;

/**
 * Created by Adrian on 2015-05-26.
 */
public class PrintExpression extends Expression {
    private final String text;

    public PrintExpression(String text) {
        this.text = text;
    }

    @Override
    public void process() throws GenerationException {
        System.out.println(text);
        Writer.getWriter().write(text);
    }
}
