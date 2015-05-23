package generator;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if(obj instanceof Variable == false)
            return false;
        Variable variable = (Variable) obj;
        return Objects.equal(name, variable.getName()) && Objects.equal(value, variable.getValue());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
