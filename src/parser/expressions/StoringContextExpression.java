package parser.expressions;

/**
 * Created by Adrian on 2015-05-23.
 */
public abstract class StoringContextExpression implements Expression{

    public abstract void call();

    @Override
    public void process() {
        storeContext();
        call();
        restoreContext();
    }

    //TODO implement storing context
    public void storeContext(){

    }

    //TODO implement restoring context
    public void restoreContext(){

    }
}
