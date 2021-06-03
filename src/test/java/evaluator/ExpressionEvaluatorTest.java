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
}
