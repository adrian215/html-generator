package tokenizer;

/**
 * Created by Adrian on 2015-05-12.
 */
public class Token {
    private TokenType type;
    private String parameter;

    public Token(TokenType type, String parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
