package parser.expressions;

import parser.exceptions.GenerationException;

/**
 * Created by Adrian on 2015-05-23.
 */
public abstract class StoringContextExpression extends Expression {

    protected abstract void call() throws GenerationException;

    @Override
    public void process() throws GenerationException {
        storeContext();
        call();
        restoreContext();
    }

    private void storeContext(){
        stackManager.pushCurrent();
    }

    private void restoreContext(){
        stackManager.popCurrent();
    }
}
