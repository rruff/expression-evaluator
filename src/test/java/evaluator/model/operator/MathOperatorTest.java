package evaluator.model.operator;

import static evaluator.model.operator.MathOperator.ADDITION;
import static evaluator.model.operator.MathOperator.DIVISION;
import static evaluator.model.operator.MathOperator.MULTIPLICATION;
import static evaluator.model.operator.MathOperator.SUBTRACTION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MathOperatorTest {
    
    @Test
    public void testMultiplicationPrecedence() {
        assertTrue(MULTIPLICATION.compareTo(ADDITION) > 0);
        assertTrue(MULTIPLICATION.compareTo(SUBTRACTION) > 0);
        assertTrue(MULTIPLICATION.compareTo(DIVISION) > 0);
    }

    @Test
    public void testDivisionPrecedence() {
        assertTrue(DIVISION.compareTo(ADDITION) > 0);
        assertTrue(DIVISION.compareTo(SUBTRACTION) > 0);
        assertTrue(DIVISION.compareTo(MULTIPLICATION) < 0);
    }

    @Test
    public void testAdditionPrecedence() {
        assertTrue(ADDITION.compareTo(SUBTRACTION) > 0);
        assertTrue(ADDITION.compareTo(MULTIPLICATION) < 0);
        assertTrue(ADDITION.compareTo(DIVISION) < 0);
    }

    @Test
    public void testSubtractionPrecedence() {
        assertTrue(SUBTRACTION.compareTo(ADDITION) < 0);
        assertTrue(SUBTRACTION.compareTo(DIVISION) < 0);
        assertTrue(SUBTRACTION.compareTo(MULTIPLICATION) < 0);
    }

    @Test
    public void testIsOperator() {
        assertTrue(MathOperator.isOperator('+'));
        assertFalse(MathOperator.isOperator('|'));
    }
}
