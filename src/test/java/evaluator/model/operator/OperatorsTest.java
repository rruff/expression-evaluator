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
        assertEquals(27, Operators.of('^').execute(3, 3));
        assertEquals(81, Operators.of('^').execute(9, 2));
    }

    @Test
    public void testMultiplication() {
        assertEquals(69, Operators.of('*').execute(3, 23));
    }

    @Test
    public void testDivision() {
        assertEquals(69, Operators.of('/').execute(207, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivision_divideByZero_throwsIllegalArgumentException() {
        Operators.of('/').execute(7, 0);
    }

    @Test
    public void testAddition() {
        assertEquals(69, Operators.of('+').execute(62, 7));
    }

    @Test
    public void testSubtraction() {
        assertEquals(69, Operators.of('-').execute(78, 9));
    }
}