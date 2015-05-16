package parser.literals;

/**
 * Created by Adrian on 2015-05-14.
 */
public class Variable {
    private final String name;
    private final String value;

    public Variable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }
}
