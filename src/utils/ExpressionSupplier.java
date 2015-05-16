package utils;

import parser.exceptions.BadTokenException;

import java.util.function.Supplier;

/**
 * Created by Adrian on 2015-05-16.
 */
@FunctionalInterface
public interface ExpressionSupplier<T> {
    T get() throws BadTokenException;
}
