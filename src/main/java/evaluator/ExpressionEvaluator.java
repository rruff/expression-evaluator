package evaluator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import evaluator.model.operator.Operator;
import evaluator.model.operator.OperatorFactory;

/**
 *  Evaluates a mathematical expression.
 */
public class ExpressionEvaluator {
    
    private OperatorFactory operatorFactory;

    public int evaluate(String expr) {
        List<Character> chars = expr.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
        Deque<Operator> operatorStack = new LinkedList<>();
        Deque<Integer> valueStack = new LinkedList<>();

        for (char c : chars) {
            if (operatorFactory.isOperator(c)) {
                operatorStack.push(operatorFactory.getInstance(c));
            } else if (Character.isDigit(c)) {
                valueStack.push(Integer.valueOf(String.valueOf(c)));
            } else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", c));
            }
        }
        return 0;
    }
}
