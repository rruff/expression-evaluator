package evaluator;

import org.junit.Before;
import org.junit.Test;

import evaluator.model.operator.OperatorFactory;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator expressionEvaluator;

    @Before
    public void setUp() {
        expressionEvaluator = new ExpressionEvaluator(new OperatorFactory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEvaluate_invalidCharacter_throwsException() {
        expressionEvaluator.evaluate("abc");
    }
    
}
