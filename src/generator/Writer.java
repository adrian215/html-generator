package generator;

/**
 * Created by Adrian on 2015-05-27.
 */
public class Writer {
    private static Writer writer = null;
    private StringBuilder output = new StringBuilder();

    public static Writer getWriter() {
        if (writer == null) {
            writer = new Writer();
        }
        return writer;
    }

    public void write(String text) {
        output.append(text);
    }

    public String getOutput() {
        return output.toString();
    }
}
