import parser.Parser;
import parser.exceptions.BadTokenException;
import parser.exceptions.GenerationException;
import parser.expressions.Expression;
import tokenizer.Token;
import tokenizer.Tokenizer;

import java.util.List;

public class Main {

//    public static final String HELLO_WORLD = "Hello <@if(/$alfa == 25 and $beta == true) {zrob\"cos\" };@>!!";
    public static final String HELLO_WORLD = "a <@{" +
        "$var = 12;" +
        "if($var == 12) $a=12;" +
        "@> a tu cos jeszcze innego <@" +
        "}@>";

    public static void main(String[] args) {
        Expression parseProduction;
        System.out.println(HELLO_WORLD);
        Tokenizer tokenizer = new Tokenizer(HELLO_WORLD);
        List<Token> tokenList = tokenizer.tokenize();
        Parser parser = new Parser(tokenList);
        try {
            parseProduction = parser.parse();
            parseProduction.process();
        } catch (BadTokenException e) {
            e.printStackTrace();
        } catch (GenerationException e) {
            e.printStackTrace();
        }
    }
}
