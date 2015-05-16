package tokenizer;

/**
 * Created by Adrian on 2015-05-12.
 */
public enum TokenType {
    START,              // <@
    STOP,               // @>
    OPEN_BRACKET,       // (
    CLOSE_BRACKET,      // )
    COMMA,              // ,
    SEMICOLON,          // ;
    QUOTES,              // "
    DOLLAR,             // $
    OPEN_CURLY_BRACES,  // {
    CLOSE_CURLY_BRACES, // }
    HASH,               // #
    IF,                 // if
    ELSE,               // else
    FOR,                // for
    TRUE,               // true
    FALSE,              // false
    EQUALS,             // ==
    ASSIGN,             // =
    AND,                // and
    OR,                 // or
    NUMERIC,
    OTHER
}
