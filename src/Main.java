import com.google.common.io.Files;
import generator.Writer;
import parser.Parser;
import parser.exceptions.BadTokenException;
import parser.exceptions.GenerationException;
import parser.expressions.Expression;
import tokenizer.Token;
import tokenizer.Tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class Main {

    //    public static final String HELLO_WORLD = "Hello <@if(/$alfa == 25 and $beta == true) {zrob\"cos\" };@>!!";
    public static final String HELLO_WORLD = "<@{" +
            "$var = 12;" +
            "#print($a){" +
            "@> <button>pisze cos </button><@;" +
            "};" +
            "for(1 to 5){" +
            "print($var);" +
            "};" +
            "}@>";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Nie podano pliku wejsciowego");
        } else {

            String filename = args[0];
            try {
                File file = new File(filename);
                String input = Files.toString(file, Charset.defaultCharset());
                Expression parseProduction;
                Tokenizer tokenizer = new Tokenizer(input);
                List<Token> tokenList = tokenizer.tokenize();
                Parser parser = new Parser(tokenList);
                try {
                    parseProduction = parser.parse();
                    parseProduction.process();
                } catch (BadTokenException e) {
                    System.out.println("Blad parsowania");
                    System.out.println(e.getMessage());
                } catch (GenerationException e) {
                    System.out.println("Blad generacji");
                    System.out.println(e.getMessage());
                }

                try {
                    String output = Writer.getWriter().getOutput();
                    FileOutputStream outputStream = new FileOutputStream("output.txt");
                    outputStream.write(output.getBytes());
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
