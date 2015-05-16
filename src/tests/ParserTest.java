package tests;

import com.google.common.collect.Lists;
import org.junit.Test;
import parser.Parser;
import parser.exceptions.BadTokenException;
import parser.expressions.Expression;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.List;

import static tokenizer.TokenType.*;

/**
 * Created by Adrian on 2015-05-14.
 */
public class ParserTest {

    @Test
    public void oneLineBlockTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "wartosc"),
                new Token(QUOTES, "\""),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void multipleLineBlockTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(OPEN_CURLY_BRACES, "{"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "wartosc"),
                new Token(QUOTES, "\""),
                new Token(SEMICOLON, ";"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "wartosc"),
                new Token(QUOTES, "\""),
                new Token(SEMICOLON, ";"),
                new Token(CLOSE_CURLY_BRACES, "}"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void declarationFunctionTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(HASH, "#"),
                new Token(OTHER, "name"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(CLOSE_BRACKET, ")"),
                new Token(OPEN_CURLY_BRACES, "{"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "wartosc"),
                new Token(QUOTES, "\""),
                new Token(SEMICOLON, ";"),
                new Token(CLOSE_CURLY_BRACES, "}"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }
}
