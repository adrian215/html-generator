package tests;

import org.junit.Assert;
import org.junit.Test;
import parser.operations.MathOperation;
import parser.operations.implementations.Const;

/**
 * Created by Adrian on 2015-05-23.
 */
public class GeneratorTest {

    @Test
    public void constValueTest() {
        //given
        String VALUE = "3";
        MathOperation c = new Const(VALUE);
        //when
        String result = c.get();
        //then
        Assert.assertEquals(VALUE, result);
    }
}
