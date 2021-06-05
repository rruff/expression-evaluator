package evaluator.model.operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class OperatorsTest {

    @Test
    public void testIsOperator_validOperators_returnTrue() {
        Arrays.asList('^', '*', '/', '+', '-').forEach(op -> {
            assertTrue(Operators.isOperator(op));
        });
    }

    @Test
    public void testIsOperator_invalidOperators_returnFalse() {
        Arrays.asList('|', 'a', '\\', '%', 't').forEach(op -> {
            assertFalse(Operators.isOperator(op));
        });
    }

    @Test
    public void testPower() {
        Operator pow = Operators.of('^');
        assertEquals(27, pow.execute(3, 3));
        assertEquals(81, pow.execute(9, 2));
    }
}