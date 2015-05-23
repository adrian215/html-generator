package parser.expressions;

/**
 * Created by Adrian on 2015-05-23.
 */
public abstract class StoringContextExpression extends Expression {

    protected abstract void call();

    @Override
    public void process() {
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
