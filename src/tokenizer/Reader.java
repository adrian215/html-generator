package tokenizer;

/**
 * Created by Adrian on 2015-05-13.
 */
public class Reader {
    private final String input;
    private int i;

    public Reader(String input) {
        this.input = input;
    }


    public char getChar() {
        return input.charAt(i);
    }

    public char getNext() throws EndOfInputException {
        if(canMove()) throw new EndOfInputException();
        return input.charAt(i + 1);
    }

    public boolean move() {
        i ++;
        return canMove();
    }

    public boolean canMove() {
        return !isLast();
    }

    private boolean isLast() {
        return i == input.length();
    }
}
