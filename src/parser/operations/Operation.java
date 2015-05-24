package parser.operations;

import parser.exceptions.GenerationException;

/**
 * Created by Adrian on 2015-05-23.
 */
public interface Operation<T> {
    public T get() throws GenerationException;
}
