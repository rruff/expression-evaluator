package evaluator.model.operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrderedIntegerOperatorTest {
    
    @Test
    public void testExponent() {
        assertEquals(25, new OrderedIntegerOperator('^').apply(5, 2).intValue());
    }

    @Test
    public void testMultipliction() {
        var op = new OrderedIntegerOperator('*');
        assertEquals(15, op.apply(5, 3).intValue());
        assertEquals(0, op.apply(17, 0).intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivision_divideByZero_throwsException() {
        new OrderedIntegerOperator('/').apply(4, 0);
    }

    @Test
    public void testDivision() {
        var op = new OrderedIntegerOperator('/');
        assertEquals(3, op.apply(7, 2).intValue());
        assertEquals(14, op.apply(28, 2).intValue());
    }

    @Test
    public void testAddition() {
        var op = new OrderedIntegerOperator('+');
        assertEquals(1005, op.apply(1000, 5).intValue());
        assertEquals(17, op.apply(10, 7).intValue());
    }

    @Test
    public void testSubtraction() {
        var op = new OrderedIntegerOperator('-');
        assertEquals(19, op.apply(29, 10).intValue());
        assertEquals(-1, op.apply(0, 1).intValue());
    }

    @Test
    public void testPrecedence() {
        var add = new OrderedIntegerOperator('+');
        var pow = new OrderedIntegerOperator('^');
        var subtract = new OrderedIntegerOperator('-');
        var divide = new OrderedIntegerOperator('/');
        var multiply = new OrderedIntegerOperator('*');

        for (var op : new OrderedIntegerOperator[] {add, subtract, multiply, divide}){
            assertTrue(pow.compareTo(op) > 0);
        }

        for (var op : new OrderedIntegerOperator[] {divide, add, subtract}) {
            assertTrue(multiply.compareTo(op) > 0);
        }
        assertTrue(multiply.compareTo(pow) < 0);

        for (var op : new OrderedIntegerOperator[] {pow, multiply, divide}) {
            assertTrue(add.compareTo(op) < 0);
        }
        assertTrue(add.compareTo(subtract) > 0);

        for (var op : new OrderedIntegerOperator[]{pow, multiply, divide, add}) {
            assertTrue(subtract.compareTo(op) < 0);
        }

        for (var op : new OrderedIntegerOperator[] {pow, multiply}) {
            assertTrue(divide.compareTo(op) < 0);
        }

        for (var op : new OrderedIntegerOperator[] {add, subtract}) {
            assertTrue(divide.compareTo(op) > 0);
        }
    }
}
