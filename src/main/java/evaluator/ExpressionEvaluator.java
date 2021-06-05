package evaluator;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Deque;
import java.util.LinkedList;

import evaluator.model.operator.Operators;

/**
 *  Evaluates a mathematical expression.
 */
public class ExpressionEvaluator {

    private static final char LEFT_PAREN = '(';
    private static final char RIGHT_PAREN = ')';

    public int evaluate(String expr) {
        checkNotNull(expr);
        Deque<Character> operatorStack = new LinkedList<>();
        Deque<Integer> valueStack = new LinkedList<>();

        expr.chars().filter(c -> !Character.isWhitespace(c)).mapToObj(c -> (char)c).forEach(c -> {
            if (Operators.isOperator(c)) {
                if (!operatorStack.isEmpty()) {
                    char op = operatorStack.peek();
                    if (!isParenthesis(op) && Operators.of(op).isHigherPrecedenceThan(Operators.of(c))) {
                        op = operatorStack.pop();
                        valueStack.push(evaluateSubExpression(op, valueStack));
                    }
                }
                operatorStack.push(c);
            } else if (Character.isDigit(c)) {
                valueStack.push(Integer.valueOf(String.valueOf(c)));
            } else if (c == LEFT_PAREN) {
                operatorStack.push(c);
            } else if (c == RIGHT_PAREN) {
                char op = operatorStack.peek();
                if (op != LEFT_PAREN) {
                    op = operatorStack.pop();
                    valueStack.push(evaluateSubExpression(op, valueStack));
                }
                // Discard the left paren
                operatorStack.pop();
            }
            else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", c));
            }
        });

        while (!operatorStack.isEmpty()) {
            char op = operatorStack.pop();
            valueStack.push(evaluateSubExpression(op, valueStack));
        }

        return valueStack.pop();
    }

    private int evaluateSubExpression(char op, Deque<Integer> valueStack) {
        int right = valueStack.pop();
        int left = valueStack.pop();
        return Operators.of(op).execute(left, right);
    }

    private boolean isParenthesis(char c) {
        return c == LEFT_PAREN || c == RIGHT_PAREN;
    }
}