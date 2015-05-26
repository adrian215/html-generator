package tokenizer;

import java.util.*;

/**
 * Created by Adrian on 2015-05-12.
 */
public class Tokenizer {
    public static final String START_TOKEN = "<@";
    public static final String STOP_TOKEN = "@>";
    private final Map<String, TokenType> tokens = createTokenMap();

    private final Map<String, TokenType> createTokenMap() {
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
    private Reader reader;
    private boolean rewriting = true;
    private char c;
    private Optional<Character> lastChar = Optional.empty();

    public Tokenizer(String input) {
        this.reader = new Reader(input);
    }

    public List<Token> tokenize() {
        do {
            c = reader.getChar();

        if(rewriting) {
            if (isOpen(lastChar, c)) {
                flushBuffer(buffer);
                clear(buffer);
                buffer.append(lastChar.get());
                buffer.append(c);
                flushBuffer(buffer);
                clear(buffer);
                lastChar = Optional.empty();
                reader.move();
            } else {
                lastChar.ifPresent(ch -> buffer.append(ch));
                if (lastChar.isPresent() && lastChar.get() == '/') {
                    lastChar = Optional.empty();
                    buffer.append(c);
                }else {
                    lastChar = Optional.of(c);
                }
                reader.move();
            }
        } else {
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
            }
            if (!reader.canMove()) {
                flushBuffer(buffer);
                clear(buffer);
            }
        } while (reader.canMove());
        return tokenResult;

    }

    private boolean isOpen(Optional<Character> lastChar, char c) {
        if (!lastChar.isPresent()) {
            return false;
        }
        String token = "";
        token += lastChar.get();
        token += c;
        return START_TOKEN.equals(token);
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
                Token token = new Token(tokens.get(buffer.toString()), buffer.toString());
                tokenResult.add(token);
                checkChangeMode(token);
            }
            else if (buffer.toString().matches("[0-9]+")){
                tokenResult.add(new Token(TokenType.NUMERIC, buffer.toString()));
            }
            else
                tokenResult.add(new Token(getDefaultTokenType(), buffer.toString()));
        }
    }

    private TokenType getDefaultTokenType() {
        if(rewriting)
            return TokenType.PRINT;
        else
            return TokenType.OTHER;
    }

    private void checkChangeMode(Token token) {
        if(token.getType() == TokenType.START)
            rewriting = false;
        if(token.getType() == TokenType.STOP)
            rewriting = true;
    }

    private boolean isEmpty(StringBuilder buffer) {
        return buffer.length() == 0;
    }

}
