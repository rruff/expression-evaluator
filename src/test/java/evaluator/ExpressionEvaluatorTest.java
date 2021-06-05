package evaluator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator expressionEvaluator;

    @Before
    public void setUp() {
        expressionEvaluator = new ExpressionEvaluator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_invalidCharacter_throwsException() {
        expressionEvaluator.evaluate("abc");
    }
    
    @Test
    public void testEvaluate_simpleExpresssion() {
        int actual = expressionEvaluator.evaluate("5 + 1");
        assertEquals(6, actual);
    }

    @Test
    public void testEvaluate_expressionWithParens() {
        int actual = expressionEvaluator.evaluate("3 + (5 * 2)");
        assertEquals(13, actual);
    }

    @Test
    public void testEvaluate_complexExpressionWithPrecedence() {
        int actual = expressionEvaluator.evaluate("3 + 3 * 7 / (2 + 1)");
        assertEquals(10, actual);
    }

    @Test
    public void testNumericValue() {
        char[] chars = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < 10; i++) {
            assertEquals(i, Character.getNumericValue(chars[i]));
        }
    }
}
