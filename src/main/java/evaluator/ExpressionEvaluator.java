package evaluator;

public interface ExpressionEvaluator {
    
    /**
     * Evaluates {@code expr} as a mathematical expression and returns the
     * result as an integer.
     * @param expr a mathematical expression.
     * @return the result of evaluating {@code expr}.
     */
    int evaluate(String expr);
}
