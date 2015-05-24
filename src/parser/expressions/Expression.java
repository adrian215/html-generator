package parser.expressions;

import generator.StackManager;
import parser.exceptions.GenerationException;

/**
 * Created by Adrian on 2015-05-14.
 */
public abstract class Expression {
    protected StackManager stackManager = StackManager.getStackManager();
    public abstract void process() throws GenerationException;
}
