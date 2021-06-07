package evaluator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ShuntingYardExpressionEvaluatorTest {

    private ExpressionEvaluator expressionEvaluator;

    @Before
    public void setUp() {
        expressionEvaluator = new ShuntingYardExpressionEvaluator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_invalidCharacter_throwsException() {
        expressionEvaluator.evaluate("abc");
    }
    
    @Test
    public void testEvaluate_simpleExpresssion() {
        int actual = expressionEvaluator.evaluate("5 + 12");
        assertEquals(17, actual);
    }

    @Test
    public void testEvaluate_expressionWithParens() {
        int actual = expressionEvaluator.evaluate("305 + (5 * 2)");
        assertEquals(315, actual);
    }

    @Test
    public void testEvaluate_complexExpressionWithPrecedence() {
        int actual = expressionEvaluator.evaluate("3 + 37 * 7 / (2 + 1)");
        assertEquals(89, actual);
    }

    @Test
    public void testEvaluateWithExponent() {
        int actual = expressionEvaluator.evaluate("3^3 * (5 + 12)");
        assertEquals(27*17, actual);
    }

    @Test
    public void testEvaluateExpression_exponentWithParenthesis() {
        String expr = "(8 * 2) ^ 3 + 15 / 5";
        assertEquals(4099, expressionEvaluator.evaluate(expr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_divideByZero_throwsException() {
        expressionEvaluator.evaluate("3 * (5 / 0)");
    }
}
