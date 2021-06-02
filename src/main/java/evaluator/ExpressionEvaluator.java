package evaluator;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import evaluator.model.operator.MathOperator;
import evaluator.model.operator.Operator;

/**
 *  Evaluates a mathematical expression.
 */
public class ExpressionEvaluator {

    public int evaluate(String expr) {
        checkNotNull(expr);
        List<Character> chars = expr.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        Deque<Operator> operatorStack = new LinkedList<>();
        Deque<Integer> valueStack = new LinkedList<>();

        for (char c : chars) {
            if (MathOperator.isOperator(c)) {
                operatorStack.push(MathOperator.fromChar(c));
            } else if (Character.isDigit(c)) {
                valueStack.push(Integer.valueOf(String.valueOf(c)));
            } else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", c));
            }
        }
        return 0;
    }
}
