package tests;

import com.google.common.collect.Lists;
import org.junit.Test;
import parser.Parser;
import parser.exceptions.BadTokenException;
import parser.expressions.Expression;
import tokenizer.Token;

import java.util.List;

import static tokenizer.TokenType.*;

/**
 * Created by Adrian on 2015-05-14.
 */
public class ParserTest {

    @Test
    public void oneLineAssignStringTest() throws BadTokenException {
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
    public void oneLineAssignNumberTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(NUMERIC, "123"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void oneLineAssignOtherVariableTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void oneLineAssignOperationTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(ASSIGN, "="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(PLUS, "+"),
                new Token(NUMERIC, "3"),
                new Token(MULLTIPLICATION, "*"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna"),
                new Token(PLUS, "+"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void conditionalTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(IF, "if"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(EQUALS, "=="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(AND, "and"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(EQUALS, "=="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "innaZmienna"),
                new Token(QUOTES, "\""),
                new Token(CLOSE_BRACKET, ")"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna"),
                new Token(ASSIGN, "="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna2"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void conditionalBlockTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(IF, "if"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(EQUALS, "=="),
                new Token(NUMERIC, "12"),
                new Token(OR, "or"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "inna"),
                new Token(EQUALS, "=="),
                new Token(QUOTES, "\""),
                new Token(OTHER, "a"),
                new Token(QUOTES, "\""),
                new Token(AND, "and"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(EQUALS, "=="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "inna"),
                new Token(CLOSE_BRACKET, ")"),
                new Token(OPEN_CURLY_BRACES, "{"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "a"),
                new Token(ASSIGN, "="),
                new Token(NUMERIC, "12"),
                new Token(SEMICOLON, ";"),
                new Token(CLOSE_CURLY_BRACES, "}"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void loopTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(FOR, "for"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(TO, "to"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "innaZmienna"),
                new Token(CLOSE_BRACKET, ")"),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna"),
                new Token(ASSIGN, "="),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "jeszczeInnaZmienna2"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void callMethodNoArgsTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(OTHER, "name"),
                new Token(OPEN_BRACKET, "("),
                new Token(CLOSE_BRACKET, ")"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void callMethodOneArgTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(OTHER, "name"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(CLOSE_BRACKET, ")"),
                new Token(STOP, "@>")
        );
        Parser parser = new Parser(tokens);
        Expression parse = parser.parse();
    }

    @Test
    public void callMethodTwoArgsTest() throws BadTokenException {
        List<Token> tokens = Lists.newArrayList(
                new Token(START, "<@"),
                new Token(OTHER, "name"),
                new Token(OPEN_BRACKET, "("),
                new Token(DOLLAR, "$"),
                new Token(OTHER, "name"),
                new Token(COMMA, ","),
                new Token(QUOTES, "\""),
                new Token(OTHER, "text"),
                new Token(QUOTES, "\""),
                new Token(CLOSE_BRACKET, ")"),
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
