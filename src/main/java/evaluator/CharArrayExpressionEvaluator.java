package evaluator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Character.isDigit;

import java.util.Deque;
import java.util.LinkedList;

import evaluator.model.operator.Operators;

/**
 *  Evaluates a mathematical expression by reading tokens from a character array. Limited to +, -
 *  *, / and ^ (exponent).
 */
public class CharArrayExpressionEvaluator implements ExpressionEvaluator {

    private static final char LEFT_PAREN = '(';
    private static final char RIGHT_PAREN = ')';

    @Override
    public int evaluate(String expr) {
        checkNotNull(expr);
        Deque<Character> operatorStack = new LinkedList<>();
        Deque<Integer> valueStack = new LinkedList<>();

        char[] tokens = expr.toCharArray();
        for (int i = 0; i < tokens.length; i++) {
            char t = tokens[i];
            if (Character.isWhitespace(t)) {
                continue;
            }

            if (Operators.isOperator(t)) {
                if (!operatorStack.isEmpty()) {
                    char op = operatorStack.peek();
                    if (!isParenthesis(op) && Operators.of(op).precedes(Operators.of(t))) {
                        op = operatorStack.pop();
                        valueStack.push(evaluateSubExpression(op, valueStack));
                    }
                }
                operatorStack.push(t);
            } else if (isDigit(t)) {
                // Handle numbers with multiple digits.
                int digits = 0;
                int j = i;
                while (j < tokens.length && isDigit(tokens[j])) {
                    digits = digits * 10 + asInt(tokens[j]);
                    j++;
                }
                valueStack.push(digits);
                i = j - 1; // Set i to the correct position
            } else if (t == LEFT_PAREN) {
                operatorStack.push(t);
            } else if (t == RIGHT_PAREN) {
                // Encountered closing parenthesis, so
                // solve the parenthesised expression.
                char op = operatorStack.peek();
                if (op != LEFT_PAREN) {
                    op = operatorStack.pop();
                    valueStack.push(evaluateSubExpression(op, valueStack));
                }
                // Discard the left parenthesis
                operatorStack.pop();
            } else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", t));
            }
        }

        while (!operatorStack.isEmpty()) {
            char op = operatorStack.pop();
            valueStack.push(evaluateSubExpression(op, valueStack));
        }

        return valueStack.pop();
    }

    private int asInt(char c) {
        return Character.getNumericValue(c);
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