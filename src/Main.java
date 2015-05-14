import tokenizer.Token;
import tokenizer.Tokenizer;

import java.util.List;

public class Main {

    public static final String HELLO_WORLD = "Hello <@if(/$alfa == 5 and $beta == true) {zrob\"cos\" };@>!!";

    public static void main(String[] args) {
        System.out.println(HELLO_WORLD);
        Tokenizer tokenizer = new Tokenizer(HELLO_WORLD);
        List<Token> tokenList = tokenizer.tokenize();
    }
}
