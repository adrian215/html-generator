package parser.exceptions;

/**
 * Created by Adrian on 2015-05-14.
 */
public class BadTokenException extends Exception {
    public BadTokenException() {
    }

    public BadTokenException(String message) {
        super(message);
    }

    public BadTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadTokenException(Throwable cause) {
        super(cause);
    }

    public BadTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
