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
                handleOperator(t, operatorStack, valueStack);
            } else if (isDigit(t)) {
                // Handle numbers with multiple digits.
                int digits = 0;
                int j = i;
                while (j < tokens.length && isDigit(tokens[j])) {
                    digits = combineDigits(digits, tokens[j]);
                    j++;
                }
                valueStack.push(digits);
                i = j - 1; // Set i to the correct position
            } else if (t == LEFT_PAREN) {
                operatorStack.push(t);
            } else if (t == RIGHT_PAREN) {
                // Encountered closing parenthesis, so
                // solve the parenthesised expression.
                if (operatorStack.peek() != LEFT_PAREN) {
                    valueStack.push(evaluate(operatorStack, valueStack));
                }
                // Discard the left parenthesis
                operatorStack.pop();
            } else {
                throw new IllegalArgumentException(String.format("Invalid character: %s", t));
            }
        }

        while (!operatorStack.isEmpty()) {
            valueStack.push(evaluate(operatorStack, valueStack));
        }

        return valueStack.pop();
    }

    private void handleOperator(char op, Deque<Character> operatorStack, Deque<Integer> valueStack) {
        if (!operatorStack.isEmpty()) {
            char opOnStack = operatorStack.peek();
            if (!isParenthesis(opOnStack) && precedes(opOnStack, op)) {
                valueStack.push(evaluate(operatorStack, valueStack));
            }
        }
        operatorStack.push(op);
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

    private int evaluate(Deque<Character> operatorStack, Deque<Integer> valStack) {
        int right = valStack.pop();
        int left = valStack.pop();
        return Operators.fromChar(operatorStack.pop()).apply(left, right);
    }

    /**
     * Returns true if {@code op1} has precedence over {@code op2}.
     * @param op1 the left operator in the comparison.
     * @param op2 the right operator in the comparison.
     * @return true if {@code op1} has precedence over {@code op2}.
     */
    private boolean precedes(char op1, char op2) {
        return Operators.fromChar(op1).compareTo(Operators.fromChar(op2)) > 0;
    }

    private boolean isParenthesis(char c) {
        return c == LEFT_PAREN || c == RIGHT_PAREN;
    }
}