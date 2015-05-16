package tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adrian on 2015-05-12.
 */
public class Tokenizer {
    private final Map<String, TokenType> tokens = createTokenMap();

    private Map<String, TokenType> createTokenMap() {
        Map<String, TokenType> resultMap = new HashMap<>();
            resultMap.put("<@", TokenType.START);
            resultMap.put("@>", TokenType.STOP);
            resultMap.put("(", TokenType.OPEN_BRACKET);
            resultMap.put(")", TokenType.CLOSE_BRACKET);
            resultMap.put(",", TokenType.COMMA);
            resultMap.put(";", TokenType.SEMICOLON);
            resultMap.put("\"", TokenType.QUOTES);
            resultMap.put("{", TokenType.OPEN_CURLY_BRACES);
            resultMap.put("}", TokenType.CLOSE_CURLY_BRACES);
            resultMap.put("$", TokenType.DOLLAR);
            resultMap.put("#", TokenType.HASH);
            resultMap.put("if", TokenType.IF);
            resultMap.put("else", TokenType.ELSE);
            resultMap.put("for", TokenType.FOR);
            resultMap.put("to", TokenType.TO);
            resultMap.put("true", TokenType.TRUE);
            resultMap.put("false", TokenType.FALSE);
            resultMap.put("==", TokenType.EQUALS);
            resultMap.put("=", TokenType.ASSIGN);
            resultMap.put("and", TokenType.AND);
            resultMap.put("or", TokenType.OR);
        return resultMap;
    }
//    private final List<String> tokenList = Arrays.asList(
//            "(",
//            ")",
//            "{",
//            "}",
//            "$",
//            "#",
//            "if",
//            "else",
//            "for",
//            "true",
//            "false",
//            "==",
//            "and",
//            "or",
//            "to"
//    );

    private StringBuilder buffer = new StringBuilder();
    private List<Token> tokenResult = new ArrayList<>();
    Reader reader;
    char c;

    public Tokenizer(String input) {
        this.reader = new Reader(input);
    }

    public List<Token> tokenize() {
        do {
            c = reader.getChar();

            if (CharacterTools.isSpace(c)) {
                flushBuffer(buffer);
                clear(buffer);
                reader.move();

            }
            else if (tokensContains(buffer.toString())) {
                if (tokensContains(buffer.toString() + reader.getChar())) {
                    buffer.append(reader.getChar());
                    reader.move();
                } else if(tokenEquals(buffer)) {
                    flushBuffer(buffer);
                    clear(buffer);
                    buffer.append(reader.getChar());
                    reader.move();
                }
                else dispatchDefault();
            } else dispatchDefault();

            if (!reader.canMove()) {
                flushBuffer(buffer);
                clear(buffer);
            }
        } while (reader.canMove());
        return tokenResult;

    }

    private void dispatchDefault() {
        if (!isAlphaNumeric(reader.getChar())){
            flushBuffer(buffer);
            clear(buffer);
            buffer.append(c);
            reader.move();
        }
        else {
            buffer.append(c);
            reader.move();
        }
    }

    private boolean isAlphaNumeric(char character) {
        return Character.isLetterOrDigit(character);
    }

    private boolean tokenEquals(StringBuilder key) {
        if("".equals(key)) return false;
        return tokens.containsKey(key.toString());
    }

    private boolean tokensContains(String key) {
        if("".equals(key)) return false;
        for(Map.Entry<String, TokenType> entry : tokens.entrySet()){
            if(entry.getKey().startsWith(key))
                return true;
        }
        return false;
    }

    private void clear(StringBuilder buffer) {
        buffer.setLength(0);
    }

    private void flushBuffer(StringBuilder buffer) {
        if (!isEmpty(buffer)) {
            if (tokens.containsKey(buffer.toString())) {
                tokenResult.add(new Token(tokens.get(buffer.toString()), buffer.toString()));
            }
            else if (buffer.toString().matches("[0-9]+")){
                tokenResult.add(new Token(TokenType.NUMERIC, buffer.toString()));
            }
            else
                tokenResult.add(new Token(TokenType.OTHER, buffer.toString()));
        }
    }

    private boolean isEmpty(StringBuilder buffer) {
        return buffer.length() == 0;
    }

}
