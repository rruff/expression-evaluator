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

    @Test
    public void testMultiplication() {
        Operator multiply = Operators.of('*');
        assertEquals(69, multiply.execute(3, 23));
    }

    @Test
    public void testDivision() {
        Operator divide = Operators.of('/');
        assertEquals(69, divide.execute(207, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivision_divideByZero_throwsIllegalArgumentException() {
        Operators.of('/').execute(7, 0);
    }

    @Test
    public void testAddition() {
        Operator add = Operators.of('+');
        assertEquals(69, add.execute(62, 7));
    }

    @Test
    public void testSubtraction() {
        Operator subtract = Operators.of('-');
        assertEquals(69, subtract.execute(78, 9));
    }
}