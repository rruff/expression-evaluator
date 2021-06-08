package evaluator;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Character.isDigit;

import java.util.Deque;
import java.util.LinkedList;

import evaluator.model.operator.Operators;

/**
 *  Evaluates an infix mathematical expression by reading tokens from a character array. Limited to +, -
 *  *, /, ^ (exponent) and parenthesised sub-expressions.
 * 
 * This is an implementation of the 
 * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting-yard Algorithm</a>,
 * adapted to produce the result rather than convert the expression to RPN. The adapted algorithm is
 * described here: {@link https://www.geeksforgeeks.org/expression-evaluation/}.
 */
public class ShuntingYardExpressionEvaluator implements ExpressionEvaluator {

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
                consumeOperator(t, operatorStack, valueStack);
            } else if (isDigit(t)) {
                int position = consumeDigits(tokens, i, valueStack);
                i = position - 1; // Set i to the correct position
            } else if (t == LEFT_PAREN) {
                operatorStack.push(t);
            } else if (t == RIGHT_PAREN) {
                // Encountered closing parenthesis, so
                // solve the parenthesised expression.
                if (operatorStack.peek() != LEFT_PAREN) {
                    int right = valueStack.pop();
                    int left = valueStack.pop();
                    valueStack.push(evaluate(operatorStack.pop(), left, right));
                }
                // Discard the left parenthesis
                operatorStack.pop();
            } else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", t));
            }
        }

        while (!operatorStack.isEmpty()) {
            int right = valueStack.pop();
            int left = valueStack.pop();
            valueStack.push(evaluate(operatorStack.pop(), left, right));
        }

        return valueStack.pop();
    }

    private void consumeOperator(char op, Deque<Character> operatorStack, Deque<Integer> valueStack) {
        if (!operatorStack.isEmpty()) {
            char opOnStack = operatorStack.peek();
            if (!isParenthesis(opOnStack) && Operators.precedes(opOnStack, op)) {
                int right = valueStack.pop();
                int left = valueStack.pop();
                valueStack.push(evaluate(operatorStack.pop(), left, right));
            }
        }
        operatorStack.push(op);
    }

    /**
     * Handles a digit token encountered in the array. It will keep consuming characters until something
     * other than a digit is encountered. Digit characers are combined into a single integer, and then pushed on
     * the value stack.
     * 
     * <p>Returns the current position in the {@code tokens} array after all consecutive digits have been consumed.</p>
     * @param tokens the array of tokens
     * @param currentPosition the current position in {@code tokens} when the method is called.
     * @param valueStack the stack of integers
     * @return the current position in the {@code tokens} array after all consecutive digits have been consumed.
     */
    private int consumeDigits(char[] tokens, int currentPosition, Deque<Integer> valueStack) {
        int digits = 0;
        int j = currentPosition;
        while (j < tokens.length && isDigit(tokens[j])) {
            digits = combineDigits(digits, tokens[j]);
            j++;
        }
        valueStack.push(digits);
        return j;
    }

    /**
     * Combines single digits into a number represented by multiple digits.
     * 
     * Example:
     * <blockquote>
     *      <pre>int result = combineDigits(5, '7'); // result = 57</pre>
     * </blockquote>
     * @param acc the accumulator that will hold the final number.
     * @param digit a numeric character.
     * @return the resulting number.
     */
    private int combineDigits(int acc, char digit) {
        return acc * 10 + Character.getNumericValue(digit);
    }

    private int evaluate(char op, int left, int right) {
        return Operators.apply(op, left, right);
    }

    private boolean isParenthesis(char c) {
        return c == LEFT_PAREN || c == RIGHT_PAREN;
    }
}