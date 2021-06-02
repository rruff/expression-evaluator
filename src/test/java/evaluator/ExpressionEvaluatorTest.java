package evaluator;

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
    
}
