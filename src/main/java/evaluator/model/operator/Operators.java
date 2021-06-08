package evaluator.model.operator;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public final class Operators {

    private static final Set<Character> OPERATORS = ImmutableSet.of('^', '*', '/', '+', '-');

    private static final Map<Character, BinaryOperator<Integer>> CHAR_TO_OP = ImmutableMap.of(
        '*', (l, r) -> l * r,
        '+', (l, r) -> l + r,
        '-', (l, r) -> l - r,
        '^', (l, r) -> (int) Math.pow((double) l, (double) r),
        '/', (l, r) -> {
            if (r == 0) {
                throw new IllegalArgumentException("Cannot divide by 0");
            }
            return l / r;
        }
    );

    private static Comparator<Character> CMP = new OperatorCompator();

    public static int apply(char op, int left, int right) {
        return CHAR_TO_OP.get(op).apply(left, right);
    }

    public static boolean isOperator(char c) {
        return OPERATORS.contains(c);
    }

    public static boolean precedes(char o1, char o2) {
        return CMP.compare(o1, o2) > 0;
    }

    /**
     * Compares mathematical operators for PEMDAS order.
     */
    private static class OperatorCompator implements Comparator<Character> {

        private static Map<Character, Integer> precedenceMap = ImmutableMap.of(
            '^', 32,
            '*', 16,
            '/', 8,
            '+', 4,
            '-', 2
        );

        @Override
        public int compare(Character o1, Character o2) {
            checkArgument(isOperator(o1), "%s is not a valid operator", o1);
            checkArgument(isOperator(o2), "%s is not a valid operator", o1);

            return Integer.compare(precedenceMap.get(o1), precedenceMap.get(o2));
        }
    }
}
